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
package org.seasar.cubby.examples.whiteboard.action;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.examples.whiteboard.dao.PageDao;
import org.seasar.cubby.examples.whiteboard.entity.Page;

@Path("whiteboard")
public class ImageUploadAction extends Action {
	
	public PageDao pageDao;

	public FileItem file;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Path("upload")
	public ActionResult upload() {
		Page page = new Page();
		page.setImage(file.get());
		pageDao.insert(page);
		return new Redirect(page.getId() + "/");
	}

}