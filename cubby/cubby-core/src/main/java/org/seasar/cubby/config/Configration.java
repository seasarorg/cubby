package org.seasar.cubby.config;

/**
 * 設定を取得するインターフェイスです。
 * @author agata
 * @since 1.0
 */
public interface Configration {
	
	/**
	 * 設定値を取得します。
	 * @param key キー
	 * @return 設定値
	 */
	Object getValue(String key);

}
