package org.seasar.cubby.tags;

import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.multipleFormValues;
import static org.seasar.cubby.tags.TagUtils.outputValues;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.message.MessageFormatter;

/**
 * selectを出力するタグ
 * 
 * @author agata
 * @author baba
 */
public class SelectTag extends DynamicAttributesTagSupport {

	private static final Logger logger = Logger.getLogger(SelectTag.class);

	private String name;

	/**
	 * option要素リスト
	 */
	private Object items;

	/**
	 * optionのラベルのプロパティ名
	 */
	private String labelProperty;

	/**
	 * optionの値のプロパティ名
	 */
	private String valueProperty;

	/**
	 * 空のoption要素を出力するかどうか。
	 */
	private boolean emptyOption = true;

	/**
	 * 空のoption要素を出力した場合のラベル文字列
	 */
	private String emptyOptionLabel;

	public Object getItems() {
		return items;
	}

	/**
	 * option要素リストをセットします。
	 * 
	 * @param items
	 *            option要素リスト
	 */
	public void setItems(final Object items) {
		this.items = items;
	}

	/**
	 * optionのラベルのプロパティ名をセットします。
	 * 
	 * @param labelProperty
	 *            optionのラベルのプロパティ名
	 */
	public void setLabelProperty(final String labelProperty) {
		this.labelProperty = labelProperty;
	}

	/**
	 * optionのラベルのプロパティ名をセットします。
	 * 
	 * @param valueProperty
	 *            optionのラベルのプロパティ名
	 */
	public void setValueProperty(final String valueProperty) {
		this.valueProperty = valueProperty;
	}

	/**
	 * 空のoption要素を出力するかどうかをセットします。
	 * 
	 * @param emptyOption
	 *            空のoption要素を出力するかどうか
	 */
	public void setEmptyOption(final boolean emptyOption) {
		this.emptyOption = emptyOption;
	}

	/**
	 * 空のoption要素を出力した場合のラベル文字列をセットします。
	 * 
	 * @param emptyOptionLabel
	 *            空のoption要素を出力した場合のラベル文字列
	 */
	public void setEmptyOptionLabel(final String emptyOptionLabel) {
		this.emptyOptionLabel = emptyOptionLabel;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * タグの処理
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		final JspContext context = this.getJspContext();
		final JspWriter out = context.getOut();
		final ActionErrors errors = errors(context);
		final Map<String, Object> dyn = this.getDynamicAttribute();
		final Map<String, String[]> outputValues = outputValues(context);

		if (errors.hasFieldError(this.name)) {
			CubbyHelperFunctions.addClassName(dyn, "fieldError");
		}

		final Object[] value = multipleFormValues(context, outputValues,
				this.name);

		out.write("<select name=\"");
		out.write(this.name);
		out.write("\" ");
		out.write(CubbyHelperFunctions.toAttr(dyn));
		out.write(">\n");

		if (emptyOption) {
			out.write("<option value=\"\">");
			out.write(CubbyFunctions.out(emptyOptionLabel));
			out.write("</option>\n");
		}

		if (items.getClass().isArray()) {
			final OptionWriter optionWriter = new OptionWriter(
					new BeanItemAdaptor());
			for (final Object item : (Object[]) items) {
				optionWriter.write(out, item, value);
			}
		} else {
			final OptionWriter optionWriter;
			final Collection<?> collection;
			if (items instanceof Collection) {
				optionWriter = new OptionWriter(new BeanItemAdaptor());
				collection = (Collection<?>) items;
			} else if (items instanceof Map) {
				optionWriter = new OptionWriter(new EntryItemAdaptor());
				collection = ((Map<?, ?>) items).entrySet();
			} else {
				throw new JspTagException(MessageFormatter.getMessage(
						"ECUB1001", new Object[] { "items", items.getClass() }));
			}
			for (final Object item : collection) {
				optionWriter.write(out, item, value);
			}
		}

		out.write("</select>\n");
	}

	static class OptionWriter {

		private final ItemAdaptor itemAdaptor;

		OptionWriter(final ItemAdaptor itemAdaptor) {
			this.itemAdaptor = itemAdaptor;
		}

		public void write(final JspWriter out, final Object item,
				final Object value) throws IOException {
			out.write("<option value=\"");
			final String itemValue = DynamicAttributesTagSupport
					.toString(itemAdaptor.getItemValue(item));
			final String labelValue = DynamicAttributesTagSupport
					.toString(itemAdaptor.getLabelValue(item));
			out.write(CubbyFunctions.out(itemValue));
			out.write("\" ");
			out.write(selected(itemValue, value));
			out.write(">");
			out.write(CubbyFunctions.out(labelValue));
			out.write("</option>\n");
		}

		private String selected(final String value, final Object values) {
			if (value == null || values == null) {
				return "";
			}
			if (CubbyHelperFunctions.isChecked(value, values)) {
				return "selected=\"true\"";
			} else {
				return "";
			}
		}
	}

	interface ItemAdaptor {

		Object getItemValue(Object item);

		Object getLabelValue(Object item);

	}

	class BeanItemAdaptor implements ItemAdaptor {

		public BeanItemAdaptor() throws JspTagException {
			if (valueProperty == null) {
				throw new JspTagException(MessageFormatter.getMessage(
						"ECUB1002", new Object[] { "items", "valueProperty" }));
			}
		}

		public Object getItemValue(final Object item) {
			return property(item, valueProperty);
		}

		public Object getLabelValue(final Object item) {
			final Object labelValue;
			if (labelProperty == null) {
				labelValue = getItemValue(item);
			} else {
				labelValue = property(item, labelProperty);
			}
			return labelValue;
		}

		private Object property(final Object bean, final String propertyName) {
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean
					.getClass());
			final PropertyDesc propertyDesc = beanDesc
					.getPropertyDesc(propertyName);
			return propertyDesc.getValue(bean);
		}

	}

	class EntryItemAdaptor implements ItemAdaptor {

		public EntryItemAdaptor() {
			if (valueProperty != null) {
				logger.log("WCUB1001", new Object[] { "items",
						Map.class.getSimpleName(), "valueProperty",
						valueProperty,
						Entry.class.getSimpleName() + "#getKey()" });
			}
			if (labelProperty != null) {
				logger.log("WCUB1002", new Object[] { "items",
						Map.class.getSimpleName(), "labelProperty",
						labelProperty,
						Entry.class.getSimpleName() + "#getValue()" });
			}
		}

		public Object getItemValue(final Object item) {
			return ((Entry<?, ?>) item).getKey();
		}

		public Object getLabelValue(final Object item) {
			return ((Entry<?, ?>) item).getValue();
		}

	}

}
