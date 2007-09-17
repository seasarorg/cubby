package org.seasar.cubby.examples.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Url("fileupload")
public class FileUploadAction extends Action {

	public static ValidationRules VALIDATION = new DefaultValidationRules() {
		public void initialize() {
			add("filename", new RequiredValidator());
			add("file", new RequiredValidator());
		}
	};
	
	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]

	private String filename;
	
	private FileItem file;

	public FileItem getFile() {
		return file;
	}

	public void setFile(FileItem file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	// ----------------------------------------------[Action Method]

	public ActionResult show() {
		return new Forward("fileupload.jsp");
	}

	@Form
	@Validation(errorPage="fileupload.jsp")
	public ActionResult upload() {
		return new Forward("fileupload_done.jsp");
	}

	// ----------------------------------------------[Helper Method]
	
}
