/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.admin.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cubbyの管理コンソールを表示するサーブレットです。
 * <ul>
 * <li>ローカルホストのみから利用できます。</li>
 * <li>「http://localhost/(コンテキストパス)/cubby-admin」でアクセスできます。</li>
 * <li>web.xmlに以下の設定を追加してください。</li>
 * </ul>
 * <code>
 *   <servlet>
 *     <servlet-name>cubbyAdminServlet</servlet-name>
 *     <servlet-class>org.seasar.cubby.admin.servlet.CubbyAdminServlet</servlet-class>
 *   </servlet>
 *   ...
 *   <servlet-mapping>
 *     <servlet-name>cubbyAdminServlet</servlet-name>
 *     <url-pattern>/cubby-admin</url-pattern>
 *   </servlet-mapping>
 * </code>
 * 
 * @author agata
 * @since 1.1.0
 */
public class CubbyAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String css;

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		css = getResource("cubby-admin.css");
	}

	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		final String remoteAddr = request.getRemoteAddr();
		if (!isLoopBackAddress(remoteAddr)) {
			return;
		}
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		render(request, response);
	}

	private boolean isLoopBackAddress(final String remoteAddr) {
		// NOTE:
		// MacOSX 10.5 + Tomcat 6ではループバックアドレスが
		// 「0:0:0:0:0:0:0:1%0」となるため、startWithを使い判定を行う
		return remoteAddr.equals("127.0.0.1")
				|| remoteAddr.startsWith("0:0:0:0:0:0:0:1");
	}

	private void render(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		final PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out
				.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.println("<title>Cubby Admin Tool</title>");
		out.println("<style>");
		out.println(css);
		out.println("</style>");
		out.println("</head>");

		out.println("<body>");
		out.println("<div id=\"banner\">");
		out.println("<h1>Cubby Admin Tool</h1>");
		out.println("</div>");

		out.println("<div id=\"main\">");
		Section routingSection = new RoutingSection(request, response);
		routingSection.print();
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	private String getResource(final String resourceName)
			throws ServletException {
		try {
			return new String(getBytes(getClass().getResourceAsStream(
					resourceName)), "UTF-8");
		} catch (final IOException e) {
			throw new ServletException("Can't read resouce file : "
					+ resourceName, e);
		}
	}

	/**
	 * {@link InputStream}からbyteの配列を取得します。
	 * 
	 * @param is
	 * @return byteの配列
	 * @throws IORuntimeException
	 *             {@link IOException}が発生した場合
	 */
	public static final byte[] getBytes(InputStream is) throws IOException {
		byte[] bytes = null;
		byte[] buf = new byte[8192];
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, n);
			}
			bytes = baos.toByteArray();
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return bytes;
	}

}
