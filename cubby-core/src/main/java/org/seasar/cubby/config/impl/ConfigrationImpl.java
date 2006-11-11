package org.seasar.cubby.config.impl;

import java.util.Map;

import org.seasar.cubby.config.Configration;

public class ConfigrationImpl implements Configration {
	
	private final Map<String, Object> config;
	
	public ConfigrationImpl(Map<String, Object> config) {
		this.config = config;
	}

	public Object getValue(String key) {
		return config.get(key);
	}	
}
