package org.seasar.cubby.wiki;

import java.lang.reflect.Method;

import org.seasar.cubby.util.ActionUtils;

import com.google.inject.matcher.AbstractMatcher;

public class ActionMethodMatcher extends AbstractMatcher<Method> {
	public boolean matches(Method method) {
		return ActionUtils.isActionMethod(method);
	}
}
