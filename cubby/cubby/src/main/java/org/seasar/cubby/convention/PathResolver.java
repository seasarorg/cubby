package org.seasar.cubby.convention;

import org.seasar.cubby.filter.RequestRoutingFilter;

/**
 * {@link RequestRoutingFilter} がフォワードするための情報を抽出するクラス。
 * 
 * @author baba
 */
public interface PathResolver {

	/**
	 * 指定されたパスからフォワードするための情報を抽出します。
	 * @param path パス
	 * @return フォワード情報
	 */
	ForwardInfo getForwardInfo(String path);

}