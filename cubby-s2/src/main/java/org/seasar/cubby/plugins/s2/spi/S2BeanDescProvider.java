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
package org.seasar.cubby.plugins.s2.spi;

import static org.seasar.cubby.internal.util.ReflectionUtils.findAllDeclaredField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.IllegalAttributeException;
import org.seasar.cubby.spi.beans.ParameterizedClassDesc;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.framework.beans.IllegalPropertyRuntimeException;
import org.seasar.framework.beans.PropertyDesc;
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
 */
public class S2BeanDescProvider extends DefaultBeanDescProvider {

	private static volatile boolean initialized;

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
	@Override
	public BeanDesc getBeanDesc(final Class<?> clazz) {
		initialize();
		return super.getBeanDesc(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BeanDesc createBeanDesc(final Class<?> clazz) {
		return new S2BeanDescImpl(clazz);
	}

	/**
	 * {@link BeanDesc} の実装です。
	 * <p>
	 * Seasar2 の {@link org.seasar.framework.beans.BeanDesc} に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 */
	private static class S2BeanDescImpl extends BeanDescImpl {

		/** プロパティとして認識されたフィールド。 */
		private Set<Field> recognizedAsPropertyFields;

		/**
		 * インスタンス化します。
		 * 
		 * @param clazz
		 *            操作対象のクラス
		 */
		S2BeanDescImpl(final Class<?> clazz) {
			super(clazz);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected Map<String, Attribute> collectPropertyAttributeMap(
				final Class<?> clazz) {
			this.recognizedAsPropertyFields = new HashSet<Field>();
			final org.seasar.framework.beans.BeanDesc s2BeanDesc = org.seasar.framework.beans.factory.BeanDescFactory
					.getBeanDesc(clazz);
			final Map<String, Attribute> attributes = new LinkedHashMap<String, Attribute>();
			for (int i = 0; i < s2BeanDesc.getPropertyDescSize(); i++) {
				final org.seasar.framework.beans.PropertyDesc propertyDesc = s2BeanDesc
						.getPropertyDesc(i);
				final Attribute attribute = new S2PropertyAttribute(
						propertyDesc);
				if (!propertyDesc.hasReadMethod()
						&& !propertyDesc.hasWriteMethod()) {
					final Field field = propertyDesc.getField();
					if (field != null) {
						recognizedAsPropertyFields.add(field);
					}
				}
				attributes.put(propertyDesc.getPropertyName(), attribute);
			}
			return attributes;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected Map<String, List<Attribute>> collectFieldAttributesMap(
				final Class<?> clazz) {
			final Map<String, List<Attribute>> fieldAttributes = new LinkedHashMap<String, List<Attribute>>();
			for (final Field field : findAllDeclaredField(clazz)) {
				if (recognizedAsPropertyFields.contains(field)) {
					continue;
				}
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

	}

	/**
	 * {@link PropertyDesc} の実装です。
	 * <p>
	 * Seasar2 の {@link org.seasar.framework.beans.PropertyDesc} に処理を委譲します。
	 * </p>
	 * 
	 * @author baba
	 */
	private static class S2PropertyAttribute implements Attribute {

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
		S2PropertyAttribute(
				final org.seasar.framework.beans.PropertyDesc s2PropertyDesc) {
			this.s2PropertyDesc = s2PropertyDesc;
			this.parameterizedClassDesc = new S2ParameterizedClassDesc(
					s2PropertyDesc.getParameterizedClassDesc());
		}

		/**
		 * {@inheritDoc}
		 */
		public String getName() {
			return s2PropertyDesc.getPropertyName();
		}

		/**
		 * {@inheritDoc}
		 */
		public Class<?> getType() {
			return s2PropertyDesc.getPropertyType();
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
				throws IllegalAttributeException {
			try {
				return s2PropertyDesc.getValue(target);
			} catch (final IllegalPropertyRuntimeException e) {
				throw new IllegalAttributeException(e.getTargetClass(), e
						.getPropertyName(), e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void setValue(final Object target, final Object value)
				throws IllegalAttributeException {
			try {
				s2PropertyDesc.setValue(target, value);
			} catch (final IllegalPropertyRuntimeException e) {
				throw new IllegalAttributeException(e.getTargetClass(), e
						.getPropertyName(), e);
			}
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((s2PropertyDesc == null) ? 0 : s2PropertyDesc.hashCode());
			result = prime * result
					+ s2PropertyDesc.getBeanDesc().getBeanClass().hashCode();
			result = prime * result
					+ s2PropertyDesc.getPropertyName().hashCode();
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

			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final S2PropertyAttribute other = (S2PropertyAttribute) obj;
			if (s2PropertyDesc == null) {
				if (other.s2PropertyDesc != null) {
					return false;
				}
			} else {
				if (!s2PropertyDesc.getBeanDesc().getBeanClass().equals(
						other.s2PropertyDesc.getBeanDesc().getBeanClass())) {
					return false;
				}
				if (!s2PropertyDesc.getPropertyName().equals(
						other.s2PropertyDesc.getPropertyName())) {
					return false;
				}
			}
			return true;
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
