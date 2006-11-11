package org.seasar.cubby.convert;

import java.lang.reflect.Method;

public interface ValueConverter {
	Object convert(Object source, Object target, Method setter, Class destClass);
}
