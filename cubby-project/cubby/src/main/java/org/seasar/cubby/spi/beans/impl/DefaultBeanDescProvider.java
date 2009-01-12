/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.spi.beans.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.IllegalPropertyException;
import org.seasar.cubby.spi.beans.ParameterizedClassDesc;
import org.seasar.cubby.spi.beans.PropertyDesc;
import org.seasar.cubby.spi.beans.PropertyNotFoundException;

/**
 * {@link BeanDesc} のプロバイダの標準的な実装です。
 * <p>
 * {@link Introspector} によって生成されるメタ情報を元に {@link BeanDesc} を構築します。
 * </p>
 * 
 * @author baba
 * @since 2.0.0
 */
public class DefaultBeanDescProvider implements BeanDescProvider {

	/** プリミティブ型のデフォルト値の <code>Map</code>。 */
	private static final Map<Class<?>, Object> PRIMITIVE_TYPE_DEFAULT_VALUES;
	static {
		final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
		map.put(boolean.class, Boolean.FALSE);
		map.put(char.class, Character.valueOf('\u0000'));
		map.put(byte.class, Byte.valueOf((byte) 0));
		map.put(short.class, Short.valueOf((short) 0));
		map.put(int.class, Integer.valueOf(0));
		map.put(long.class, Long.valueOf(0L));
		map.put(float.class, Float.valueOf(0F));
		map.put(double.class, Double.valueOf(0D));
		PRIMITIVE_TYPE_DEFAULT_VALUES = Collections.unmodifiableMap(map);
	}

	/** <code>BeanDesc</code> のキャッシュ。 */
	private final Map<Class<?>, BeanDesc> beanDescCache = new ConcurrentHashMap<Class<?>, BeanDesc>(
			1024);

	/**
	 * {@inheritDoc}
	 */
	public BeanDesc getBeanDesc(final Class<?> clazz) {
		if (beanDescCache.containsKey(clazz)) {
			return beanDescCache.get(clazz);
		}

		synchronized (clazz) {
			if (beanDescCache.containsKey(clazz)) {
				return beanDescCache.get(clazz);
			}

			try {
				final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
				final BeanDesc beanDesc = new BeanDescImpl(clazz, beanInfo);
				beanDescCache.put(clazz, beanDesc);
				return beanDesc;
			} catch (final IntrospectionException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * {@link BeanDesc} の実装です。
	 * <p>
	 * {@link Introspector} に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private static class BeanDescImpl implements BeanDesc {

		/** JavaBean のクラス。 */
		private final Class<?> clazz;

		/** {@link PropertyDesc} のキャッシュ。 */
		private final Map<String, PropertyDesc> propertyDescMap = new LinkedHashMap<String, PropertyDesc>();

		/**
		 * インスタンス化します。
		 * 
		 * @param clazz
		 *            JavaBean のクラス
		 * @param beanInfo
		 *            JavaBean の情報
		 */
		public BeanDescImpl(final Class<?> clazz, final BeanInfo beanInfo) {
			this.clazz = clazz;
			for (final PropertyDescriptor propertyDescriptor : beanInfo
					.getPropertyDescriptors()) {
				propertyDescMap.put(propertyDescriptor.getName(),
						new PropertyDescImpl(clazz, propertyDescriptor));
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasPropertyDesc(final String propertyName) {
			return propertyDescMap.containsKey(propertyName);
		}

		/**
		 * {@inheritDoc}
		 */
		public PropertyDesc getPropertyDesc(final String propertyName)
				throws PropertyNotFoundException {
			if (!propertyDescMap.containsKey(propertyName)) {
				throw new PropertyNotFoundException(clazz, propertyName);
			}
			return propertyDescMap.get(propertyName);
		}

		/**
		 * {@inheritDoc}
		 */
		public PropertyDesc[] getPropertyDescs() {
			return propertyDescMap.values().toArray(new PropertyDesc[0]);
		}

	}

	/**
	 * {@link PropertyDesc} の実装です。
	 * <p>
	 * {@link PropertyDescriptor} に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private static class PropertyDescImpl implements PropertyDesc {

		/** JavaBean のクラス。 */
		private final Class<?> clazz;

		/** プロパティの記述。 */
		private final PropertyDescriptor propertyDescriptor;

		/** パラメタ化されたクラスの記述。 */
		private final ParameterizedClassDesc parameterizedClassDesc;

		/** アノテーションのキャッシュ。 */
		private final Map<Class<? extends Annotation>, Annotation> annotationCache = new HashMap<Class<? extends Annotation>, Annotation>();

		/**
		 * インスタンス化します。
		 * 
		 * @param clazz
		 *            JavaBean のクラス
		 * @param propertyDescriptor
		 *            プロパティの記述
		 */
		PropertyDescImpl(final Class<?> clazz,
				final PropertyDescriptor propertyDescriptor) {
			this.clazz = clazz;
			this.propertyDescriptor = propertyDescriptor;

			if (propertyDescriptor.getReadMethod() != null) {
				parameterizedClassDesc = createParameterizedClassDesc(propertyDescriptor
						.getReadMethod().getGenericReturnType());
			} else if (propertyDescriptor.getWriteMethod() != null) {
				parameterizedClassDesc = createParameterizedClassDesc(propertyDescriptor
						.getWriteMethod().getParameterTypes()[0]);
			} else {
				parameterizedClassDesc = null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public String getPropertyName() {
			return propertyDescriptor.getName();
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getPropertyType() {
			return propertyDescriptor.getPropertyType();
		}

		/**
		 * {@inheritDoc}
		 */
		public Method getReadMethod() {
			return propertyDescriptor.getReadMethod();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasReadMethod() {
			return this.getReadMethod() != null;
		}

		/**
		 * {@inheritDoc}
		 */
		public Method getWriteMethod() {
			return propertyDescriptor.getWriteMethod();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasWriteMethod() {
			return this.getWriteMethod() != null;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isReadable() {
			return propertyDescriptor.getReadMethod() != null;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isWritable() {
			return propertyDescriptor.getWriteMethod() != null;
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getValue(final Object target)
				throws IllegalPropertyException {
			final Method method = this.getReadMethod();
			if (method == null) {
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), new IllegalStateException(
						propertyDescriptor.getName() + " is not readable."));
			}
			try {
				return method.invoke(target);
			} catch (final IllegalAccessException e) {
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), e);
			} catch (final InvocationTargetException e) {
				final Throwable t = e.getTargetException();
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void setValue(final Object target, final Object value)
				throws IllegalPropertyException {
			final Method method = this.getWriteMethod();
			if (method == null) {
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), new IllegalStateException(
						propertyDescriptor.getName() + " is not writable."));
			}
			try {
				final Class<?> propertyType = propertyDescriptor
						.getPropertyType();
				if (value == null && propertyType.isPrimitive()) {
					method.invoke(target, PRIMITIVE_TYPE_DEFAULT_VALUES
							.get(propertyType));
				} else {
					method.invoke(target, value);
				}
			} catch (final IllegalArgumentException e) {
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), e);
			} catch (final IllegalAccessException e) {
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), e);
			} catch (final InvocationTargetException e) {
				final Throwable t = e.getTargetException();
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new IllegalPropertyException(clazz, propertyDescriptor
						.getName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isParameterized() {
			return parameterizedClassDesc != null
					&& parameterizedClassDesc.isParameterizedClass();
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

			final Method readMethod = this.getReadMethod();
			if (readMethod != null) {
				final T annotation = findAnnotation(annotationClass, readMethod);
				if (annotation != null) {
					annotationCache.put(annotationClass, annotation);
					return annotation;
				}
			}

			final Method writeMethod = this.getWriteMethod();
			if (writeMethod != null) {
				final T annotation = findAnnotation(annotationClass,
						writeMethod);
				if (annotation != null) {
					annotationCache.put(annotationClass, annotation);
					return annotation;
				}
			}

			annotationCache.put(annotationClass, null);
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
	 * {@link ParameterizedClassDesc}の実装クラスです。
	 * 
	 * @since 2.0.0
	 * @author baba
	 */
	private static class ParameterizedClassDescImpl implements
			ParameterizedClassDesc {

		/** 原型となるクラス */
		protected Class<?> rawClass;

		/** 型引数を表す{@link ParameterizedClassDesc}の配列 */
		protected ParameterizedClassDesc[] arguments;

		/**
		 * インスタンスを構築します。
		 */
		public ParameterizedClassDescImpl() {
		}

		/**
		 * インスタンスを構築します。
		 * 
		 * @param rawClass
		 *            原型となるクラス
		 */
		public ParameterizedClassDescImpl(final Class<?> rawClass) {
			this.rawClass = rawClass;
		}

		/**
		 * インスタンスを構築します。
		 * 
		 * @param rawClass
		 *            原型となるクラス
		 * @param arguments
		 *            型引数を表す{@link ParameterizedClassDesc}の配列
		 */
		public ParameterizedClassDescImpl(final Class<?> rawClass,
				final ParameterizedClassDesc[] arguments) {
			this.rawClass = rawClass;
			this.arguments = arguments;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isParameterizedClass() {
			return arguments != null;
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getRawClass() {
			return rawClass;
		}

		/**
		 * {@inheritDoc}
		 */
		public ParameterizedClassDesc[] getArguments() {
			return arguments;
		}

	}

	/**
	 * {@link Type}を表現する{@link ParameterizedClassDesc}を作成して返します。
	 * 
	 * @param type
	 *            型
	 * @return 型を表現する{@link ParameterizedClassDesc}
	 */
	private static ParameterizedClassDesc createParameterizedClassDesc(
			final Type type) {
		final Class<?> rowClass = getRawClass(type);
		if (rowClass == null) {
			return null;
		}
		final Type[] parameterTypes = getGenericParameter(type);
		if (parameterTypes == null) {
			final ParameterizedClassDescImpl desc = new ParameterizedClassDescImpl(
					rowClass);
			return desc;
		} else {
			final ParameterizedClassDesc[] parameterDescs = new ParameterizedClassDesc[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; ++i) {
				parameterDescs[i] = createParameterizedClassDesc(parameterTypes[i]);
			}
			final ParameterizedClassDescImpl desc = new ParameterizedClassDescImpl(
					rowClass, parameterDescs);
			return desc;
		}
	}

	/**
	 * <code>type</code>の原型を返します。
	 * <p>
	 * <code>type</code>が原型でもパラメータ化された型でもない場合は<code>null</code>を返します。
	 * </p>
	 * 
	 * @param type
	 *            タイプ
	 * @return <code>type</code>の原型
	 */
	private static Class<?> getRawClass(final Type type) {
		if (Class.class.isInstance(type)) {
			return Class.class.cast(type);
		}
		if (ParameterizedType.class.isInstance(type)) {
			final ParameterizedType parameterizedType = ParameterizedType.class
					.cast(type);
			return getRawClass(parameterizedType.getRawType());
		}
		if (GenericArrayType.class.isInstance(type)) {
			final GenericArrayType genericArrayType = GenericArrayType.class
					.cast(type);
			final Class<?> rawClass = getRawClass(genericArrayType
					.getGenericComponentType());
			return Array.newInstance(rawClass, 0).getClass();
		}
		return null;
	}

	/**
	 * <code>type</code>の型引数の配列を返します。
	 * <p>
	 * <code>type</code>がパラメータ化された型でない場合は<code>null</code>を返します。
	 * </p>
	 * 
	 * @param type
	 *            タイプ
	 * @return <code>type</code>の型引数の配列
	 */
	private static Type[] getGenericParameter(final Type type) {
		if (ParameterizedType.class.isInstance(type)) {
			return ParameterizedType.class.cast(type).getActualTypeArguments();
		}
		if (GenericArrayType.class.isInstance(type)) {
			return getGenericParameter(GenericArrayType.class.cast(type)
					.getGenericComponentType());
		}
		return null;
	}

}
