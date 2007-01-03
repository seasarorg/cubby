package org.seasar.cubby.examples.components;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;

@Url("")
public class FileUploadController extends Controller {

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
