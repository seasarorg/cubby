package org.seasar.cubby.seasar.convert.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.impl.AbstractConverter;

public class RemoveEmptyValueConverterImpl extends AbstractConverter {
    public RemoveEmptyValueConverterImpl() {
        super();
    }
    public Class[] getSourceClasses() {
        return new Class[] { Object[].class };
    }

    public Class<?> getDestClass() {
        return Object[].class;
    }

    public Object convert(Object source, Class destClass,
            ConversionContext context) {
    	Object[] src = (Object[]) source;
    	List<Object> dest = new ArrayList<Object>();
    	for (Object s : src) {
    		if (s == null || (s instanceof String && StringUtils.isEmpty((String)s))) {
    			// 何もしない
    		} else {
    			dest.add(s);
    		}
    	}
        return dest.toArray(src);
    }
}
