package org.seasar.cubby.action;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.framework.util.JSONSerializer;

public class Json extends AbstractActionResult {

	private Object bean;

	public Json(Object bean) {
		this.bean = bean;
	}

	public void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/javascript; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		String jsonResponse = JSONSerializer.serialize(bean);
		Writer writer = response.getWriter();
		writer.write(jsonResponse);
		writer.flush();
	}

}
