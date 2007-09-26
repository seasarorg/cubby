package org.seasar.cubby.helper.impl;

import java.io.IOException;
import java.util.Enumeration;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;

public class ByteArrayDownloadHelperImplTest extends S2TestCase {

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testDownload() throws IOException {
		MockHttpServletRequest request = getRequest();
		MockHttpServletResponse response = getResponse();

		ByteArrayDownloadHelperImpl downloadHelper = new ByteArrayDownloadHelperImpl();
		downloadHelper.setContentType("text/html");
		downloadHelper.setFilename("filename");
		downloadHelper.setData("Hello World !".getBytes("UTF-8"));
		downloadHelper.download(request, response);

		assertEquals("text/html; charset=\"UTF-8\"", response.getContentType());
		assertEquals("attachment; filename=\"filename\"", response.getHeader("Content-Disposition"));
		for (Enumeration<?> e = response.getHeaderNames(); e.hasMoreElements();) {
			String headerName = (String) e.nextElement();
			System.out.println(headerName + ":" + response.getHeader(headerName));
		}
	}
}
