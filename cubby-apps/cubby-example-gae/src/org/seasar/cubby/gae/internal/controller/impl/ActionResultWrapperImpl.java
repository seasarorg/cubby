package org.seasar.cubby.gae.internal.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ActionResultWrapper;

public class ActionResultWrapperImpl implements ActionResultWrapper {
	/** アクションの実行結果。 */
	private final ActionResult actionResult;

	/** アクションコンテキスト。 */
	private final ActionContext actionContext;

	/**
	 * 指定されたアクションの実行結果をラップしたインスタンスを生成します。
	 * 
	 * @param actionResult
	 *            アクションの実行結果
	 * @param actionContext
	 *            アクションコンテキスト
	 */
	public ActionResultWrapperImpl(final ActionResult actionResult,
			final ActionContext actionContext) {
		super();
		this.actionResult = actionResult;
		this.actionContext = actionContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		actionResult.execute(actionContext, request, response);
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult getActionResult() {
		return actionResult;
	}

}
