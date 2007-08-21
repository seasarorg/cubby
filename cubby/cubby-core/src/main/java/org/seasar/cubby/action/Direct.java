package org.seasar.cubby.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.framework.log.Logger;

public class Direct implements ActionResult {

	private final Logger logger = Logger.getLogger(this.getClass());

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
			out.flush();
		} catch (IOException e) {
			if (logger.isInfoEnabled()) {
				logger.info(e);
			}
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
