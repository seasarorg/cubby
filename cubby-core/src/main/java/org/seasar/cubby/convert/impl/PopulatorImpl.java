package org.seasar.cubby.convert.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.dxo.HttpParameterDxo;
import org.seasar.cubby.util.ClassUtils;

public class PopulatorImpl implements Populater {

    private HttpParameterDxo httpParameterDxo;

    public void setHttpParameterDxo(final HttpParameterDxo httpParameterDxo) {
        this.httpParameterDxo = httpParameterDxo;
    }

    public void populate(final Object target, final Map<String, Object> params) {
        Map<String, Object> normalized = new HashMap<String, Object>();
        for (String name : params.keySet()) {
            Method setter = ClassUtils.getSetter(target.getClass(), name);
            if (setter != null) {
                Object[] values = (Object[]) params.get(name);
                if (setter.getParameterTypes()[0].isArray()) {
                    normalized.put(name, values);
                } else {
                    normalized.put(name, values[0]);
                }
            }
        }
        try {
        	httpParameterDxo.convert(normalized, target);
        } catch (NumberFormatException e) {
        	// do nothing
        }
    }
}
