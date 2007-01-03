package org.seasar.cubby.dxo.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.extension.dxo.converter.impl.AbstractConverter;

public class RemoveEmptyElementConverterImpl extends AbstractConverter {

	public RemoveEmptyElementConverterImpl() {
        super();
    }

	public Class[] getSourceClasses() {
        return new Class[] { Object[].class };
    }

    public Class<?> getDestClass() {
        return Object[].class;
    }

    public Object convert(final Object source, final Class destClass,
            final ConversionContext context) {

    	ConverterFactory converterFactory = context.getConverterFactory();
    	Converter converter = converterFactory.getConverter(source.getClass(), destClass);
    	Object[] converted = (Object[]) converter.convert(source, destClass, context);

    	List<Object> removed = new ArrayList<Object>();
    	Class<?> elementType = null;
    	for (Object value : converted) {
    		if (!isEmpty(value)) {
    			removed.add(value);
        		elementType = value.getClass();
    		}
    	}

    	if (removed.isEmpty()) {
    		return null;
    	}

    	Object[] array = (Object[]) Array.newInstance(elementType, removed.size());
    	return removed.toArray(array);
    }

	private static boolean isEmpty(final Object value) {
		if (value instanceof String) {
			if (StringUtils.isEmpty((String) value)) {
				return true;
			}
		} else {
			if (value == null) {
				return true;
			}
		}
		return false;
	}
}
