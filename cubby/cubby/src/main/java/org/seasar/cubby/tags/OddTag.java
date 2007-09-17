package org.seasar.cubby.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.seasar.cubby.util.CubbyFunctions;

/**
 * 
 * @author agata
 * @deprecated いまいちなので廃止予定。
 * @see CubbyFunctions#odd(Integer, String)
 */
public class OddTag extends DynamicAttributesTagSupport {

	private int var;

	public void setVar(final int var) {
		this.var = var;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		if (var % 2 == 0) {
			getJspContext().getOut().write("odd");
		} else {
			getJspContext().getOut().write("even");
		}
	}
}
