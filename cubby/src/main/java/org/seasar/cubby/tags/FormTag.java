package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.seasar.cubby.util.CubbyHelperFunctions;

/**
 * フォームを出力するタグライブラリ。
 * <p>
 * {@link InputTag}, {@link SelectTag}, {@link TextareaTag}を保持することができます。
 * </p>
 * FIXME 親子関係の仕組みを改善したい。現在は__form決めうち
 * @author agata
 * 
 */
public class FormTag extends BodyTagSupport implements DynamicAttributes {

	private static final long serialVersionUID = 3997441280451382093L;

	/**
	 * DynamicAttributes
	 */
	private Map<String, Object> attrs = new HashMap<String, Object>();

	/**
	 * フォームのバインディング対象のBean
	 */
	private Object value;

	/**
	 * DynamicAttributeをセットします。
	 */
	public void setDynamicAttribute(final String uri, final String localName,
			final Object value) throws JspException {
		this.attrs.put(localName, value);
	}

	/**
	 * DynamicAttributeを取得します。
	 * @return DynamicAttribute
	 */
	protected Map<String, Object> getDynamicAttribute() {
		return this.attrs;
	}

	/**
	 * フォームのバインディング対象のBeanをセットします。
	 * @param value フォームのバインディング対象のBean
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * 開始タグ
	 */
	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute("__form", value, PageContext.REQUEST_SCOPE);
		JspWriter out = pageContext.getOut();
		try {
			out.write("<form ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write(">\n");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * 終了タグ
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</form>\n");
		} catch (IOException e) {
			throw new JspException(e);
		}
		pageContext.removeAttribute("__form", PageContext.REQUEST_SCOPE);
		return EVAL_PAGE;
	}
}
