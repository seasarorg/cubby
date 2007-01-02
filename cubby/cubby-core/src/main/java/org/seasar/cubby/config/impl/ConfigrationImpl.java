package org.seasar.cubby.config.impl;

import java.util.Map;

import org.seasar.cubby.config.Configration;

/**
 * 設定値を取得するクラスの実装クラスです。
 * @author agata
 * @since 1.0
 */
public class ConfigrationImpl implements Configration {
	
	private final Map<String, Object> config;
	
	/**
	 * コンストラクタ
	 * @param config 設定値がセットされたMap
	 */
	public ConfigrationImpl(final Map<String, Object> config) {
		this.config = config;
	}
	
	public Object getValue(final String key) {
		return config.get(key);
	}	
}
