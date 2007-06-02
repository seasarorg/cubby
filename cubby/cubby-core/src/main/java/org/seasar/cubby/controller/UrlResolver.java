package org.seasar.cubby.controller;

public interface UrlResolver {
	ActionMethod resolve(String url);
}
