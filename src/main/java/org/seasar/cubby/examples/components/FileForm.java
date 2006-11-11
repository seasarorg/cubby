package org.seasar.cubby.examples.components;

import org.apache.commons.fileupload.FileItem;

public class FileForm {
	private String filename;
	private FileItem file;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public FileItem getFile() {
		return file;
	}

	public void setFile(FileItem file) {
		this.file = file;
	}
	
	
}
