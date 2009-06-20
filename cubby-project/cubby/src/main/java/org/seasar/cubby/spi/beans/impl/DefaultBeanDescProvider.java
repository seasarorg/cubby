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

import static org.seasar.cubby.internal.util.ReflectionUtils.findAllDeclaredField;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.AttributeNotFoundException;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.IllegalAttributeException;
import org.seasar.cubby.spi.beans.ParameterizedClassDesc;

/**
 * {@link BeanDesc} のプロバイダの標準的な実装です。
 * <p>
 * {@link Introspector} によって生成されるメタ情報とそのフィールドの情報を元に {@link BeanDesc} を構築します。
 * </p>
 * 
 * @author baba
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
	protected final Map<Class<?>, BeanDesc> beanDescCache = new ConcurrentHashMap<Class<?>, BeanDesc>(
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

			final BeanDesc beanDesc = createBeanDesc(clazz);
			beanDescCache.put(clazz, beanDesc);
			return beanDesc;
		}
	}

	/**
	 * {@link BeanDesc} を生成します。
	 * 
	 * @param clazz
	 *            操作対象のクラス
	 * @return {@link BeanDesc}
	 */
	protected BeanDesc createBeanDesc(final Class<?> clazz) {
		return new BeanDescImpl(clazz);
	}

	/**
	 * {@link BeanDesc} の実装です。
	 * 
	 * @author baba
	 */
	protected static class BeanDescImpl implements BeanDesc {

		/** 操作対象のクラス。 */
		private final Class<?> clazz;

		/** プロパティの属性。 */
		private final Map<String, Attribute> propertyAttributeMap;

		/** フィールドの属性。 */
		private final Map<String, List<Attribute>> fieldAttributesMap;

		/**
		 * インスタンス化します。
		 * 
		 * @param clazz
		 *            操作対象のクラス
		 */
		public BeanDescImpl(final Class<?> clazz) {
			this.clazz = clazz;
			this.propertyAttributeMap = collectPropertyAttributeMap(clazz);
			this.fieldAttributesMap = collectFieldAttributesMap(clazz);
		}

		/**
		 * 指定されたクラスからプロパティの {@link Attribute} を生成します。
		 * 
		 * @param clazz
		 *            対象のクラス
		 * @return {@link Attribute} の {@link Map}
		 */
		protected Map<String, Attribute> collectPropertyAttributeMap(
				final Class<?> clazz) {
			final Map<String, Attribute> propertyAttributes = new LinkedHashMap<String, Attribute>();
			final BeanInfo beanInfo;
			try {
				beanInfo = Introspector.getBeanInfo(clazz);
			} catch (final IntrospectionException e) {
				throw new IllegalStateException(e);
			}
			for (final PropertyDescriptor propertyDescriptor : beanInfo
					.getPropertyDescriptors()) {
				final String propertyName = propertyDescriptor.getName();
				final Attribute propertyDesc = new PropertyAttribute(clazz,
						propertyDescriptor);
				propertyAttributes.put(propertyName, propertyDesc);
			}
			return propertyAttributes;
		}

		/**
		 * 指定されたクラスからフィールドの {@link Attribute} を生成します。
		 * 
		 * @param clazz
		 *            対象のクラス
		 * @return {@link Attribute} の {@link Map}
		 */
		protected Map<String, List<Attribute>> collectFieldAttributesMap(
				final Class<?> clazz) {
			final Map<String, List<Attribute>> fieldAttributes = new LinkedHashMap<String, List<Attribute>>();
			for (final Field field : findAllDeclaredField(clazz)) {
				final String fieldName = field.getName();
				List<Attribute> fieldDescs;
				if (!fieldAttributes.containsKey(fieldName)) {
					fieldDescs = new ArrayList<Attribute>();
					fieldAttributes.put(fieldName, fieldDescs);
				} else {
					fieldDescs = fieldAttributes.get(fieldName);
				}
				final Attribute attributes = new FieldAttribute(clazz, field);
				fieldDescs.add(attributes);
			}
			return fieldAttributes;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasPropertyAttribute(final String name) {
			return propertyAttributeMap.containsKey(name);
		}

		/**
		 * {@inheritDoc}
		 */
		public Attribute getPropertyAttribute(final String name)
				throws AttributeNotFoundException {
			if (!propertyAttributeMap.containsKey(name)) {
				throw new AttributeNotFoundException(clazz, name);
			}
			return propertyAttributeMap.get(name);
		}

		/**
		 * {@inheritDoc}
		 */
		public Set<Attribute> findtPropertyAttributes() {
			final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
			attributes.addAll(propertyAttributeMap.values());
			return Collections.unmodifiableSet(attributes);
		}

		/**
		 * {@inheritDoc}
		 */
		public Attribute getFieldAttribute(final String fieldName) {
			if (!fieldAttributesMap.containsKey(fieldName)) {
				throw new AttributeNotFoundException(clazz, fieldName);
			}
			return fieldAttributesMap.get(fieldName).get(0);
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasFieldAttribute(final String fieldName) {
			return fieldAttributesMap.containsKey(fieldName);
		}

		/**
		 * {@inheritDoc}
		 */
		public Set<Attribute> findFieldAttributes() {
			final Set<Attribute> fieldAttributes = new LinkedHashSet<Attribute>();
			for (final List<Attribute> attributes : fieldAttributesMap.values()) {
				fieldAttributes.addAll(attributes);
			}
			return Collections.unmodifiableSet(fieldAttributes);
		}

		/**
		 * {@inheritDoc}
		 */
		public Set<Attribute> findAllAttributes() {
			final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
			attributes.addAll(this.findtPropertyAttributes());
			attributes.addAll(this.findFieldAttributes());
			return Collections.unmodifiableSet(attributes);
		}

		/**
		 * {@inheritDoc}
		 */
		public Set<Attribute> findAttributesAnnotatedWith(
				final Class<? extends Annotation> annotationClass) {
			final Set<Attribute> attributes = new LinkedHashSet<Attribute>();
			for (final Attribute attribute : findAllAttributes()) {
				if (attribute.isAnnotationPresent(annotationClass)) {
					attributes.add(attribute);
				}
			}
			return Collections.unmodifiableSet(attributes);
		}

	}

	/**
	 * プロパティに対する {@link Attribute} の実装です。
	 * 
	 * @author baba
	 */
	protected static class PropertyAttribute implements Attribute {

		/** 操作対象のクラス。 */
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
		 *            操作対象のクラス
		 * @param propertyDescriptor
		 *            プロパティの記述
		 */
		PropertyAttribute(final Class<?> clazz,
				final PropertyDescriptor propertyDescriptor) {
			this.clazz = clazz;
			this.propertyDescriptor = propertyDescriptor;

			if (propertyDescriptor.getReadMethod() != null) {
				this.parameterizedClassDesc = createParameterizedClassDesc(propertyDescriptor
						.getReadMethod().getGenericReturnType());
			} else if (propertyDescriptor.getWriteMethod() != null) {
				this.parameterizedClassDesc = createParameterizedClassDesc(propertyDescriptor
						.getWriteMethod().getParameterTypes()[0]);
			} else {
				this.parameterizedClassDesc = null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public String getName() {
			return propertyDescriptor.getName();
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getType() {
			return propertyDescriptor.getPropertyType();
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
				throws IllegalAttributeException {
			final Method method = propertyDescriptor.getReadMethod();
			if (method == null) {
				throw new IllegalAttributeException(clazz, propertyDescriptor
						.getName(), new IllegalStateException(
						propertyDescriptor.getName() + " is not readable."));
			}
			try {
				return method.invoke(target);
			} catch (final IllegalAccessException e) {
				throw new IllegalAttributeException(clazz, propertyDescriptor
						.getName(), e);
			} catch (final InvocationTargetException e) {
				final Throwable t = e.getTargetException();
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new IllegalAttributeException(clazz, propertyDescriptor
						.getName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void setValue(final Object target, final Object value)
				throws IllegalAttributeException {
			final Method method = propertyDescriptor.getWriteMethod();
			if (method == null) {
				throw new IllegalAttributeException(clazz, propertyDescriptor
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
				throw new IllegalAttributeException(clazz, propertyDescriptor
						.getName(), e);
			} catch (final IllegalAccessException e) {
				throw new IllegalAttributeException(clazz, propertyDescriptor
						.getName(), e);
			} catch (final InvocationTargetException e) {
				final Throwable t = e.getTargetException();
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new IllegalAttributeException(clazz, propertyDescriptor
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
		 * <p>
		 * 以下の順序でプロパティのメソッドの定義を検索し、最初に見つかったアノテーションを返します。
		 * <ol>
		 * <li>プロパティ値の読み込みに使用するメソッド {@link #getReadMethod()}</li>
		 * <li>プロパティ値の書き込みに使用するメソッド {@link #getWriteMethod()}</li>
		 * </ol>
		 * </p>
		 * <p>
		 * また、クラスが {@link Proxy}
		 * になどよって動的に生成されている場合などは、メソッドからアノテーションを取得することができません。 (アノテーションが
		 * {@link Inherited} で修飾されている場合でも取得できません。)
		 * そのため、読み込み/書き込みメソッドの定義を以下のように検索し、アノテーションを取得します。
		 * <ul>
		 * <li>読み込み/書き込みメソッドが定義されたクラス ({@link Method#getDeclaringClass()})
		 * を検索対象クラスの起点とします。</li>
		 * <li>検索対象クラスと、そのインターフェイスから読み込み/書き込みメソッドの定義を検索します。
		 * <li>アノテーションが取得できなかった場合は、検索対象クラスをそのスーパークラスとし、再度検索を行います。</li>
		 * </ul>
		 * </p>
		 */
		public <T extends Annotation> T getAnnotation(
				final Class<T> annotationClass) {
			if (annotationCache.containsKey(annotationClass)) {
				return annotationClass.cast(annotationCache
						.get(annotationClass));
			}

			final Method readMethod = propertyDescriptor.getReadMethod();
			if (readMethod != null) {
				final T annotation = findAnnotation(annotationClass, readMethod);
				if (annotation != null) {
					annotationCache.put(annotationClass, annotation);
					return annotation;
				}
			}

			final Method writeMethod = propertyDescriptor.getWriteMethod();
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((propertyDescriptor == null) ? 0 : propertyDescriptor
							.hashCode());
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final PropertyAttribute other = (PropertyAttribute) obj;
			if (propertyDescriptor == null) {
				if (other.propertyDescriptor != null) {
					return false;
				}
			} else if (!propertyDescriptor.equals(other.propertyDescriptor)) {
				return false;
			}
			return true;
		}

	}

	/**
	 * フィールドに対する {@link Attribute} の実装です。
	 * 
	 * @author baba
	 */
	protected static class FieldAttribute implements Attribute {

		/** 操作対象のクラス。 */
		private final Class<?> clazz;

		/** フィールド。 */
		private final Field field;

		/** この属性が書き込み可能か。 */
		private final boolean writable;

		/** パラメタ化されたクラスの記述。 */
		private final ParameterizedClassDesc parameterizedClassDesc;

		/**
		 * インスタンス化します。
		 * 
		 * @param clazz
		 *            操作対象のクラス
		 * @param field
		 *            フィールド
		 */
		public FieldAttribute(final Class<?> clazz, final Field field) {
			this.clazz = clazz;
			this.field = field;
			this.writable = (field.getModifiers() & Modifier.FINAL) == 0;
			this.parameterizedClassDesc = createParameterizedClassDesc(field
					.getGenericType());
		}

		/**
		 * {@inheritDoc}
		 */
		public String getName() {
			return field.getName();
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getType() {
			return field.getType();
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getValue(final Object target) {
			try {
				if (this.isReadable() && !field.isAccessible()) {
					field.setAccessible(true);
					final Object value = field.get(target);
					field.setAccessible(false);
					return value;
				} else {
					final Object value = field.get(target);
					return value;
				}
			} catch (final IllegalAccessException e) {
				throw new IllegalAttributeException(clazz, field.getName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void setValue(final Object target, final Object value) {
			try {
				if (this.isWritable() && !field.isAccessible()) {
					field.setAccessible(true);
					field.set(target, value);
					field.setAccessible(false);
				} else {
					field.set(target, value);
				}
			} catch (final IllegalAccessException e) {
				throw new IllegalAttributeException(clazz, field.getName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isReadable() {
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isWritable() {
			return writable;
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
			return field.getAnnotation(annotationClass);
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isAnnotationPresent(
				final Class<? extends Annotation> annotationClass) {
			return field.isAnnotationPresent(annotationClass);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((field == null) ? 0 : field.hashCode());
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final FieldAttribute other = (FieldAttribute) obj;
			if (field == null) {
				if (other.field != null) {
					return false;
				}
			} else if (!field.equals(other.field)) {
				return false;
			}
			return true;
		}

	}

	/**
	 * {@link ParameterizedClassDesc}の実装クラスです。
	 * 
	 * @author baba
	 */
	protected static class ParameterizedClassDescImpl implements
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
	protected static ParameterizedClassDesc createParameterizedClassDesc(
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
	protected static Class<?> getRawClass(final Type type) {
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
	protected static Type[] getGenericParameter(final Type type) {
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
