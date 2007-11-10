package org.seasar.cubby.tags;

import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.cubby.validator.validators.TokenValidator;
import org.seasar.framework.util.StringUtil;

/**
 * 2重サブミット防止用の<input type="hidden"/>を出力するタグ。
 * <p>
 * このタグが呼び出されると一意なトークン文字列を生成してhiddenとセッションに格納します。
 * サブミットされた先の処理の検証フェーズで、ポストされたhidden値とセッション中の値を比較して、
 * 一致しない場合、不正な経路からのアクセスとみなしてエラー処理を行います。
 * </p>
 * 
 * @see TokenValidator#validate(org.seasar.cubby.validator.ValidationContext)
 * @author agata
 */
public class TokenTag extends DynamicAttributesTagSupport {

	private String name;

	public void setName(final String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		PageContext context = (PageContext) getJspContext();
		JspWriter out = context.getOut();

		String token = TokenHelper.generateGUID();
		out.append("<input type=\"hidden\" name=\"");
		if (StringUtil.isEmpty(name)) {
			out.append(TokenHelper.DEFAULT_TOKEN_NAME);
		} else {
			out.append(name);
		}
		out.append("\" value=\"");
		out.append(token);
		out.append("\" ");
		out.write(toAttr(getDynamicAttribute()));
		out.append("/>");
		HttpSession session = ThreadContext.getRequest().getSession();
		TokenHelper.setToken(session, token);
	}
}
