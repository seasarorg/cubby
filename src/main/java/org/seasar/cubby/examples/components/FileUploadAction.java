package org.seasar.cubby.examples.components;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;

@Url("")
public class FileUploadAction extends Action {

	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]
	
	public FileForm form = new FileForm();

	// ----------------------------------------------[Action Method]

	@Url("fileupload")
	public ActionResult show() {
		return new Forward("/fileupload.jsp");
	}

	@Form("form")
	@Url("fileupload_upload")
	public ActionResult upload() {
		return new Forward("/fileupload_done.jsp");
	}

	// ----------------------------------------------[Helper Method]
	
}
