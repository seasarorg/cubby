package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;
import org.seasar.framework.log.Logger;
import org.seasar.framework.message.MessageFormatter;

public class SelectTag extends DynamicAttributesTagSupport {

	final Logger logger = Logger.getLogger(this.getClass());

	private Object items;

	private String labelProperty;

	private String valueProperty;

	private boolean emptyOption = true;

	private String emptyOptionLabel;

	public Object getItems() {
		return items;
	}

	public void setItems(final Object items) {
		this.items = items;
	}

	public String getLabelProperty() {
		return labelProperty;
	}

	public void setLabelProperty(final String labelProperty) {
		this.labelProperty = labelProperty;
	}

	public String getValueProperty() {
		return valueProperty;
	}

	public void setValueProperty(final String valueProperty) {
		this.valueProperty = valueProperty;
	}

	public boolean isEmptyOption() {
		return emptyOption;
	}

	public void setEmptyOption(final boolean emptyOption) {
		this.emptyOption = emptyOption;
	}

	public String getEmptyOptionLabel() {
		return emptyOptionLabel;
	}

	public void setEmptyOptionLabel(final String emptyOptionLabel) {
		this.emptyOptionLabel = emptyOptionLabel;
	}

	@Override
	public void doTag() throws JspException, IOException {
		final Object form = getJspContext().getAttribute("__form",
				PageContext.REQUEST_SCOPE);
		final Object value = CubbyHelperFunctions.formValue2(
				getDynamicAttribute(), form, getJspContext(), "value");
		getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
		final Map<?, ?> fieldErros = (Map<?, ?>) getJspContext().getAttribute(
				"fieldErrors", PageContext.REQUEST_SCOPE);
		if (fieldErros.get(getDynamicAttribute().get("name")) != null) {
			CubbyHelperFunctions.addClassName(getDynamicAttribute(),
					"fieldError");
		}
		final JspWriter out = getJspContext().getOut();
		out.write("<select ");
		out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
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

	class OptionWriter {

		private final ItemAdaptor itemAdaptor;

		OptionWriter(final ItemAdaptor itemAdaptor) {
			this.itemAdaptor = itemAdaptor;
		}

		public void write(final JspWriter out, final Object item,
				final Object value) throws IOException {
			out.write("<option value=\"");
			final String itemValue = SelectTag.this.toString(itemAdaptor
					.getItemValue(item));
			final String labelValue = SelectTag.this.toString(itemAdaptor
					.getLabelValue(item));
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
			return CubbyHelperFunctions.property(item, valueProperty);
		}

		public Object getLabelValue(final Object item) {
			final Object labelValue;
			if (labelProperty == null) {
				labelValue = getItemValue(item);
			} else {
				labelValue = CubbyHelperFunctions.property(item, labelProperty);
			}
			return labelValue;
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
