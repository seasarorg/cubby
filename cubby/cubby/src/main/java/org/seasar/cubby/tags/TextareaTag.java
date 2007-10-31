package org.seasar.cubby.tags;

import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.formValue;
import static org.seasar.cubby.tags.TagUtils.outputValues;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;

/**
 * textareaを出力するタグ
 * 
 * @author agata
 * @author baba
 */
public class TextareaTag extends DynamicAttributesTagSupport {

	private String name;

	private Object value;

	private Integer index;

	public void setName(final String name) {
		this.name = name;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public void setIndex(final Integer index) {
		this.index = index;
	}

	/**
	 * タグの処理
	 */
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
		final Object value = formValue(context, outputValues, this.name,
				this.index, this.value);

		out.write("<textarea name=\"");
		out.write(this.name);
		out.write("\" ");
		out.write(CubbyHelperFunctions.toAttr(dyn));
		out.write(">");
		out.write(CubbyFunctions.out(value));
		out.write("</textarea>\n");
	}
}
