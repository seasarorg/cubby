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
package org.seasar.cubby.unit;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.junit.Assert;

/**
 * {@link FilterConfig} のモック。
 * 
 * @author baba
 */
public class MockFilterConfig implements FilterConfig {

	private final ServletContext servletContext;

	private final String filterName;

	private final Properties initParameters = new Properties();

	/**
	 * Create a new MockFilterConfig.
	 * 
	 * @param servletContext
	 *            the ServletContext that the servlet runs in
	 */
	public MockFilterConfig(ServletContext servletContext) {
		this(servletContext, "");
	}

	/**
	 * Create a new MockFilterConfig.
	 * 
	 * @param servletContext
	 *            the ServletContext that the servlet runs in
	 * @param filterName
	 *            the name of the filter
	 */
	public MockFilterConfig(ServletContext servletContext, String filterName) {
		this.servletContext = (servletContext != null ? servletContext
				: new MockServletContext());
		this.filterName = filterName;
	}

	public String getFilterName() {
		return filterName;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void addInitParameter(String name, String value) {
		Assert.assertNotNull(name, "Parameter name must not be null");
		this.initParameters.setProperty(name, value);
	}

	public String getInitParameter(String name) {
		Assert.assertNotNull(name, "Parameter name must not be null");
		return this.initParameters.getProperty(name);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return this.initParameters.keys();
	}

}
