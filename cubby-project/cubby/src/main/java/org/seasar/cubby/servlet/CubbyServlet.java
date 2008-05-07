/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.cubby.servlet;

import static java.lang.String.format;
import static org.seasar.cubby.util.CubbyUtils.escapeHtml;
import static org.seasar.framework.util.StringUtil.isEmpty;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.util.Messages;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.ResourceBundleUtil;

/**
 * Cubbyの管理コンソールを表示するサーブレットです。
 * <ul>
 * <li>ローカルホストのみから利用できます。</li>
 * <li>「http://localhost/(コンテキストパス)/cubbyservlet」でアクセスできます。</li>
 * <li>web.xmlに以下の設定を追加してください。</li>
 * </ul>
 * <code>
 *   <servlet>
 *     <servlet-name>cubbyservlet</servlet-name>
 *     <servlet-class>org.seasar.cubby.servlet.CubbyServlet</servlet-class>
 *   </servlet>
 *   ...
 *   <servlet-mapping>
 *     <servlet-name>cubbyservlet</servlet-name>
 *     <url-pattern>/cubbyservlet</url-pattern>
 *   </servlet-mapping>
 * </code>
 * @author agata
 * @since 1.0.2
 */
public class CubbyServlet extends HttpServlet {

	private static final long serialVersionUID = -8846604994208165731L;
	
	private static final String[] TABS = {"routingInfo" ,"regexpTool" ,"systemProperty"};

	private String css;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		css = getResource("cubby-admin.css");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String remoteHost = req.getRemoteAddr();
		if (!remoteHost.equals("127.0.0.1")) {
			return;
		}
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.println("<title>Cubby Admin Tool</title>");
		out.println("<style>");
		out.print(css);
		out.println("--></style>");
		out.println("<body>");
		out.println("<div id=\"banner\">");
		out.printf("<a class=\"right-menu\" target=\"_blank\" href=\"http://localhost:8082/\">%s</a>", 
				getText(req.getLocale(), "lbl.openH2"));
		out.println("<h1>Cubby Admin Tool</h1>");

		String tabId = req.getParameter("tab");
		tabId = isEmpty(tabId) ? TABS[0] : tabId;
		out.printf("<ul id=\"tabs\">\n");
		for (String tab : TABS) {
			if (tabId.equals(tab)) {
				out.printf("<li class=\"active\"><a href=\"?tab=%s\">%s</a></li>", 
						tab, getText(req.getLocale(), "tab." + tab));
			} else {
				out.printf("<li><a href=\"?tab=%s\">%s</a></li>", tab, getText(req.getLocale(), "tab." + tab));
			}
		}
		out.println("</ul>");
		out.println("</div>");
		out.println("<div id=\"main\">");
		if (tabId.equals("routingInfo")) {
			printRoutingInfoSection(req, out);
		} else if (tabId.equals("regexpTool")) {
			printRegexpToolSection(req, out);
		} else if (tabId.equals("systemProperty")) {
			printSystemPropertySection(req, out);
		}
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	private String getResource(String resourceName) throws ServletException {
		try {
			return new String(InputStreamUtil.getBytes(getClass().getResourceAsStream(resourceName)), "UTF-8");
		} catch (Exception e) {
			throw new ServletException("Can't read resouce file : " + resourceName, e);
		}
	}

	private void printRegexpToolSection(HttpServletRequest req, PrintWriter out) {
		String ptn = req.getParameter("ptn");
		String str = req.getParameter("str");
		out.printf("<form name=\"regexpForm\">\n");
		out.printf("<input type=\"hidden\"  name=\"tab\" value=\"regexpTool\" />\n");
		out.printf("%s<input type=\"text\" name=\"ptn\" size=\"40\" value=\"%s\">\n", 
				getText(req.getLocale(), "lbl.pattern"), escapeHtml(ptn));
		out.printf("%s<input type=\"text\" name=\"str\" size=\"40\" value=\"%s\">\n", 
				getText(req.getLocale(), "lbl.string"), escapeHtml(str));
		out.printf("<input type=\"button\" value=\"%s\" onclick=\"regexpForm.ptn.value='';regexpForm.str.value='';\">\n", 
				getText(req.getLocale(), "lbl.clear"));
		out.printf("<input type=\"submit\" value=\"%s\"><br/>\n", 
				getText(req.getLocale(), "lbl.test"));
		out.printf("%s%s<br/>\n", getText(req.getLocale(), "lbl.result"), 
				getRegexpResult(req, ptn, str));
		out.printf("</form>\n");
	}

	private String getRegexpResult(HttpServletRequest req, String ptn, String str) {
		if (isEmpty(ptn)) {
			return "";
		}
		Pattern pattern = Pattern.compile(ptn);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return format("<span class=\"true\">%s</span>", getText(req.getLocale(), "lbl.match"));
		} else {
			return format("<span class=\"false\">%s</span>", getText(req.getLocale(), "lbl.unmatch"));
		}
	}

	private void printRoutingInfoSection(HttpServletRequest req, PrintWriter out) {
		String path = req.getParameter("path");
		PathResolverImpl resolver = (PathResolverImpl)SingletonS2Container.getComponent(PathResolver.class);
		resolver.initialize();
		out.printf("<form name=\"pathSerachForm\">\n");
		out.printf("<input type=\"hidden\"  name=\"tab\" value=\"routingInfo\" />\n");
		out.printf("%s<input type=\"text\" name=\"path\" size=\"40\" value=\"%s\">\n", 
				getText(req.getLocale(), "lbl.path"), escapeHtml(path));
		out.printf("<input type=\"button\" value=\"%s\" onclick=\"pathSerachForm.path.value='';pathSerachForm.submit();\">\n", 
				getText(req.getLocale(), "lbl.clear"));
		out.printf("<input type=\"submit\" value=\"%s\">\n", getText(req.getLocale(), "lbl.test"));
		out.printf("</form>\n");
		out.printf("<table>\n");
		out.printf("<th>%s</th><th>%s</th><th>%s</th><th>%s</th><th>%s</th>\n", 
				getText(req.getLocale(), "lbl.no"),
				getText(req.getLocale(), "lbl.pathPattern"),
				getText(req.getLocale(), "lbl.requestMethod"),
				getText(req.getLocale(), "lbl.actionMethod"),
				getText(req.getLocale(), "lbl.pathParams"));
		int no = 1;
		for (Routing routing : resolver.getRoutings()) {
			if (isEmpty(path)) {
				out.printf("<tr class=\"%s\">\n", no % 2 == 0 ? "odd" : "even");
				out.printf("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td></td>\n", 
						no, 
						routing.getPattern().pattern(), 
						routing.getRequestMethod(),
						routing.getActionClass().getName() + "#" + routing.getMethod().getName());
				out.printf("</tr>\n");
			} else {
				final Matcher matcher = routing.getPattern().matcher(path);
				if (matcher.matches()) {
					final Map<String, String> uriParameters = new HashMap<String, String>();
					for (int i = 0; i < matcher.groupCount(); i++) {
						final String name = routing.getUriParameterNames().get(
								i);
						final String value = matcher.group(i + 1);
						uriParameters.put(name, value);
					}
					out.printf("<tr>\n");
					out.printf("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>\n", 
							no, 
							routing.getPattern().pattern(), 
							routing.getRequestMethod(),
							routing.getActionClass().getName() + "#" + routing.getMethod().getName(),
							uriParameters);
					out.printf("</tr>\n");
				}
			}
			no++;
		} 
		out.println("</table>\n");
	}

	private void printSystemPropertySection(HttpServletRequest req, PrintWriter out) {
		out.printf("<table>\n");
		out.printf("<th>%s</th><th>%s</th><th>%s</th>\n", 
				getText(req.getLocale(), "lbl.no"),
				getText(req.getLocale(), "lbl.key"),
				getText(req.getLocale(), "lbl.value"));
		int no = 1;
		Object[] keys = System.getProperties().keySet().toArray();
		Arrays.sort(keys);
		for (Object key : keys) {
			String value = System.getProperty((String)key);
			out.printf("<tr class=\"%s\">\n", no % 2 == 0 ? "odd" : "even");
			out.printf("<td>%s</td><td>%s</td><td>%s</td>\n", 
					no, escapeHtml(key), escapeHtml(value));
			out.printf("</tr>\n");
			no++;
		} 
		out.println("</table>\n");
	}

	private static String getText(Locale locale, String key, Object... args) {
		ResourceBundle bundle = ResourceBundleUtil.getBundle("cubby-admin-messages", locale);
		return Messages.getText(bundle, key, args);
	}
}
