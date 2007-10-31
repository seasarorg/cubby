package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;

/**
 * inputを出力するタグ。
 * <p>
 * 入力検証にエラーがある場合、class属性に「fieldError」を追加します。
 * なおこのタグはtype属性により挙動が変わります。
 * </p>
 * <ul>
 * <li>type値がcheckbox - checkedValue値をvalue属性の値として出力します。value値とcheckedValueが一致した場合checked="checked"を出力します。</li>
 * <li>type値がradio - checkedValue値をvalue属性の値として出力します。value値とcheckedValueが一致した場合checked="checked"を出力します。</li>
 * <li>その他 - value値をvalue属性の値として出力します。</li>
 * </ul>
 * @author agata
 */
public class InputTag extends DynamicAttributesTagSupport {
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		final Map fieldErros = (Map) getJspContext().getAttribute("fieldErrors", PageContext.REQUEST_SCOPE);
		if (fieldErros.get(getDynamicAttribute().get("name")) != null) {
			CubbyHelperFunctions.addClassName(getDynamicAttribute(), "fieldError");
		}

		final JspWriter out = getJspContext().getOut();
		final Object form = getJspContext().getAttribute("__form", PageContext.REQUEST_SCOPE);
		if ("checkbox".equals(type) || "radio".equals(type)) {
			if (!getDynamicAttribute().containsKey("value")) {
				throw new JspException("'value' attribute required.");
			}
			final String value = toString(getDynamicAttribute().get("value"));
			final Object checkedValue = CubbyHelperFunctions.formMultiValue(getDynamicAttribute(), form, getJspContext(), "checkedValue");
			getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
			getJspContext().setAttribute("checkedValue", checkedValue, PageContext.PAGE_SCOPE);
			out.write("<input type=\"");
			out.write(type);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(value));// TODO
			out.write("\" ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write(" ");
			out.write(CubbyHelperFunctions.checked(value, checkedValue));
			out.write("/>\n");
		} else {
			final Object value = CubbyHelperFunctions.formValue(getDynamicAttribute(), form, getJspContext(), "value");
			//final Object checkedValue = getDynamicAttribute().get("checkedValue");
			getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
			//getJspContext().setAttribute("checkedValue", checkedValue, PageContext.PAGE_SCOPE);
			out.write("<input type=\"");
			out.write(type);
			out.write("\" value=\"");
			if (getDynamicAttribute().containsKey("value")) {
				out.write(CubbyFunctions.out(value));
			} else {
				out.write(CubbyHelperFunctions.convertFieldValue(value, form, getRequest(), toString(getDynamicAttribute().get("name"))));// TODO
			}
			out.write("\" ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write("/>\n");
		}
	}
}
