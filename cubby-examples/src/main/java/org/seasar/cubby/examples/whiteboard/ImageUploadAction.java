package org.seasar.cubby.examples.whiteboard;

import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.RandomStringUtils;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Url;

@Url("whiteboard")
public class ImageUploadAction extends BaseAction {
	
	private FileItem file;

	public void setFile(FileItem file) {
		this.file = file;
	}
	
	public FileItem getFile() {
		return this.file;
	}
	
	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Form
	@Url("upload")
	public ActionResult upload() {
		String id = RandomStringUtils.randomNumeric(8);
		Map<String, Page> pages = getPages();
		Page page = new Page(id);
		page.setImage(file.get());
		pages.put(page.getId(), page);
		return new Redirect(id + "/");
	}
}
