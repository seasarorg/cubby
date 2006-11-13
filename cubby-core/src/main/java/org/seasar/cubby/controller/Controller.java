package org.seasar.cubby.controller;

import java.text.MessageFormat;
import java.util.Map;

import org.seasar.cubby.util.Messages;
import org.seasar.cubby.util.ParameterMap;


public abstract class Controller {
	protected ActionErrors errors;
	protected Map<String,Object> request;
	protected Map<String,Object> session;
	protected ParameterMap params;
	protected Map<String,Object> flash;

	public void initalize() {}
	
	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public Map<String, Object> getFlash() {
		return flash;
	}

	public void setFlash(Map<String, Object> flash) {
		this.flash = flash;
	}

	public ParameterMap getParams() {
		return params;
	}

	public void setParams(ParameterMap params) {
		this.params = params;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public String getText(String key) {
		return Messages.getString(key);
	}

	public String getText(String key, Object... args) {
		String text = getText(key);
		MessageFormat format = new MessageFormat(text);
		return format.format(args);
	}

}