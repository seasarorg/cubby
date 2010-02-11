/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.tags;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import org.easymock.IAnswer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.seasar.cubby.mock.MockActionErrors;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public abstract class AbstractTagTestCase {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	protected MockJspFragment jspBody;

	protected MockJspContext context;

	private ServletContext servletContext;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	public AbstractTagTestCase() {
		super();
	}

	@Before
	public void setupMocks() throws Exception {
		servletContext = createMock(ServletContext.class);
		response = createMock(HttpServletResponse.class);
		final Hashtable<String, Object> attributes = new Hashtable<String, Object>();
		session = createMock(HttpSession.class);
		expect(session.getAttribute(isA(String.class))).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		session.setAttribute(isA(String.class), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				return attributes.put(String.class
						.cast(getCurrentArguments()[0]),
						getCurrentArguments()[1]);
			}

		});

		request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
		expect(request.getSession()).andStubReturn(session);
		expect(response.encodeURL(isA(String.class))).andStubAnswer(
				new IAnswer<String>() {

					public String answer() throws Throwable {
						return String.class.cast(getCurrentArguments()[0]);
					}

				});
		expect(request.getRequestURL()).andStubReturn(
				new StringBuffer("http://localhost:8080/test/test"));
		replay(request, response, session, servletContext);

		context = new MockJspContext(servletContext, request, response);
		jspBody = new MockJspFragment();
		jspBody.setJspContext(context);
	}

	@Before
	public void setupContainer() {
		final BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						return null;
					}

				}));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardownPluginRegistry() {
		pluginRegistry.clear();
	}

	protected Element getResultAsElementFromContext() throws JDOMException,
			IOException {
		String result = context.getResult();
		Document document = new SAXBuilder().build(new StringReader(result));
		Element element = document.getRootElement();
		return element;
	}

	protected void setupErrors(JspContext context) {
		MockActionErrors errors = new MockActionErrors();
		context.setAttribute("errors", errors, PageContext.REQUEST_SCOPE);
	}

}