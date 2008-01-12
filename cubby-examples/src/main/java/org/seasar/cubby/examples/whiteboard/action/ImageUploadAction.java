package org.seasar.cubby.examples.whiteboard.action;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.examples.whiteboard.dao.PageDao;
import org.seasar.cubby.examples.whiteboard.entity.Page;

@Path("whiteboard")
public class ImageUploadAction extends Action {
	
	public PageDao pageDao;

	public FileItem file;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Form
	@Path("upload")
	public ActionResult upload() {
		Page page = new Page();
		page.setImage(file.get());
		pageDao.insert(page);
		return new Redirect(page.getId() + "/");
	}

}
