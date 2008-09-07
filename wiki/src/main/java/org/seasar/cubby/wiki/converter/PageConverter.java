package org.seasar.cubby.wiki.converter;

import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.impl.AbstractConverter;
import org.seasar.cubby.wiki.entity.Page;
import org.seasar.cubby.wiki.service.PageService;


public class PageConverter extends AbstractConverter {

	public PageService pageService;
	
	public Object convertToObject(Object value, Class<?> objectType,
			ConversionHelper helper) {
		String name = (String) value;
		return pageService.getPageByName(name);
	}

	public String convertToString(Object value, ConversionHelper helper) {
		return Page.class.cast(value).getName();
	}

	public Class<?> getObjectType() {
		return Page.class;
	}

}
