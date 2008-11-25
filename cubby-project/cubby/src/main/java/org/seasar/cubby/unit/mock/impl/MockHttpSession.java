package org.seasar.cubby.unit.mock.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.seasar.cubby.unit.mock.MockUtils;


public class MockHttpSession implements HttpSession {

    private static final long serialVersionUID = 1L;

    private final long creationTime = System.currentTimeMillis();

    private long lastAccessedTime = creationTime;

    private ServletContext servletContext;

    private String id;

    private boolean new_ = true;

    private boolean valid = true;

    private int maxInactiveInterval = -1;

    private Map attributes = new HashMap();

    /**
     * {@link MockHttpSession}を作成します。
     * 
     * @param servletContext
     */
    public MockHttpSession(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.id = MockUtils.uuid();
    }

    /**
     * @see javax.servlet.http.HttpSession#getCreationTime()
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * @see javax.servlet.http.HttpSession#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     */
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void access() {
        new_ = false;
        lastAccessedTime = System.currentTimeMillis();
    }

    /**
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     */
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    /**
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     */
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    /**
     * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
     *      java.lang.Object)
     */
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    /**
     * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    public javax.servlet.http.HttpSessionContext getSessionContext() {
        return null;
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    public Object getValue(String name) {
        return getAttribute(name);
    }

    /**
     * @see javax.servlet.http.HttpSession#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        return MockUtils.toEnumeration(attributes.keySet().iterator());
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    public String[] getValueNames() {
        return (String[]) attributes.keySet().toArray(
                new String[attributes.size()]);
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
     *      java.lang.Object)
     */
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    public void removeValue(String name) {
        removeAttribute(name);
    }

    /**
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    public void invalidate() {
        if (!valid) {
            return;
        }
        attributes.clear();
        valid = false;
    }

    /**
     * @see javax.servlet.http.HttpSession#isNew()
     */
    public boolean isNew() {
        return new_;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
