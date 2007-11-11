package org.seasar.cubby.examples.whiteboard.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Url;

@Url("whiteboard")
public class ImageLineAction extends Action {

	public HttpServletResponse response;

	public Integer x;

	public Integer y;

	public Integer size;

	@Form
	@Url("api/line/{x,[-]?[0-9]+}/{y,[-]?[0-9]+}/")
	public ActionResult drawLine() throws IOException {
		int width = Math.abs(x);
		int height = Math.abs(y);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setBackground(new Color(0,0,0,Color.OPAQUE));
		g.setColor(Color.BLACK);
		
		int sx = x > 0 ? 0 : width;
		int ex = x > 0 ? width : 0;
		int sy = y > 0 ? 0 : height;
		int ey = y > 0 ? height : 0;
		g.drawLine(sx, sy, ex, ey);
		image.flush();

		response.setContentType("image/png");
		response.setDateHeader("Last-Modified", new Date().getTime());

		ImageIO.write(image, "png", response.getOutputStream());

		return new Direct();
	}
}
