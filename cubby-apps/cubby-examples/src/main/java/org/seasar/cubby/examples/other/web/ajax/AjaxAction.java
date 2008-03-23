package org.seasar.cubby.examples.other.web.ajax;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Json;

/**
 * Ajaxのサンプル
 * @author agata
 */
public class AjaxAction extends Action {

	// ----------------------------------------------[DI Filed]

	public HttpServletResponse response;
	
	// ----------------------------------------------[Action Method]

	public ActionResult index() {
		return new Forward("index.jsp");
	}
	
	/**
	 * Textを返却
	 * @return
	 */
	public ActionResult text() {
		response.setContentType("text/plain");
		try {
			response.getWriter().print("ajax text result");
			response.flushBuffer();
		} catch (IOException e) {
			response.setStatus(500);
		}
		return new Direct();
	}

	/**
	 * Jsonを返却
	 * @return
	 */
	public ActionResult json() {
		Bean bean = new Bean();
		bean.setText("ajax json result");
		return new Json(bean);
	}
	
	public static class Bean {
		private String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
}
