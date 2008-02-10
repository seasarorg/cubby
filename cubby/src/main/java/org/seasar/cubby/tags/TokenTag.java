/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
 * @see TokenValidator#validate(org.seasar.cubby.validator.ValidationContext,
 *      Object[])
 * @author agata
 * @since 1.0.0
 */
public class TokenTag extends DynamicAttributesTagSupport {

	private String name;

	/**
	 * name属性を設定します。
	 * 
	 * @param name
	 *            name属性
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTag() throws JspException, IOException {
		final PageContext context = (PageContext) getJspContext();
		final JspWriter out = context.getOut();

		final String token = TokenHelper.generateGUID();
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
		final HttpSession session = ThreadContext.getRequest().getSession();
		TokenHelper.setToken(session, token);
	}
}
