package org.seasar.cubby.examples.components;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.controller.Controller;

@Url("")
public class FileUploadController extends Controller {

	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]
	
	public FileForm form = new FileForm();

	// ----------------------------------------------[Action Method]

	@Override
	public void initalize() {
	}
	
	@Url("fileupload")
	public String show() {
		return "/fileupload.jsp";
	}

	@Form("form")
	@Url("fileupload_upload")
	public String upload() {
		return "/fileupload_done.jsp";
	}

	// ----------------------------------------------[Helper Method]
	
}
