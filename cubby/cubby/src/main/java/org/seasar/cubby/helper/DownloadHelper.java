package org.seasar.cubby.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Direct;

/**
 * ダウンロード処理のためのヘルパクラス。
 * <p>
 * {@link Direct} のパラメータにこのインスタンスを設定することで、レスポンスとしてダウンロード処理を実行します。
 * </p>
 * 
 * @see Direct
 * @author baba
 */
public interface DownloadHelper {

	/**
	 * ダウンロードを実行します。
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @throws IOException
	 *             ダウンロード処理に失敗した場合
	 */
	void download(HttpServletRequest request, HttpServletResponse response)
			throws IOException;

	void setupHeader(HttpServletRequest request, HttpServletResponse response)
			throws IOException;
}