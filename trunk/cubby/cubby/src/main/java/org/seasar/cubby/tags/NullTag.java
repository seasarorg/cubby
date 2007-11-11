package org.seasar.cubby.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

/**
 * 何も出力しないタグ。
 * <p>
 * このタグは何も出力しません。body要素は削除されます。
 * </p>
 * 
 * @author agata
 * 
 */
public class NullTag extends DynamicAttributesTagSupport {
	@Override
	public void doTag() throws JspException, IOException {
	}
}
