package org.seasar.cubby.cubbitter.converter;

import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.impl.AbstractConverter;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;

public class EntryConverter extends AbstractConverter {

	public EntryService entryService;

	public Class<?> getObjectType() {
		return Entry.class;
	}

	public Object convertToObject(Object value, Class<?> objectType,
			ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		Long id = Long.valueOf(String.class.cast(value));
		Entry entry = entryService.find(id);
		return entry;
	}

	public String convertToString(Object value, ConversionHelper helper) {
		return null;
	}

}
