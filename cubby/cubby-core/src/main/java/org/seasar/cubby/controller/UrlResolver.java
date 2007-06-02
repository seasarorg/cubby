package org.seasar.cubby.controller;

public interface UrlResolver {
	void initialize();
	ResolveResult resolve(String url);
}
