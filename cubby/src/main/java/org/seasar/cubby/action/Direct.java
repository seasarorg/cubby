package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.helper.DownloadHelper;
import org.seasar.cubby.helper.impl.ByteArrayDownloadHelperImpl;

/**
 * アクションメソッドから直接レスポンスを返すことを示す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定すると、以下のような動作になります。
 * <ul>
 * <li> コンストラクタに何も指定しない場合
 * <p>
 * 後続の処理はレスポンスに何も出力しません。
 * アクションメソッド中でレスポンスを出力してください。
 * </p>
 * </li>
 * <li> コンストラクタに {@link DownloadHelper} を指定した場合
 * <p>
 * {@link DownloadHelper#download(HttpServletRequest, HttpServletResponse)}
 * によるダウンロード処理をレスポンスとします。
 * </p>
 * </li>
 * </ul>
 * <p>
 * また、指定したデータをダウンロードさせるためのコンストラクタも用意されています。
 * </p>
 * 
 * @author baba
 */
public class Direct extends AbstractActionResult {

	private DownloadHelper downloadHelper;

	/**
	 * インスタンスを生成します。
	 */
	public Direct() {
		this.downloadHelper = null;
	}

	/**
	 * アクションメソッド終了後に、指定された {@link DownloadHelper} によってデータをダウンロードさせるコンストラクタです。
	 * 
	 * @param downloadHelper
	 *            ダウンロードヘルパ
	 */
	public Direct(final DownloadHelper downloadHelper) {
		this.downloadHelper = downloadHelper;
	}

	/**
	 * アクションメソッド終了後に、指定されたデータをダウンロードさせるコンビニエンスコンストラクタです。
	 * 
	 * @param data
	 *            ダウンロードさせるデータ
	 * @param contentType
	 *            ダウンロードさせるデータのコンテントタイプ
	 * @param lastModified
	 *            ダウンロードさせるデータの最終更新時刻
	 */
	public Direct(final byte[] data, final String contentType,
			final long lastModified) {
		final ByteArrayDownloadHelperImpl downloadHelper = new ByteArrayDownloadHelperImpl();
		downloadHelper.setData(data);
		downloadHelper.setContentType(contentType);
		downloadHelper.setLastModified(lastModified);
		this.downloadHelper = downloadHelper;
	}

	public void execute(final ActionContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		if (this.downloadHelper != null) {
			this.downloadHelper.download(request, response);
		}
	}

}
