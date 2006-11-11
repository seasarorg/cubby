package org.seasar.cubby.seasar;

import java.lang.reflect.Method;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.aop.Pointcut;

public class ActionPointCut implements Pointcut {

	public boolean isApplied(Method method) {
		return CubbyUtils.isActionMethod(method);
	}

}
