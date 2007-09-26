package org.seasar.cubby.helper.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;

public class FileDownloadHelperImplTest extends S2TestCase {

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testDownload() throws IOException {
		MockHttpServletRequest request = getRequest();
		MockHttpServletResponse response = getResponse();

		URL url = this.getClass().getResource("hello.txt");
		File file = new File(url.getFile());
		FileDownloadHelperImpl downloadHelper = new FileDownloadHelperImpl();
		downloadHelper.setContentType("text/html");
		downloadHelper.setFile(file);
		downloadHelper.download(request, response);

		for (Enumeration<?> e = response.getHeaderNames(); e.hasMoreElements();) {
			String headerName = (String) e.nextElement();
			System.out.println(headerName + ":" + response.getHeader(headerName));
		}

		assertEquals("text/html; charset=\"UTF-8\"", response.getContentType());
		assertEquals("attachment; filename=\"hello.txt\"", response.getHeader("Content-Disposition"));
	}
}
