package org.seasar.cubby.examples.whiteboard;

import java.util.Date;
import java.util.Map;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Url;

@Url("whiteboard")
public class ImageDownloadAction extends BaseAction {
	
	public String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Form
	@Url("api/image/{id,[0-9]+}")
	public ActionResult upload() {
		Map<String, Page> pages = getPages();
		Page page = pages.get(id);
		return new Direct(page.getImage(), "", new Date().getTime());
	}
		
}
