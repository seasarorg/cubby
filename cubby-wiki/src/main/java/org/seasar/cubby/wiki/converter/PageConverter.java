package org.seasar.cubby.wiki.converter;

import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.impl.AbstractConverter;
import org.seasar.cubby.wiki.entity.Page;
import org.seasar.cubby.wiki.service.PageService;

import com.google.inject.Inject;


public class PageConverter extends AbstractConverter {

	@Inject
	private PageService pageService;
	
	public Object convertToObject(Object value, Class<?> objectType,
			ConversionHelper helper) {
		String name = (String) value;
		Page page = pageService.getPageByName(name);
		return page;
	}

	public String convertToString(Object value, ConversionHelper helper) {
		return Page.class.cast(value).getName();
	}

	public Class<?> getObjectType() {
		return Page.class;
	}

}
