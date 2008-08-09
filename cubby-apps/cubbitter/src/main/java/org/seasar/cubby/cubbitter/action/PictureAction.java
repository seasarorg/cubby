package org.seasar.cubby.cubbitter.action;

/** 
 * アイコン表示用クラス
 */

import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.IOUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.dao.MemberDao;

public class PictureAction extends Action {

	// ----------------------------------------------[DI Filed]

	public MemberDao memberDao;

	public HttpServletResponse response;
	public ServletContext application;

	// ----------------------------------------------[Attribute]
	
	public int memberId;

	// ----------------------------------------------[Action Method]

	@Path("s/{memberId,[0-9a-zA-Z_]+}")
	public ActionResult small() throws Exception {

		byte image[] = memberDao.getSmallPicture(memberId);
		outputImage(image, "small.jpg");

		return new Direct();
	}

	@Path("m/{memberId,[0-9a-zA-Z_]+}")
	public ActionResult medium() throws Exception {

		byte image[] = memberDao.getMediumPicture(memberId);
		outputImage(image, "medium.jpg");
		return new Direct();
	}

	@Path("l/{memberId,[0-9a-zA-Z_]+}")
	public ActionResult large() throws Exception {

		byte image[] = memberDao.getLargePicture(memberId);
		outputImage(image, "large.jpg");
		return new Direct();
	}

	// ----------------------------------------------[Private Method]
	
	/** HTTPレスポンスに画像を出力 */
	private void outputImage(byte[] image, String defaultImageFileName)
			throws Exception {
		// ヘッダ設定
		response.setContentType("image/jpeg");

		// 画像を出力
		ServletOutputStream os = response.getOutputStream();
		if (image != null && image.length > 0) {
			os.write(image);
		} else {
			String fileName = application.getRealPath("image/"
					+ defaultImageFileName);
			FileInputStream is = new FileInputStream(fileName);
			IOUtils.copy(is, os);
		}
		os.close();
	}
}
