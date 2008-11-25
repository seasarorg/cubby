package org.seasar.cubby.unit.mock.impl;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.seasar.cubby.unit.mock.MockHttpServletRequest;
import org.seasar.cubby.unit.mock.MockServletContext;
import org.seasar.cubby.unit.mock.MockUtils;

public class MockServletContextImpl implements MockServletContext {

	/**
	 * Major Version
	 */
	public static final int MAJOR_VERSION = 2;

	/**
	 * Minor Version
	 */
	public static final int MINOR_VERSION = 4;

	/**
	 * Server Info
	 */
	public static final String SERVER_INFO = "cubby/2.0";

	private String servletContextName;

	private final Map mimeTypes = new HashMap();

	private final Map initParameters = new HashMap();

	private final Map attributes = new HashMap();

	/**
	 * {@link MockServletContextImpl}を作成します。
	 * 
	 * @param path
	 */
	public MockServletContextImpl(String path) {
		if (path == null || path.charAt(0) != '/') {
			path = "/";
		}
		this.servletContextName = path;
	}

	/**
	 * @see javax.servlet.ServletContext#getContext(java.lang.String)
	 */
	public ServletContext getContext(String path) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.servlet.ServletContext#getMajorVersion()
	 */
	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	/**
	 * @see javax.servlet.ServletContext#getMinorVersion()
	 */
	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	/**
	 * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
	 */
	public String getMimeType(String file) {
		return (String) mimeTypes.get(file);
	}

	public void addMimeType(String file, String type) {
		mimeTypes.put(file, type);
	}

	/**
	 * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
	 */
	public Set getResourcePaths(String path) {
		path = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
		File src = MockUtils.getResourceAsFile(".");
		File root = src.getParentFile();
		if (root.getName().equalsIgnoreCase("WEB-INF")) {
			root = root.getParentFile();
		}
		File file = new File(root, adjustPath(path));
		if (!file.exists()) {
			int pos = path.lastIndexOf('/');
			if (pos != -1) {
				path = path.substring(pos + 1);
			}
			do {
				file = new File(root, path);
				root = root.getParentFile();
			} while (!file.exists() && root != null);
			path = "/" + path;
		}
		if (file.isDirectory()) {
			int len = file.getAbsolutePath().length();
			Set paths = new HashSet();
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; ++i) {
					paths.add(path
							+ files[i].getAbsolutePath().substring(len)
									.replace('\\', '/'));
				}
				return paths;
			}
		}
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#getResource(java.lang.String)
	 */
	public URL getResource(String path) throws MalformedURLException {
		if (path == null) {
			return null;
		}
		path = adjustPath(path);
		File src = MockUtils.getResourceAsFile(".");
		File root = src.getParentFile();
		if (root.getName().equalsIgnoreCase("WEB-INF")) {
			root = root.getParentFile();
		}
		while (root != null) {
			File file = new File(root, path);
			if (file.exists()) {
				return file.toURL();
			}
			root = root.getParentFile();
		}
		if (MockUtils.isExist(path)) {
			return MockUtils.getResource(path);
		}
		if (path.startsWith("WEB-INF")) {
			path = path.substring("WEB-INF".length());
			return getResource(path);
		}
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String path) {
		if (path == null) {
			return null;
		}
		path = adjustPath(path);
		if (MockUtils.isExist(path)) {
			return MockUtils.getResourceAsStream(path);
		}
		if (path.startsWith("WEB-INF")) {
			path = path.substring("WEB-INF".length());
			return getResourceAsStream(path);
		}
		return null;
	}

	/**
	 * パスを調整します。
	 * 
	 * @param path
	 *            パス
	 * @return 調整後のパス
	 */
	protected String adjustPath(String path) {
		if (path != null && path.length() > 0 && path.charAt(0) == '/') {
			return path.substring(1);
		}
		return path;
	}

	/**
	 * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
	 */
	public RequestDispatcher getRequestDispatcher(String path) {
		return MockUtils.getRequestDispatcher();
	}

	/**
	 * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
	 */
	public RequestDispatcher getNamedDispatcher(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#getServlet(java.lang.String)
	 */
	public Servlet getServlet(String name) throws ServletException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#getServlets()
	 */
	public Enumeration getServlets() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#getServletNames()
	 */
	public Enumeration getServletNames() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.servlet.ServletContext#log(java.lang.String)
	 */
	public void log(String message) {
		System.out.println(message);
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#log(java.lang.Exception,
	 *      java.lang.String)
	 */
	public void log(Exception ex, String message) {
		System.out.println(message);
		ex.printStackTrace();
	}

	/**
	 * @see javax.servlet.ServletContext#log(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void log(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace();
	}

	/**
	 * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
	 */
	public String getRealPath(String path) {
		// Servlet APIによると、リソースが無い場合はnullを返す。
		try {
			return MockUtils.getResource(adjustPath(path)).getFile();
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * @see javax.servlet.ServletContext#getServerInfo()
	 */
	public String getServerInfo() {
		return SERVER_INFO;
	}

	/**
	 * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String name) {
		return (String) initParameters.get(name);
	}

	/**
	 * @see javax.servlet.ServletContext#getInitParameterNames()
	 */
	public Enumeration getInitParameterNames() {
		return MockUtils.toEnumeration(initParameters.keySet().iterator());
	}

	public void setInitParameter(String name, String value) {
		initParameters.put(name, value);
	}

	/**
	 * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	/**
	 * @see javax.servlet.ServletContext#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return MockUtils.toEnumeration(attributes.keySet().iterator());
	}

	/**
	 * @see javax.servlet.ServletContext#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	/**
	 * @see javax.servlet.ServletContext#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	/**
	 * @see javax.servlet.ServletContext#getServletContextName()
	 */
	public String getServletContextName() {
		return servletContextName;
	}

	public void setServletContextName(final String servletContextName) {
		this.servletContextName = servletContextName;
	}

	public MockHttpServletRequest createRequest(String path) {
		String queryString = null;
		int question = path.indexOf('?');
		if (question >= 0) {
			queryString = path.substring(question + 1);
			path = path.substring(0, question);
		}
		MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(
				this, path);
		request.setQueryString(queryString);
		return request;
	}

	public Map getInitParameterMap() {
		return initParameters;
	}

}
