package org.seasar.cubby.dxo.converter.impl;

import org.apache.commons.fileupload.FileItem;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.impl.AbstractConverter;

public abstract class AbstractFileItemConverter extends AbstractConverter {

	@SuppressWarnings("unchecked")
	public Class[] getSourceClasses() {
		return new Class[] { FileItem.class, FileItem[].class };
	}

	@SuppressWarnings("unchecked")
	public Object convert(final Object source, final Class destClass,
			final ConversionContext context) {
		if (source == null) {
			return null;
		}
		if (source instanceof FileItem) {
			final FileItem fileItem = (FileItem) source;
			return convert(fileItem);
		}
		if (source instanceof FileItem[]) {
			final FileItem[] fileItems = (FileItem[]) source;
			return convert(fileItems[0]);
		}
		return null;
	}

	protected abstract Object convert(final FileItem fileItem);

}
