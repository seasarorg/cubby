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
package org.seasar.cubby.admin.servlet;

import static org.seasar.cubby.util.CubbyUtils.escapeHtml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.container.Container;
import org.seasar.cubby.container.ContainerFactory;
import org.seasar.cubby.factory.PathResolverFactory;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;

class RoutingSection implements Section {

	private HttpServletRequest request;

	private PrintWriter out;

	private Locale locale;

	private Iterator<String> rowClasses = new LoopingIterator<String>(Arrays
			.asList(new String[] { "odd", "even" }));

	private Messages messages;

	public RoutingSection(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		this.request = request;
		this.out = response.getWriter();
		this.locale = request.getLocale();
		this.messages = new Messages(locale);
	}

	public void print() {
		final String path = request.getParameter("path");

		out.println("<form name=\"pathSerachForm\">");

		out.print("<label for=\"path\">");
		out.print(messages.getString("lbl.path"));
		out.print("</label>");
		out.print("<input type=\"text\" name=\"path\" size=\"40\" value=\"");
		out.print(escapeHtml(path));
		out.println("\" />");

		out.print("<input type=\"submit\" value=\"");
		out.print(messages.getString("lbl.test"));
		out.println("\">");

		out.print("<input type=\"button\" value=\"");
		out.print(messages.getString("lbl.clear"));
		out
				.println("\" onclick=\"pathSerachForm.path.value='';pathSerachForm.submit();\">");

		out.println("</form>");

		out.println("<table>");
		out.println("<thead>");
		out.println(th(messages.getString("lbl.no")));
		out.println(th(messages.getString("lbl.regexp")));
		out.println(th(messages.getString("lbl.requestMethod")));
		out.println(th(messages.getString("lbl.actionMethod")));
		out.println(th(messages.getString("lbl.pathParams")));
		out.println(th(messages.getString("lbl.priority")));
		out.println("</thead>");
		out.println("<tbody>");

		final Container container = ContainerFactory.getContainer();
		final PathResolverFactory pathResolverFactory = container
				.lookup(PathResolverFactory.class);
		final PathResolver pathResolver = pathResolverFactory.getPathResolver();

		int no = 1;
		for (final Routing routing : pathResolver.getRoutings().values()) {
			final boolean write;
			final Map<String, String> pathParameters;
			if (path == null || path.length() == 0) {
				write = true;
				pathParameters = null;
			} else {
				final Matcher matcher = routing.getPattern().matcher(path);
				if (matcher.matches()) {
					write = true;
					pathParameters = new HashMap<String, String>();
					for (int i = 0; i < matcher.groupCount(); i++) {
						final String name = routing.getUriParameterNames().get(
								i);
						final String value = matcher.group(i + 1);
						pathParameters.put(name, value);
					}
				} else {
					write = false;
					pathParameters = null;
				}
			}
			if (write) {
				out.print("<tr class=\"");
				out.print(rowClasses.next());
				out.println("\">");
				out.println(td(no));
				out.println(td(routing.getPattern().pattern()));
				out.println(td(routing.getRequestMethod()));
				out.println(td(routing.getActionClass().getName() + "#"
						+ routing.getMethod().getName()));
				if (pathParameters == null) {
					out.println(td(routing.getUriParameterNames()));
				} else {
					out.println(td(pathParameters));
				}
				out.println(td(routing.getPriority()));
				out.println("</tr>");
			}
			no++;
		}
		out.println("</tbody>");
		out.println("</table>");
	}

	// private String messages.getString(final String key, final Object... args)
	// {
	// final ResourceBundle bundle = ResourceBundleUtil.getBundle(
	// "messages", locale);
	// return Messages.messages.getString(bundle, key, args);
	// }

	private static String th(final Object body) {
		return "<th>" + String.valueOf(body) + "</th>";
	}

	private static String td(final Object body) {
		return "<td>" + String.valueOf(body) + "</td>";
	}

}
