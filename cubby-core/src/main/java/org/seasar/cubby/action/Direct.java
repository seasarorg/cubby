package org.seasar.cubby.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.controller.ActionContext;

public class Direct implements ActionResult {

	private static Log LOG = LogFactory.getLog(Direct.class);

	private final String contentType;
	private final long lastModified;
	private final byte[] data;

	public Direct(byte[] data, String contentType, long lastModified) {
		this.data = data;
		this.contentType = contentType;
		this.lastModified = lastModified;
	}
	
	public void execute(ActionContext context) throws Exception {
		HttpServletResponse response = context.getResponse();
		response.setContentType(contentType);
		response.addDateHeader("Last-Modified", lastModified);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(data);
			out.flush();
		} catch (IOException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(e);
			}
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
