package org.seasar.cubby.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.OutputStreamUtil;

public class Direct extends AbstractActionResult {

	private final String contentType;

	private final long lastModified;

	private final byte[] data;

	public Direct(byte[] data, String contentType, long lastModified) {
		this.data = data;
		this.contentType = contentType;
		this.lastModified = lastModified;
	}

	public void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(contentType);
		response.addDateHeader("Last-Modified", lastModified);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(data);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} finally {
			OutputStreamUtil.flush(out);
			OutputStreamUtil.close(out);
		}
	}
}
