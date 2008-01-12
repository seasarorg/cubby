package org.seasar.cubby.examples.whiteboard.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.examples.whiteboard.dao.PageDao;
import org.seasar.cubby.examples.whiteboard.entity.Page;

@Path("whiteboard")
public class ImageDownloadAction extends Action {

	public HttpServletResponse response;

	public PageDao pageDao;

	public Integer id;

	@Form
	@Path("api/image/{id,[0-9]+}")
	public ActionResult upload() throws IOException {
		Page page = pageDao.selectById(id);

		response.setContentType("image/png");
		response.setDateHeader("Last-Modified", new Date().getTime());

		OutputStream output = response.getOutputStream();
		output.write(page.getImage());

		return new Direct();
	}

}
