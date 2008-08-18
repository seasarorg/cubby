package org.seasar.cubby.cubbitter.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Image;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}")
public class AccountPictureAction extends AbstractAccountAction {

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
		}

	};

	@Path("small.jpg")
	@Validation(rules = "validationRules")
	public ActionResult small() throws IOException {
		Image image = account.getSmallImage();
		writeResponse(response, image, "images/small.jpg");
		return new Direct();
	}

	@Path("medium.jpg")
	@Validation(rules = "validationRules")
	public ActionResult medium() throws IOException {
		Image image = account.getMediumImage();
		writeResponse(response, image, "images/medium.jpg");
		return new Direct();
	}

	@Path("large.jpg")
	@Validation(rules = "validationRules")
	public ActionResult large() throws IOException {
		Image image = account.getMediumImage();
		writeResponse(response, image, "images/large.jpg");
		return new Direct();
	}

	private static void writeResponse(HttpServletResponse response,
			Image image, String defaultResourceName) throws IOException {
		response.setContentType("image/jpeg");
		OutputStream output = response.getOutputStream();
		try {
			if (image == null) {
				writeDefaultImage(output, defaultResourceName);
			} else {
				writeImage(output, image);
			}
			output.flush();
		} finally {
			output.close();
		}
	}

	private static void writeImage(OutputStream output, Image image)
			throws IOException {
		output.write(image.getData());
	}

	private static void writeDefaultImage(OutputStream output, String name)
			throws IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(name);
		IOUtils.copy(input, output);
	}

}
