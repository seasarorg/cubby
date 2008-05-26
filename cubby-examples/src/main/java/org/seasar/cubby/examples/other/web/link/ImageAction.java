package org.seasar.cubby.examples.other.web.link;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Path;

public class ImageAction extends Action {

	public HttpServletResponse response;

	public String token;

	@Path("/link/{token,.*}.png")
	public ActionResult image() throws IOException {
		BufferedImage image = new BufferedImage(100, 20,
				BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = image.getGraphics();

		graphics.setColor(Color.BLUE);
		graphics.drawString(token, 10, 15);

		response.setContentType("image/png");
		response.setDateHeader("Last-Modified", new Date().getTime());

		ImageIO.write(image, "png", response.getOutputStream());

		return new Direct();
	}

}
