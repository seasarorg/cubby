package org.seasar.cubby.tags;

import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.formValue;
import static org.seasar.cubby.tags.TagUtils.multipleFormValues;
import static org.seasar.cubby.tags.TagUtils.outputValues;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;
import org.seasar.framework.message.MessageFormatter;
import org.seasar.framework.util.ArrayUtil;

/**
 * inputを出力するタグ。
 * <p>
 * 入力検証にエラーがある場合、class属性に「fieldError」を追加します。 なおこのタグはtype属性により挙動が変わります。
 * </p>
 * <ul>
 * <li>type値がcheckbox/radio -
 * value値をvalue属性の値として出力します。フォームオブジェクトの値とvalueが一致した場合checked="checked"を出力します。</li>
 * <li>その他 - value値をvalue属性の値として出力します。</li>
 * </ul>
 * 
 * @author agata
 * @author baba
 */
public class InputTag extends DynamicAttributesTagSupport {

	private static final String[] MULTIPLE_VALUE_TYPES = new String[] {
			"checkbox", "radio" };

	private String type;

	private String name;

	private Object value;

	private String checkedValue;

	private Integer index;

	public void setType(final String type) {
		this.type = type;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setCheckedValue(final String checkedValue) {
		this.checkedValue = checkedValue;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public void setIndex(final Integer index) {
		this.index = index;
	}

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

		if (ArrayUtil.contains(MULTIPLE_VALUE_TYPES, this.type)) {
			if (this.value == null) {
				throw new JspTagException(MessageFormatter.getMessage(
						"ECUB1003", new Object[] {
								Arrays.deepToString(MULTIPLE_VALUE_TYPES),
								"value" }));
			}
			final Object[] values = multipleFormValues(context, outputValues,
					this.name, this.checkedValue);

			out.write("<input type=\"");
			out.write(type);
			out.write("\" name=\"");
			out.write(this.name);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(this.value));// TODO
			out.write("\" ");
			out.write(CubbyHelperFunctions.toAttr(dyn));
			out.write(" ");
			out.write(checked(toString(this.value), values));
			out.write("/>\n");
		} else {
			final Object value = formValue(context, outputValues, this.name,
					this.index, this.value);

			out.write("<input type=\"");
			out.write(type);
			out.write("\" name=\"");
			out.write(this.name);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(toString(value)));
			out.write("\" ");
			out.write(CubbyHelperFunctions.toAttr(dyn));
			out.write("/>\n");
		}
	}

	private static String checked(final String value, final Object values) {
		if (value == null || values == null) {
			return "";
		}
		if (CubbyHelperFunctions.isChecked(value, values)) {
			return "checked=\"true\"";
		} else {
			return "";
		}
	}

}
