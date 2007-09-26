package org.seasar.cubby.helper.impl;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.helper.DownloadHelper;

/**
 * レスポンスヘッダの設定のみを行う {@link DownloadHelper}。
 * <p>
 * このクラスはレスポンスへの出力は行わないません。
 * {@link #setupHeader(HttpServletRequest, HttpServletResponse)} でレスポンスヘッダを設定後、{@link ServletResponse#getOutputStream()}
 * や {@link ServletResponse#getWriter()} を使用して、アクションメソッド中でレスポンスを出力してください。
 * </p>
 * 
 * @author baba
 */
public class DirectDownloadHelperImpl extends AbstractDownloadHelperImpl {

	public void download(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
	}

}
