package org.seasar.cubby.cubbitter.action;

/** 設定画面用Actionクラス */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;


import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.CheckResultDto;
import org.seasar.cubby.cubbitter.dto.SettingDto;
import org.seasar.cubby.cubbitter.dxo.SettingDxo;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.EmailValidator;
import org.seasar.cubby.validator.validators.FileRegexpValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RangeLengthValidator;
import org.seasar.cubby.validator.validators.RegexpValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.framework.aop.annotation.InvalidateSession;
import org.seasar.framework.util.StringUtil;

public class SettingAction extends DefaultMenuAction {

	// ----------------------------------------------[DI Filed]

	public MemberDao memberDao;
	public CommentDao commentDao;

	public SettingDto settingDto;
	public SettingDxo settingDxo;

	// ----------------------------------------------[Attribute]
	
	public String memberName; // 重複チェック用
	public FileItem file; // アイコンファイル
	public String password;// 更新パスワード
	public String password2;// 確認パスワード

	// ----------------------------------------------[Override]

	@Override
	public void prerender() {
		setMainMenuData();
	}

	// ----------------------------------------------[Action Method]

	/** 初期表示 */
	public ActionResult index() {
		return new Forward("index.jsp");
	}

	/** 更新 */
	@Form("settingDto")
	@Validation(rules = "settingValidation", errorPage = "index.jsp")
	public ActionResult update() {
		Member user = (Member) sessionScope.get("user");
		settingDto.setLocale("ja");
		settingDxo.convert(settingDto, user);

		if (memberDao.updateMember(user) > 0) {
			flash.put("notice", Messages.getText("setting.msg.updateSuccess"));
		} else {
			flash.put("notice", Messages.getText("setting.msg.updateFailure"));
		}

		return new Redirect("./");
	}

	/** 自分を削除 */
	@InvalidateSession
	public ActionResult delete() {
		List<Integer> commentIdList = commentDao
				.getMemberCommentIdList(userMemberId);
		commentDao.deleteCommentList(userMemberId, commentIdList);
		memberDao.deleteMemberById(userMemberId);

		return new Forward("delete.jsp");
	}

	/** チェック */
	@Path("checkName/{memberName,[0-9a-zA-Z_]+}")
	public ActionResult checkName() throws IOException {

		Member user = memberDao.getMemberByName(memberName);

		CheckResultDto dto = new CheckResultDto();
		if (user == null || user.getMemberId() == userMemberId) {
			dto.isError = false;
		} else {
			dto.isError = true;
			dto.errorMessage = Messages.getText("checkMemberName.msg.error",
					memberName);
		}

		return new Json(dto);
	}

	/** パスワード設定初期表示 */
	public ActionResult password() {
		return new Forward("password.jsp");
	}

	/** パスワード更新 */
	@Validation(rules = "passwordValidation", errorPage = "password.jsp")
	public ActionResult updatePassword() {

		if (memberDao.updatePassword(password, userMemberId) > 0) {
			flash.put("notice", Messages.getText("setting.msg.updateSuccess"));
		} else {
			flash.put("notice", Messages.getText("setting.msg.updateFailure"));
		}

		return new Forward("password.jsp");

	}

	/** 写真初期表示 */
	public ActionResult picture() {
		return new Forward("picture.jsp");
	}

	/** 写真アップロード */
	@Validation(rules = "uploadValidation", errorPage = "picture.jsp")
	public ActionResult uploadPicture() throws Exception {
		InputStream is = file.getInputStream();
		 BufferedImage image = ImageIO.read(is);

		// byte配列の場合
		if (memberDao.updatePictures(getResizedImageBytes(image, 24),
				getResizedImageBytes(image, 48), getResizedImageBytes(image, 73),
				userMemberId) > 0) {
			flash.put("notice", Messages.getText("setting.msg.updateSuccess"));
		} else {
			flash.put("notice", Messages.getText("setting.msg.updateFailure"));
		}

		return new Forward("picture.jsp");
	}

	// ----------------------------------------------[Validation]

	public ValidationRules settingValidation = new DefaultValidationRules(
			"setting.") {
		@Override
		public void initialize() {
			add("memberName", new RequiredValidator(), new MaxLengthValidator(
					15), new RegexpValidator("^[0-9a-zA-Z_]+$"),
					new MemberNameValidationRule());
			add("email", new RequiredValidator(), new MaxLengthValidator(50),
					new EmailValidator());
			add("fullName", new RequiredValidator(), new MaxLengthValidator(40));
			add("web", new MaxLengthValidator(100), new RegexpValidator(
					"(https?://[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+)?"));
			add("biography", new MaxLengthValidator(200));
			add("location", new MaxLengthValidator(100));

		}
	};

	public class MemberNameValidationRule implements ScalarFieldValidator {
		private final MessageHelper messageHelper;

		public MemberNameValidationRule() {
			this.messageHelper = new MessageHelper("valid.existMemberName");
		}

		public void validate(final ValidationContext context, final Object value) {
			if (value == null || !(value instanceof String)) {
				return;
			}

			final String str = (String) value;
			if (StringUtil.isEmpty(str)) {
				return;
			}
			Member user = memberDao.getMemberByName(str);

			if (user != null && user.getMemberId() != userMemberId) {
				context.addMessageInfo(this.messageHelper
						.createMessageInfo(str));
			}
		}
	}

	public ValidationRules uploadValidation = new DefaultValidationRules(
			"setting.") {
		@Override
		public void initialize() {
			add("file", new RequiredValidator(), new FileRegexpValidator(
					".+\\.(?i)(png|jpg|gif)"));
		}
	};

	public ValidationRules passwordValidation = new DefaultValidationRules(
			"setting.") {
		@Override
		public void initialize() {
			add("password", new RequiredValidator(), new RangeLengthValidator(
					6, 20));
			add(new ValidationRule() {

				public void apply(Map<String, Object[]> params, Object form,
						ActionErrors errors) {
					if (password != null) {
						if (!password.equals(password2)) {
							errors.add(Messages.getText("setting.msg.verifyPasswordError"));
						}
					}
				}

			});
		}
	};

	// ----------------------------------------------[Private]
	
	/** imageを縦横sizeピクセルにリサイズ、カットした画像配列を取得 */
	private byte[] getResizedImageBytes(BufferedImage image, int size) throws Exception {
		int width = image.getWidth();
		int height = image.getHeight();

		int resizeWidth, resizeHeight, offsetX = 0, offsetY = 0;

		// 縦長の場合
		if (width < height) {
			// 横幅を基準に縮小
			resizeWidth = size;
			resizeHeight = -1;

			// 縦方向オフセット
			offsetY = (int) ((height - width) * size / (double) width / 2.0);

		}
		// 横長の場合
		else {
			// 高さを基準に縮小
			resizeWidth = -1;
			resizeHeight = size;

			// 横方向オフセット
			offsetX = (int) ((width - height) * size / (double) height / 2.0);
		}

		// 縮小
		Image resizedImage = image.getScaledInstance(resizeWidth, resizeHeight,
				Image.SCALE_AREA_AVERAGING);

		// リサイズ画像作成
		BufferedImage resizedCutImage = new BufferedImage(size, size,
				BufferedImage.TYPE_INT_RGB);
		resizedCutImage.getGraphics().drawImage(resizedImage, 0, 0, size, size, offsetX,
				offsetY, size + offsetX, size + offsetY, null);

		// JPEGにしてバイト配列に変換
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(resizedCutImage, "JPG", os);

		return os.toByteArray();
	}


}
