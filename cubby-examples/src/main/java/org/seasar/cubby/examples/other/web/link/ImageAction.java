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
import org.seasar.cubby.action.RequestParameter;

public class ImageAction extends Action {

	public HttpServletResponse response;

	@RequestParameter
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
