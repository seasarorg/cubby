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
package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class MockJspContext extends PageContext {

	private MockServletContext servletContext = new MockServletContextImpl("cubby");
	private MockHttpServletRequest request = new MockHttpServletRequestImpl(servletContext, "/mock");
	private MockJspWriter writer = new MockJspWriter();
	private Map<Integer, Map<String, Object>> attributes = new HashMap<Integer, Map<String,Object>>();
	private int[] FIND_ATTRIBUTE_SEQ = { PageContext.PAGE_SCOPE, PageContext.REQUEST_SCOPE, PageContext.SESSION_SCOPE, PageContext.APPLICATION_SCOPE };
	
	public MockJspContext() {
		for (int scope : FIND_ATTRIBUTE_SEQ) {
			attributes.put(scope, new HashMap<String, Object>());
		}
	}
	
	public MockJspWriter getMockJspWriter() {
		return this.writer;
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
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
	public ExpressionEvaluator getExpressionEvaluator() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public JspWriter getOut() {
		// TODO 自動生成されたメソッド・スタブ
		return writer ;
	}

	@Override
	public VariableResolver getVariableResolver() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		attributes.get(scope).put(name, value);
	}

	@Override
	public void forward(String relativeUrlPath) throws ServletException,
			IOException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public Exception getException() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Object getPage() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ServletRequest getRequest() {
		return request;
	}

	@Override
	public ServletResponse getResponse() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void handlePageException(Exception e) throws ServletException,
			IOException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void handlePageException(Throwable t) throws ServletException,
			IOException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void include(String relativeUrlPath) throws ServletException,
			IOException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void include(String relativeUrlPath, boolean flush)
			throws ServletException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void initialize(Servlet servlet, ServletRequest request,
			ServletResponse response, String errorPageURL,
			boolean needsSession, int bufferSize, boolean autoFlush)
			throws IOException, IllegalStateException, IllegalArgumentException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void release() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}