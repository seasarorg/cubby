package org.seasar.cubby.action;

import org.seasar.cubby.controller.ActionContext;

/**
 * {@link ActionResult} の抽象クラス。
 * <p>
 * 空の {@link ActionResult#prerender(ActionContext)} を実装します。
 * </p>
 * 
 * @author baba
 */
abstract class AbstractActionResult implements ActionResult {

	/**
	 * 何も行いません。
	 */
	public void prerender(final ActionContext context) {
	}

}
