package org.seasar.cubby.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * アクションメソッドに適用される {@link Pointcut}。
 * 
 * @author baba
 */
public class ActionMethodPointcutImpl implements Pointcut, Serializable {

	private static final long serialVersionUID = -5701826062675617117L;

	private String[] methodNames;

	private Pattern[] patterns;

	/**
	 * {@link ActionMethodPointcutImpl}を作成します。
	 * 
	 * @param targetClass
	 * @throws EmptyRuntimeException
	 */
	public ActionMethodPointcutImpl(final Class<?> targetClass)
			throws EmptyRuntimeException {

		if (targetClass == null) {
			throw new EmptyRuntimeException("targetClass");
		}
		setMethodNames(getMethodNames(targetClass));
	}

	/**
	 * {@link ActionMethodPointcutImpl}を作成します。
	 * 
	 * @param methodNames
	 * @throws EmptyRuntimeException
	 */
	public ActionMethodPointcutImpl(final String[] methodNames)
			throws EmptyRuntimeException {

		if (methodNames == null || methodNames.length == 0) {
			throw new EmptyRuntimeException("methodNames");
		}
		setMethodNames(methodNames);
	}

	public boolean isApplied(final Method targetMethod) {
		if (!CubbyUtils.isActionMethod(targetMethod)) {
			return false;
		}

		final String methodName = targetMethod.getName();
		for (int i = 0; i < patterns.length; ++i) {
			if (patterns[i].matcher(methodName).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 対象になったメソッド名の配列を返します。
	 * 
	 * @return 対象になったメソッド名の配列
	 */
	public String[] getMethodNames() {
		return methodNames.clone();
	}

	private void setMethodNames(final String[] methodNames) {
		this.methodNames = methodNames;
		patterns = new Pattern[methodNames.length];
		for (int i = 0; i < patterns.length; ++i) {
			patterns[i] = Pattern.compile(methodNames[i]);
		}
	}

	private static String[] getMethodNames(final Class<?> targetClass) {
		final Set<String> methodNameSet = new HashSet<String>();
		if (targetClass.isInterface()) {
			addInterfaceMethodNames(methodNameSet, targetClass);
		}
		for (Class<?> clazz = targetClass; clazz != Object.class
				&& clazz != null; clazz = clazz.getSuperclass()) {
			final Class<?>[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; ++i) {
				addInterfaceMethodNames(methodNameSet, interfaces[i]);
			}
		}
		return methodNameSet.toArray(new String[methodNameSet.size()]);

	}

	private static void addInterfaceMethodNames(
			final Set<String> methodNameSet, final Class<?> interfaceClass) {
		final Method[] methods = interfaceClass.getDeclaredMethods();
		for (int j = 0; j < methods.length; j++) {
			methodNameSet.add(methods[j].getName());
		}
		final Class<?>[] interfaces = interfaceClass.getInterfaces();
		for (int i = 0; i < interfaces.length; ++i) {
			addInterfaceMethodNames(methodNameSet, interfaces[i]);
		}
	}

}
