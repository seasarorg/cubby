package org.seasar.cubby.examples.whiteboard;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.seasar.cubby.action.Action;

public class BaseAction extends Action {
	private ServletContext context;
	
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Page> getPages() {
		Map<String, Page> pages = (Map<String, Page>) context.getAttribute("pages");
		if (pages == null) {
			pages = new HashMap<String, Page>();
			context.setAttribute("pages", pages);
		}
		return pages;
	}


}
