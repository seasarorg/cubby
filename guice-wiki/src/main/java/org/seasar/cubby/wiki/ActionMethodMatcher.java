package org.seasar.cubby.wiki;

import java.lang.reflect.Method;

import org.seasar.cubby.internal.util.CubbyUtils;

import com.google.inject.matcher.AbstractMatcher;

public class ActionMethodMatcher extends AbstractMatcher<Method> {
	public boolean matches(Method method) {
		return CubbyUtils.isActionMethod(method);
	}
}
