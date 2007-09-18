package org.seasar.cubby.examples.whiteboard.action;

import java.util.Date;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.examples.whiteboard.dao.PageDao;
import org.seasar.cubby.examples.whiteboard.entity.Page;

@Url("whiteboard")
public class ImageDownloadAction extends Action {

	public PageDao pageDao;

	public Integer id;

	@Form
	@Url("api/image/{id,[0-9]+}")
	public ActionResult upload() {
		Page page = pageDao.selectById(id);
		return new Direct(page.getImage(), "", new Date().getTime());
	}

}
