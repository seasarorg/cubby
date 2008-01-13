/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

public class ImageAction extends Action {

	public HttpServletRequest request;

	public HttpServletResponse response;

	public String message = "The quick brown fox jumps over the lazy dog";

	public ActionResult index() {
		return new Forward("image.jsp");
	}

	@Path("{message,.*}.png")
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
