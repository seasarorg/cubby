package org.seasar.cubby.cubbitter.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dao.ImageDao;
import org.seasar.cubby.cubbitter.entity.Image;
import org.seasar.cubby.cubbitter.util.ImageUtils;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.FileRegexpValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Path("setting/picture")
public class SettingPictureAction extends AbstractAction {

	public ImageDao imageDao;

	@RequestParameter
	public FileItem file;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	public ValidationRules validationRules = new DefaultValidationRules(
			"setting.") {
		@Override
		public void initialize() {
			add("file", new RequiredValidator(), new FileRegexpValidator(
					".+\\.(?i)(png|jpg|gif)"));
		}
	};

	@Validation(rules = "validationRules", errorPage = "index.jsp")
	public ActionResult update() throws IOException {
		InputStream input = file.getInputStream();
		BufferedImage image = ImageIO.read(input);

		Image small = loginAccount.getSmallImage();
		if (small == null) {
			small = new Image();
			loginAccount.setSmallImage(small);
		}
		small.setData(ImageUtils.getResizedImageBytes(image, 24));

		Image medium = loginAccount.getMediumImage();
		if (medium == null) {
			medium = new Image();
			loginAccount.setMediumImage(medium);
		}
		medium.setData(ImageUtils.getResizedImageBytes(image, 48));

		Image large = loginAccount.getLargeImage();
		if (large == null) {
			large = new Image();
			loginAccount.setLargeImage(large);
		}
		large.setData(ImageUtils.getResizedImageBytes(image, 72));

		flash.put("notice", Messages.getText("setting.msg.updateSuccess"));
		return new Redirect(SettingPictureAction.class, "index");
	}

}
