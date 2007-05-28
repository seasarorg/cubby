package org.seasar.cubby.examples.whiteboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Url;

@Url("whiteboard")
public class ImageLineAction extends BaseAction {
	
	public Integer x;
	public void setX(Integer x) {
		this.x = x;
	}

	public Integer y;
	public void setY(Integer y) {
		this.y = y;
	}

	public Integer size;
	public void setSize(Integer size) {
		this.size = size;
	}

	@Form
	@Url("api/line/{x,[-]?[0-9]+}/{y,[-]?[0-9]+}/")
	public ActionResult drawLine() throws Exception {
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
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		out.flush();
		byte[] toByteArray = out.toByteArray();
		return new Direct(toByteArray, "", new Date().getTime());
	}
}
