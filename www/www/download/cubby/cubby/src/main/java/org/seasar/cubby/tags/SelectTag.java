package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;

public class SelectTag extends DynamicAttributesTagSupport {
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		final Object form = getJspContext().getAttribute("__form", PageContext.REQUEST_SCOPE);
		final Object value = CubbyHelperFunctions.formValue2(getDynamicAttribute(), form, getJspContext(), "value");
		getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
		final Map fieldErros = (Map) getJspContext().getAttribute("fieldErrors", PageContext.REQUEST_SCOPE);
		if (fieldErros.get(getDynamicAttribute().get("name")) != null) {
			CubbyHelperFunctions.addClassName(getDynamicAttribute(), "fieldError");
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
		for (Object item : CubbyHelperFunctions.toArray(items)) {
			out.write("<option value=\"");
			String itemValue = toString(CubbyHelperFunctions.property(item, valueProperty));
			String labelValue = labelProperty == null ? itemValue : toString(CubbyHelperFunctions.property(item, labelProperty));
			out.write(CubbyFunctions.out(itemValue));
			out.write("\" ");
			out.write(CubbyHelperFunctions.selected(itemValue, value));
			out.write(">");
			out.write(CubbyFunctions.out(labelValue));
			out.write("</option>\n");
		}
		out.write("</select>\n");
	}
}
