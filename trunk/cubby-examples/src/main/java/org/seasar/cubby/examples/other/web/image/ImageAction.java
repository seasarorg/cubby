package org.seasar.cubby.examples.other.web.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;

public class ImageAction extends Action {

	public HttpServletRequest request;

	public HttpServletResponse response;

	public String message = "The quick brown fox jumps over the lazy dog";

	@Form
	public ActionResult index() {
		return new Forward("image.jsp");
	}

	@Url("{message,.*}.png")
	@Form
	public ActionResult download() throws IOException {
		BufferedImage image = new BufferedImage(300, 50, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = image.getGraphics();

		graphics.setColor(Color.ORANGE);
		graphics.drawString(message, 5, 20);

		graphics.setColor(Color.GREEN);
		graphics.drawRoundRect(200, 5, 50, 40, 10, 5);

		response.setContentType("image/png");
		response.setDateHeader("Last-Modified", new Date().getTime());

		ImageIO.write(image, "png", response.getOutputStream());

		return new Direct();
	}

}
