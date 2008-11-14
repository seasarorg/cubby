package org.seasar.cubby.beans;

import static org.seasar.cubby.util.LogMessages.format;

public class PropertyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Class<?> targetClass;

	private String propertyName;

	/**
	 * {@link PropertyNotFoundException}を返します。
	 * 
	 * @param componentClass
	 * @param propertyName
	 */
	public PropertyNotFoundException(Class<?> componentClass, String propertyName) {
		super(format("ECUB0052", componentClass.getName(), propertyName));
		this.targetClass = componentClass;
		this.propertyName = propertyName;
	}

	/**
	 * ターゲットの{@link Class}を返します。
	 * 
	 * @return ターゲットの{@link Class}
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * プロパティ名を返します。
	 * 
	 * @return
	 */
	public String getPropertyName() {
		return propertyName;
	}
}
