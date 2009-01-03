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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.IllegalPropertyException;
import org.seasar.cubby.spi.beans.ParameterizedClassDesc;
import org.seasar.cubby.spi.beans.PropertyDesc;
import org.seasar.cubby.spi.beans.PropertyNotFoundException;
import org.seasar.framework.beans.IllegalPropertyRuntimeException;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

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

	private static volatile boolean initialized;

	/** <code>BeanDesc</code> のキャッシュ。 */
	private final Map<Class<?>, BeanDesc> beanDescCache = new ConcurrentHashMap<Class<?>, BeanDesc>(
			1024);

	private void initialize() {
		if (!initialized) {
			DisposableUtil.add(new Disposable() {

				public void dispose() {
					beanDescCache.clear();
					initialized = false;
				}

			});
			initialized = true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public BeanDesc getBeanDesc(final Class<?> clazz) {
		initialize();

		if (beanDescCache.containsKey(clazz)) {
			return beanDescCache.get(clazz);
		}

		synchronized (clazz) {
			if (beanDescCache.containsKey(clazz)) {
				return beanDescCache.get(clazz);
			}

			final org.seasar.framework.beans.BeanDesc s2beanDesc = org.seasar.framework.beans.factory.BeanDescFactory
					.getBeanDesc(clazz);
			final BeanDesc beanDesc = new S2BeanDescImpl(s2beanDesc);
			beanDescCache.put(clazz, beanDesc);
			return beanDesc;
		}
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

		/** アノテーションのキャッシュ。 */
		private final Map<Class<? extends Annotation>, Annotation> annotationCache = new HashMap<Class<? extends Annotation>, Annotation>();

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
			if (annotationCache.containsKey(annotationClass)) {
				return annotationClass.cast(annotationCache
						.get(annotationClass));
			}

			if (s2PropertyDesc.hasReadMethod()) {
				final Method method = s2PropertyDesc.getReadMethod();
				final T annotation = findAnnotation(annotationClass, method);
				if (annotation != null) {
					annotationCache.put(annotationClass, annotation);
					return annotation;
				}
			}
			if (s2PropertyDesc.hasWriteMethod()) {
				final Method method = s2PropertyDesc.getWriteMethod();
				final T annotation = findAnnotation(annotationClass, method);
				if (annotation != null) {
					annotationCache.put(annotationClass, annotation);
					return annotation;
				}
			}
			final Field field = s2PropertyDesc.getField();
			if (field != null) {
				final T annotation = field.getAnnotation(annotationClass);
				if (annotation != null) {
					annotationCache.put(annotationClass, annotation);
					return field.getAnnotation(annotationClass);
				}
			}
			return null;
		}

		/**
		 * 指定されたメソッドのアノテーションを検索します。
		 * <p>
		 * インターフェイスやスーパークラスに定義されたメソッドの定義からもアノテーションが見つかるまで検索します。
		 * アノテーションが見つからなかった場合は <code>null</code> を返します。
		 * </p>
		 * 
		 * @param <T>
		 *            アノテーションの型
		 * @param annotationClass
		 *            アノテーションの型
		 * @param method
		 *            メソッド
		 * @return アノテーションが見つかった場合はそのアノテーション、見つからなかった場合は <code>null</code>
		 */
		private static <T extends Annotation> T findAnnotation(
				final Class<T> annotationClass, final Method method) {
			final String methodName = method.getName();
			final Class<?>[] parameterTypes = method.getParameterTypes();
			for (Class<?> target = method.getDeclaringClass(); !target
					.equals(Object.class); target = target.getSuperclass()) {
				final T annotation = getAnnotation(annotationClass, target,
						methodName, parameterTypes);
				if (annotation != null) {
					return annotation;
				}
				final T annotationOfInterfaces = getAnnotationOfInterfaces(
						annotationClass, target, methodName, parameterTypes);
				if (annotationOfInterfaces != null) {
					return annotationOfInterfaces;
				}
			}
			return null;
		}

		/**
		 * 指定されたクラスが実装するインターフェイスにメソッド名、パラメータ型でシグニチャを指定されたメソッドが定義されていれば、
		 * そのメソッドに定義されたアノテーションを返します。
		 * 
		 * @param <T>
		 *            アノテーションの型
		 * @param annotationClass
		 *            アノテーションの型
		 * @param clazz
		 *            クラス
		 * @param methodName
		 *            メソッド名
		 * @param parameterTypes
		 *            パラメータの型
		 * @return アノテーション
		 */
		private static <T extends Annotation> T getAnnotationOfInterfaces(
				final Class<T> annotationClass, final Class<?> clazz,
				final String methodName, final Class<?>[] parameterTypes) {
			for (final Class<?> interfaceClass : clazz.getInterfaces()) {
				final T annotation = getAnnotation(annotationClass,
						interfaceClass, methodName, parameterTypes);
				if (annotation != null) {
					return annotation;
				}
			}
			return null;
		}

		/**
		 * 指定されたクラスにメソッド名、パラメータ型でシグニチャを指定されたメソッドが定義されていれば、
		 * そのメソッドに定義されたアノテーションを返します。
		 * 
		 * @param <T>
		 *            アノテーションの型
		 * @param annotationClass
		 *            アノテーションの型
		 * @param clazz
		 *            クラス
		 * @param methodName
		 *            メソッド名
		 * @param parameterTypes
		 *            パラメータの型
		 * @return アノテーション
		 */
		private static <T extends Annotation> T getAnnotation(
				final Class<T> annotationClass, final Class<?> clazz,
				final String methodName,
				@SuppressWarnings("unchecked") final Class[] parameterTypes) {
			try {
				final Method method = clazz.getDeclaredMethod(methodName,
						parameterTypes);
				if (method.isAnnotationPresent(annotationClass)) {
					return method.getAnnotation(annotationClass);
				}
			} catch (final NoSuchMethodException e) {
				// do nothing
			}

			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isAnnotationPresent(
				final Class<? extends Annotation> annotationClass) {
			return this.getAnnotation(annotationClass) != null;
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
