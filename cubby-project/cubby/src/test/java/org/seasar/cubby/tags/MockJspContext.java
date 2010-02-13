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

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class MockJspContext extends PageContext {

	private ServletContext servletContext;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private JspWriter writer = new MockJspWriter();
	private Stack<JspWriter> outStack = new Stack<JspWriter>();
	private Map<Integer, Map<String, Object>> attributes = new HashMap<Integer, Map<String, Object>>();
	private int[] FIND_ATTRIBUTE_SEQ = { PageContext.PAGE_SCOPE,
			PageContext.REQUEST_SCOPE, PageContext.SESSION_SCOPE,
			PageContext.APPLICATION_SCOPE };

	public MockJspContext() {
		this(null, null, null);
	}

	public MockJspContext(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response) {
		for (int scope : FIND_ATTRIBUTE_SEQ) {
			attributes.put(scope, new HashMap<String, Object>());
		}
		this.servletContext = servletContext;
		this.request = request;
		this.response = response;
	}

	public MockJspWriter getMockJspWriter() {
		return (MockJspWriter) this.writer;
	}

	public String getResult() {
		return getMockJspWriter().getResult();
	}

	@Override
	public Object findAttribute(String name) {
		Object value = null;
		for (int scope : FIND_ATTRIBUTE_SEQ) {
			value = getAttribute(name, scope);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		return getAttribute(name, PageContext.PAGE_SCOPE);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		Map<String, Object> scopeMap = attributes.get(scope);
		return scopeMap != null ? scopeMap.get(name) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getAttributeNamesInScope(int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAttributesScope(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JspWriter getOut() {
		return writer;
	}

	@Override
	public void removeAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttribute(String name, int scope) {
		this.attributes.get(scope).remove(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		setAttribute(name, value, PageContext.PAGE_SCOPE);
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		attributes.get(scope).put(name, value);
	}

	@Override
	public void forward(String relativeUrlPath) throws ServletException,
			IOException {
	}

	@Override
	public Exception getException() {
		return null;
	}

	@Override
	public Object getPage() {
		return null;
	}

	@Override
	public ServletRequest getRequest() {
		return request;
	}

	@Override
	public ServletResponse getResponse() {
		return response;
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public HttpSession getSession() {
		return request.getSession();
	}

	@Override
	public void handlePageException(Exception e) throws ServletException,
			IOException {
	}

	@Override
	public void handlePageException(Throwable t) throws ServletException,
			IOException {
	}

	@Override
	public void include(String relativeUrlPath) throws ServletException,
			IOException {
	}

	@Override
	public void include(String relativeUrlPath, boolean flush)
			throws ServletException, IOException {
	}

	@Override
	public void initialize(Servlet servlet, ServletRequest request,
			ServletResponse response, String errorPageURL,
			boolean needsSession, int bufferSize, boolean autoFlush)
			throws IOException, IllegalStateException, IllegalArgumentException {
	}

	@Override
	public void release() {
	}

	public JspWriter popBody() {
		writer = outStack.pop();
		return writer;
	}

	public BodyContent pushBody() {
		outStack.push(writer);
		writer = new MockBodyContent(writer);
		return (BodyContent) writer;
	}

	@Override
	public ELContext getELContext() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("deprecation")
	@Override
	public javax.servlet.jsp.el.ExpressionEvaluator getExpressionEvaluator() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("deprecation")
	@Override
	public javax.servlet.jsp.el.VariableResolver getVariableResolver() {
		throw new UnsupportedOperationException();
	}

}
