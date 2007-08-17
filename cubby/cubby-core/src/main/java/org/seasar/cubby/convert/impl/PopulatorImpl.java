package org.seasar.cubby.convert.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.dxo.HttpRequestDxo;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;

public class PopulatorImpl implements Populater {

    private HttpRequestDxo httpRequestDxo;

    public void setHttpRequestDxo(final HttpRequestDxo httpRequestDxo) {
        this.httpRequestDxo = httpRequestDxo;
    }

    public void populate(final Map<String, Object> src, final Object dest) {
    	if (src == null) {
    		return;
    	}

    	Map<String, Object> normalized = new HashMap<String, Object>();
    	BeanDesc beanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
    	for (String name : src.keySet()) {
    		try {
    			Object[] values = (Object[]) src.get(name);
    			PropertyDesc propertyDesc = beanDesc.getPropertyDesc(name);
    			Class<?> propertyType = propertyDesc.getPropertyType();
				if (propertyType.isArray()) {
    				normalized.put(name, values);
    			} else if (String.class.isAssignableFrom(propertyType)) {
                	String value = (String) values[0];
                	if (!StringUtil.isEmpty(value)) {
                		normalized.put(name, value);
                	} else {
                		normalized.put(name, null);
                	}
    			} else {
    				normalized.put(name, values[0]);
    			}
    		} catch (PropertyNotFoundRuntimeException e) {
    			
    		}
//            Method setter = ClassUtils.getSetter(dest.getClass(), name);
//            if (setter != null) {
//                Object[] values = (Object[]) src.get(name);
//                if (setter.getParameterTypes()[0].isArray()) {
//                    normalized.put(name, values);
//                } else if (values[0] instanceof String) {
//                	String value = (String) values[0];
//                	if (!StringUtil.isEmpty(value)) {
//                		normalized.put(name, value);
//                	} else {
//                		normalized.put(name, null);
//                	}
//                } else {
//                    normalized.put(name, values[0]);
//                }
//            }
        }
        try {
        	httpRequestDxo.convert(normalized, dest);
        } catch (NumberFormatException e) {
        	// do nothing
        	e.printStackTrace();
        }
    }

    public Map<String, String> describe(final Object src) {
    	Map<String, String> dest = new HashMap<String, String>();
    	if (src == null) {
    		return dest;
    	}
    	httpRequestDxo.convert(src, dest);
    	return dest;
    }
}
