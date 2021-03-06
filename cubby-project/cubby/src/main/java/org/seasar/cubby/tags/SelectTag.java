/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.seasar.cubby.tags;

import static org.seasar.cubby.internal.util.LogMessages.format;
import static org.seasar.cubby.tags.TagUtils.addCSSClassName;
import static org.seasar.cubby.tags.TagUtils.contains;
import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.getFormWrapper;
import static org.seasar.cubby.tags.TagUtils.multipleFormValues;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.controller.FormWrapper;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>&lt;select&gt;</code> タグを出力します。
 * 
 * @author agata
 * @author baba
 */
public class SelectTag extends DynamicAttributesSimpleTagSupport {

	/** <code>name</code> 属性。 */
	private String name;

	/** <code>&lt;option&gt;</code> 要素のリスト。 */
	private Object items;

	/** <code>option</code> のラベルのプロパティ名。 */
	private String labelProperty;

	/** <code>option</code> の値のプロパティ名。 */
	private String valueProperty;

	/** 空の <code>&lt;option&gt;</code> 要素を出力するかどうか。 */
	private Boolean emptyOption = Boolean.TRUE;

	/** 空の <code>&lt;option&gt;</code> 要素を出力した場合のラベル文字列。 */
	private String emptyOptionLabel;

	/**
	 * <code>&lt;option&gt;</code> 要素のリストを設定します。
	 * 
	 * @param items
	 *            <code>&lt;option&gt;</code> 要素のリスト
	 */
	public void setItems(final Object items) {
		this.items = items;
	}

	/**
	 * <code>items</code> から <code>&lt;option&gtl</code> 要素をのラベルを取得する時の名前を設定します。
	 * 
	 * @param labelProperty
	 *            <code>items</code> から <code>&lt;option&gtl</code>
	 *            要素をのラベルを取得する時の名前
	 */
	public void setLabelProperty(final String labelProperty) {
		this.labelProperty = labelProperty;
	}

	/**
	 * <code>items</code> から <code>&lt;option&gtl</code> 要素をの値を取得する時の名前を設定します。
	 * 
	 * @param valueProperty
	 *            <code>items</code> から <code>&lt;option&gtl</code>
	 *            要素をの値を取得する時の名前
	 */
	public void setValueProperty(final String valueProperty) {
		this.valueProperty = valueProperty;
	}

	/**
	 * 空の <code>&lt;option&gtl</code> 要素を出力するかどうかを設定します。
	 * 
	 * @param emptyOption
	 *            空の <code>&lt;option&gtl</code> 要素を出力するかどうか
	 */
	public void setEmptyOption(final Boolean emptyOption) {
		this.emptyOption = emptyOption;
	}

	/**
	 * 空の <code>&lt;option&gtl</code> 要素を出力した場合のラベル文字列を設定します。
	 * 
	 * @param emptyOptionLabel
	 *            空の <code>&lt;option&gtl</code> 要素を出力した場合のラベル文字列
	 */
	public void setEmptyOptionLabel(final String emptyOptionLabel) {
		this.emptyOptionLabel = emptyOptionLabel;
	}

	/**
	 * <code>name</code> 属性を設定します。
	 * 
	 * @param name
	 *            <code>name</code> 属性
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if (items == null) {
			throw new JspTagException(format("ECUB1005"));
		}
		final JspContext context = this.getJspContext();
		final JspWriter out = context.getOut();
		final ActionErrors errors = errors(context);
		final Map<String, Object> dyn = this.getDynamicAttributes();
		final FormWrapper formWrapper = getFormWrapper(this);

		if (!errors.getFields().get(this.name).isEmpty()) {
			addCSSClassName(dyn, "fieldError");
		}

		final Object[] value = multipleFormValues(context, formWrapper,
				this.name);

		out.write("<select name=\"");
		out.write(this.name);
		out.write("\" ");
		out.write(toAttr(dyn));
		out.write(">\n");

		if (emptyOption) {
			out.write("<option value=\"\">");
			out.write(CubbyFunctions.out(emptyOptionLabel));
			out.write("</option>\n");
		}

		if (items != null && items.getClass().isArray()) {
			final OptionWriter optionWriter = new OptionWriter(
					new BeanItemAdaptor());
			for (final Object item : (Object[]) items) {
				optionWriter.write(out, item, value);
			}
		} else {
			final OptionWriter optionWriter;
			final Collection<?> collection;
			if (items instanceof Collection<?>) {
				optionWriter = new OptionWriter(new BeanItemAdaptor());
				collection = (Collection<?>) items;
			} else if (items instanceof Map<?, ?>) {
				optionWriter = new OptionWriter(new EntryItemAdaptor());
				collection = ((Map<?, ?>) items).entrySet();
			} else {
				throw new JspTagException(format("ECUB1001", "items",
						items == null ? null : items.getClass()));
			}
			for (final Object item : collection) {
				optionWriter.write(out, item, value);
			}
		}

		out.write("</select>");
	}

	private static class OptionWriter {

		private final ItemAdaptor itemAdaptor;

		OptionWriter(final ItemAdaptor itemAdaptor) {
			this.itemAdaptor = itemAdaptor;
		}

		void write(final JspWriter out, final Object item, final Object value)
				throws IOException {
			out.write("<option value=\"");
			final String itemValue = TagUtils.toString(itemAdaptor
					.getItemValue(item));
			final String labelValue = TagUtils.toString(itemAdaptor
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
			if (contains(values, value)) {
				return "selected=\"selected\"";
			} else {
				return "";
			}
		}
	}

	private interface ItemAdaptor {

		/**
		 * 要素の値を取得します。
		 * 
		 * @param item
		 *            要素
		 * @return 要素の値
		 */
		Object getItemValue(Object item);

		/**
		 * 要素のラベルを取得します。
		 * 
		 * @param item
		 *            要素
		 * @return 要素のラベル
		 */
		Object getLabelValue(Object item);

	}

	private class BeanItemAdaptor implements ItemAdaptor {

		BeanItemAdaptor() throws JspTagException {
			if (valueProperty == null) {
				throw new JspTagException(format("ECUB1002", "items",
						"valueProperty"));
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getItemValue(final Object item) {
			return property(item, valueProperty);
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getLabelValue(final Object item) {
			final Object labelValue;
			if (labelProperty == null) {
				labelValue = getItemValue(item);
			} else {
				labelValue = property(item, labelProperty);
			}
			return labelValue;
		}

		/**
		 * 指定されたオブジェクトからプロパティの値を取得します。
		 * 
		 * @param bean
		 *            値を取得するオブジェクト
		 * @param propertyName
		 *            プロパティ名
		 * @return プロパティの値
		 */
		private Object property(final Object bean, final String propertyName) {
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean
					.getClass());
			final Attribute attribute = beanDesc
					.getPropertyAttribute(propertyName);
			return attribute.getValue(bean);
		}

	}

	private class EntryItemAdaptor implements ItemAdaptor {

		EntryItemAdaptor() {
			if (valueProperty != null) {
				final Logger logger = LoggerFactory.getLogger(SelectTag.class);
				if (logger.isWarnEnabled()) {
					logger.warn(format("WCUB1001", "items", Map.class
							.getSimpleName(), "valueProperty", valueProperty,
							Entry.class.getSimpleName() + "#getKey()"));
				}
			}
			if (labelProperty != null) {
				final Logger logger = LoggerFactory.getLogger(SelectTag.class);
				if (logger.isWarnEnabled()) {
					logger.warn(format("WCUB1002", "items", Map.class
							.getSimpleName(), "labelProperty", labelProperty,
							Entry.class.getSimpleName() + "#getValue()"));
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getItemValue(final Object item) {
			return ((Entry<?, ?>) item).getKey();
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getLabelValue(final Object item) {
			return ((Entry<?, ?>) item).getValue();
		}

	}

}
