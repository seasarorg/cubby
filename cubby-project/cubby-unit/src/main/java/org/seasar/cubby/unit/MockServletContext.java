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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * プラグインを初期化するためのサーブレットコンテキストのモックです。
 * 
 * @author baba
 */
class MockServletContext implements ServletContext {

	private Hashtable<String, String> initParameters = new Hashtable<String, String>();

	private Hashtable<String, Object> attributes = new Hashtable<String, Object>();

	/**
	 * {@inheritDoc}
	 */
	public String getContextPath() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public ServletContext getContext(String uripath) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMajorVersion() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMinorVersion() {
		return 5;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getMimeType(String file) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Set getResourcePaths(String path) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public URL getResource(String path) throws MalformedURLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public InputStream getResourceAsStream(String path) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public RequestDispatcher getRequestDispatcher(String path) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public RequestDispatcher getNamedDispatcher(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public Servlet getServlet(String name) throws ServletException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getServlets() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getServletNames() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public void log(String msg) {
		System.out.println(msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void log(Exception exception, String msg) {
		log(msg, exception);
	}

	/**
	 * {@inheritDoc}
	 */
	public void log(String message, Throwable throwable) {
		System.out.println(message);
		throwable.printStackTrace();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRealPath(String path) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServerInfo() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getInitParameter(String name) {
		return initParameters.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return initParameters.keys();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		return attributes.keys();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAttribute(String name, Object object) {
		attributes.put(name, object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServletContextName() {
		throw new UnsupportedOperationException();
	}

}
