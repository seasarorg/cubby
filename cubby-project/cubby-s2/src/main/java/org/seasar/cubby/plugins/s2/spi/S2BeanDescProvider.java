/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.plugins.s2.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.internal.beans.BeanDesc;
import org.seasar.cubby.internal.beans.IllegalPropertyException;
import org.seasar.cubby.internal.beans.ParameterizedClassDesc;
import org.seasar.cubby.internal.beans.PropertyDesc;
import org.seasar.cubby.internal.beans.PropertyNotFoundException;
import org.seasar.cubby.internal.spi.BeanDescProvider;
import org.seasar.framework.beans.IllegalPropertyRuntimeException;

/**
 * {@link BeanDesc} の Seasar2 向け実装を提供します。
 * <p>
 * getter/setter メソッドだけではなく、public フィールドもプロパティとして認識します。
 * </p>
 * <p>
 * Seasar2 の {@link org.seasar.framework.beans.factory.BeanDescFactory}
 * によって生成されるメタ情報を元に {@link BeanDesc} を構築します。
 * </p>
 * 
 * @author baba
 * @since 2.0.0
 */
public class S2BeanDescProvider implements BeanDescProvider {

	/**
	 * {@inheritDoc}
	 */
	public BeanDesc getBeanDesc(final Class<?> clazz) {
		final org.seasar.framework.beans.BeanDesc beanDesc = org.seasar.framework.beans.factory.BeanDescFactory
				.getBeanDesc(clazz);
		return new S2BeanDescImpl(beanDesc);

	}

	/**
	 * {@link BeanDesc} の実装です。
	 * <p>
	 * Seasar2 の {@link org.seasar.framework.beans.BeanDesc} に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private static class S2BeanDescImpl implements BeanDesc {

		/** Seasar2 の {@link org.seasar.framework.beans.BeanDesc} */
		private final org.seasar.framework.beans.BeanDesc s2BeanDesc;

		/** {@link PropertyDesc} のキャッシュ。 */
		private final Map<String, PropertyDesc> propertyDescMap;

		/**
		 * インスタンス化します。
		 * 
		 * @param s2BeanDesc
		 *            Seasar2 の {@link org.seasar.framework.beans.BeanDesc}
		 */
		S2BeanDescImpl(final org.seasar.framework.beans.BeanDesc s2BeanDesc) {
			this.s2BeanDesc = s2BeanDesc;
			this.propertyDescMap = new LinkedHashMap<String, PropertyDesc>();
			for (int i = 0; i < s2BeanDesc.getPropertyDescSize(); i++) {
				final org.seasar.framework.beans.PropertyDesc propertyDesc = s2BeanDesc
						.getPropertyDesc(i);
				propertyDescMap.put(propertyDesc.getPropertyName(),
						new S2PropertyDescImpl(propertyDesc));
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getBeanClass() {
			return s2BeanDesc.getBeanClass();
		}

		/**
		 * {@inheritDoc}
		 */
		public PropertyDesc getPropertyDesc(final String propertyName)
				throws PropertyNotFoundException {
			if (!propertyDescMap.containsKey(propertyName)) {
				throw new PropertyNotFoundException(s2BeanDesc.getBeanClass(),
						propertyName);
			}
			return propertyDescMap.get(propertyName);
		}

		/**
		 * {@inheritDoc}
		 */
		public PropertyDesc[] getPropertyDescs() {
			return propertyDescMap.values().toArray(new PropertyDesc[0]);
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasPropertyDesc(final String propertyName) {
			return propertyDescMap.containsKey(propertyName);
		}

	}

	/**
	 * {@link PropertyDesc} の実装です。
	 * <p>
	 * Seasar2 の {@link org.seasar.framework.beans.PropertyDesc} に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private static class S2PropertyDescImpl implements PropertyDesc {

		/** Seasar2 の {@link org.seasar.framework.beans.PropertyDesc} */
		private final org.seasar.framework.beans.PropertyDesc s2PropertyDesc;

		/** パラメタ化されたクラスの定義。 */
		private final ParameterizedClassDesc parameterizedClassDesc;

		/**
		 * インスタンス化します。
		 * 
		 * @param s2PropertyDesc
		 *            Seasar2 の {@link org.seasar.framework.beans.PropertyDesc}
		 */
		S2PropertyDescImpl(
				final org.seasar.framework.beans.PropertyDesc s2PropertyDesc) {
			this.s2PropertyDesc = s2PropertyDesc;
			this.parameterizedClassDesc = new S2ParameterizedClassDesc(
					s2PropertyDesc.getParameterizedClassDesc());
		}

		/**
		 * {@inheritDoc}
		 */
		public String getPropertyName() {
			return s2PropertyDesc.getPropertyName();
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getPropertyType() {
			return s2PropertyDesc.getPropertyType();
		}

		/**
		 * {@inheritDoc}
		 */
		public Method getReadMethod() {
			return s2PropertyDesc.getReadMethod();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasReadMethod() {
			return s2PropertyDesc.hasReadMethod();
		}

		/**
		 * {@inheritDoc}
		 */
		public Method getWriteMethod() {
			return s2PropertyDesc.getWriteMethod();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasWriteMethod() {
			return s2PropertyDesc.hasWriteMethod();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isReadable() {
			return s2PropertyDesc.isReadable();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isWritable() {
			return s2PropertyDesc.isWritable();
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getValue(final Object target)
				throws IllegalPropertyException, IllegalStateException {
			try {
				return s2PropertyDesc.getValue(target);
			} catch (final IllegalPropertyRuntimeException e) {
				throw new IllegalPropertyException(e.getTargetClass(), e
						.getPropertyName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void setValue(final Object target, final Object value)
				throws IllegalPropertyException, IllegalStateException {
			try {
				s2PropertyDesc.setValue(target, value);
			} catch (final IllegalPropertyRuntimeException e) {
				throw new IllegalPropertyException(e.getTargetClass(), e
						.getPropertyName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isParameterized() {
			return s2PropertyDesc.isParameterized();
		}

		/**
		 * {@inheritDoc}
		 */
		public ParameterizedClassDesc getParameterizedClassDesc() {
			return parameterizedClassDesc;
		}

		/**
		 * {@inheritDoc}
		 */
		public <T extends Annotation> T getAnnotation(
				final Class<T> annotationClass) {
			if (s2PropertyDesc.hasReadMethod()) {
				final Method method = s2PropertyDesc.getReadMethod();
				if (method.isAnnotationPresent(annotationClass)) {
					return method.getAnnotation(annotationClass);
				}
			}
			if (s2PropertyDesc.hasWriteMethod()) {
				final Method method = s2PropertyDesc.getWriteMethod();
				if (method.isAnnotationPresent(annotationClass)) {
					return method.getAnnotation(annotationClass);
				}
			}
			final Field field = s2PropertyDesc.getField();
			if (field != null && field.isAnnotationPresent(annotationClass)) {
				return field.getAnnotation(annotationClass);
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isAnnotationPresent(
				final Class<? extends Annotation> annotationClass) {
			return (s2PropertyDesc.hasReadMethod() && s2PropertyDesc
					.getReadMethod().isAnnotationPresent(annotationClass))
					|| (s2PropertyDesc.hasWriteMethod() && s2PropertyDesc
							.getWriteMethod().isAnnotationPresent(
									annotationClass))
					|| s2PropertyDesc.getField() != null
					&& s2PropertyDesc.getField().isAnnotationPresent(
							annotationClass);
		}

	}

	/**
	 * {@link ParameterizedClassDesc} の実装です。
	 * <p>
	 * Seasar2 の {@link org.seasar.framework.beans.ParameterizedClassDesc}
	 * に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private static class S2ParameterizedClassDesc implements
			ParameterizedClassDesc {

		/** Seasar2 の {@link org.seasar.framework.beans.ParameterizedClassDesc} */
		private final org.seasar.framework.beans.ParameterizedClassDesc s2ParameterizedClassDesc;

		/**
		 * インスタンス化します。
		 * 
		 * @param s2ParameterizedClassDesc
		 *            Seasar2 の
		 *            {@link org.seasar.framework.beans.ParameterizedClassDesc}
		 */
		S2ParameterizedClassDesc(
				final org.seasar.framework.beans.ParameterizedClassDesc s2ParameterizedClassDesc) {
			this.s2ParameterizedClassDesc = s2ParameterizedClassDesc;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isParameterizedClass() {
			return s2ParameterizedClassDesc.isParameterizedClass();
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getRawClass() {
			return s2ParameterizedClassDesc.getRawClass();
		}

		/**
		 * {@inheritDoc}
		 */
		public ParameterizedClassDesc[] getArguments() {
			final org.seasar.framework.beans.ParameterizedClassDesc[] s2Arguments = this.s2ParameterizedClassDesc
					.getArguments();
			final List<ParameterizedClassDesc> arguments = new ArrayList<ParameterizedClassDesc>(
					s2Arguments.length);
			for (final org.seasar.framework.beans.ParameterizedClassDesc s2Argument : s2Arguments) {
				final S2ParameterizedClassDesc argument = new S2ParameterizedClassDesc(
						s2Argument);
				arguments.add(argument);
			}
			return arguments.toArray(new ParameterizedClassDesc[0]);
		}

	}

}
