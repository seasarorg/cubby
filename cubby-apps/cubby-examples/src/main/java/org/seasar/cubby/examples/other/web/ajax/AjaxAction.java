package org.seasar.cubby.examples.other.web.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public ActionResult textFromString() {
		response.setContentType("text/plain");
		try {
			response.getWriter().print("ajax text from string");
			response.flushBuffer();
		} catch (IOException e) {
			response.setStatus(500);
		}
		return new Direct();
	}

	/**
	 * Beanを元にJsonを返却
	 * @return
	 */
	public ActionResult jsonFromBean() {
		Bean bean = new Bean();
		bean.setText("ajax json from bean");
		return new Json(bean);
	}
	
	/**
	 * Mapを元にJsonを返却
	 * @return
	 */
	public ActionResult jsonFromMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "ajax json from map");
		return new Json(map);
	}
	
	/**
	 * Collectionを元にJsonを返却
	 * @return
	 */
	public ActionResult jsonFromCollection() {
		List<String> list = new ArrayList<String>();
		list.add("text1");
		list.add("text2");
		list.add("text3");
		return new Json(list);
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
