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
package org.seasar.cubby.plugins.s2.aop;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.annotation.Annotation;

import org.seasar.framework.aop.InterType;
import org.seasar.framework.aop.intertype.AbstractInterType;
import org.seasar.framework.exception.CannotCompileRuntimeException;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.exception.NotFoundRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * プロパティを追加するための{@link InterType}です。
 * 
 * @author baba
 * @since 2.0.0
 */
public class PropertyInterType extends AbstractInterType {

	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static Logger logger = Logger.getLogger(PropertyInterType.class);

	private boolean trace;

	private PropertyType defaultPropertyType = PropertyType.READWRITE;

	private ClassFile classFile;

	private ConstPool constPool;

	/**
	 * トレースを出力するかどうか設定します。
	 * 
	 * @param trace
	 */
	public void setTrace(final boolean trace) {
		this.trace = trace;
	}

	/**
	 * デフォルトのpropertyType(NONE, READ, WRITE, READWRITE)を設定します。
	 * 
	 * @param defaultPropertyType
	 */
	public void setDefaultPropertyType(final String defaultPropertyType) {
		this.defaultPropertyType = PropertyType.valueOf(defaultPropertyType);
	}

	@Override
	protected void introduce() {
		if (logger.isDebugEnabled()) {
			logger.debug("[PropertyInterType] Introducing... "
					+ targetClass.getName());
		}

		classFile = enhancedClass.getClassFile();
		classFile.setVersionToJava5();
		constPool = classFile.getConstPool();

		final CtClass targetCtClass = toCtClass(targetClass);
		final PropertyType classPropertyType = getPropertyType(
				getTargetClass(), defaultPropertyType);
		for (final Field field : getTargetFields(targetClass)) {
			final Property property = field.getAnnotation(Property.class);
			PropertyType propertyType;
			if (property == null) {
				propertyType = classPropertyType;
			} else {
				propertyType = property.value();
			}

			final CtField ctField;
			try {
				ctField = targetCtClass.getField(field.getName());
			} catch (final NotFoundException e) {
				throw new NotFoundRuntimeException(e);
			}

			switch (propertyType) {
			case READ:
				createGetter(targetClass, ctField);
				break;

			case WRITE:
				createSetter(targetClass, ctField);
				break;

			case READWRITE:
				createGetter(targetClass, ctField);
				createSetter(targetClass, ctField);
				break;

			default:
				break;
			}
		}
	}

	private void createGetter(final Class<?> targetClass, final CtField ctField) {
		final String targetFieldName = ctField.getName();
		final String methodName = createMethodName(targetFieldName,
				GETTER_PREFIX);
		if (hasMethod(methodName)) {
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("[PropertyInterType] Creating getter "
					+ targetClass.getName() + "#" + methodName);
		}

		final StringBuilder src = new StringBuilder(512);
		src.append("{");
		if (trace) {
			src.append("org.seasar.framework.log.Logger logger =");
			src.append("org.seasar.framework.log.Logger.getLogger($class);");
			src.append("if(logger.isDebugEnabled()){");
			src
					.append("logger.debug(\"CALL \" + $class.getSuperclass().getName() + \"#");
			src.append(methodName);
			src.append("() : \" + this.");
			src.append(targetFieldName);
			src.append(");}");
		}
		src.append("return this.");
		src.append(targetFieldName);
		src.append(";}");

		try {
			final CtMethod ctMethod = CtNewMethod.make(Modifier.PUBLIC, ctField
					.getType(), methodName, toCtClassArray(null),
					toCtClassArray(null), src.toString(), enhancedClass);

			final FieldInfo fieldInfo = ctField.getFieldInfo();
			final MethodInfo methodInfo = ctMethod.getMethodInfo();

			final SignatureAttribute fieldSignature = SignatureAttribute.class
					.cast(fieldInfo.getAttribute(SignatureAttribute.tag));
			if (fieldSignature != null) {
				final SignatureAttribute methodSignature = new SignatureAttribute(
						constPool, "()" + fieldSignature.getSignature());
				methodInfo.addAttribute(methodSignature);
			}

			final AnnotationsAttribute fieldAnnotations = AnnotationsAttribute.class
					.cast(fieldInfo
							.getAttribute(AnnotationsAttribute.visibleTag));
			if (fieldAnnotations != null) {
				final AnnotationsAttribute methodAnnotations = new AnnotationsAttribute(
						constPool, AnnotationsAttribute.visibleTag);
				for (final Annotation annotation : fieldAnnotations
						.getAnnotations()) {
					methodAnnotations.addAnnotation(annotation);
				}
				methodInfo.addAttribute(methodAnnotations);
			}

			enhancedClass.addMethod(ctMethod);

		} catch (final NotFoundException e) {
			throw new NotFoundRuntimeException(e);
		} catch (final CannotCompileException e) {
			throw new CannotCompileRuntimeException(e);
		}
	}

	private void createSetter(final Class<?> targetClass, final CtField ctField) {
		final String targetFieldName = ctField.getName();
		final String methodName = createMethodName(targetFieldName,
				SETTER_PREFIX);
		try {
			final Class<?> paramType = Class.forName(ctField.getType()
					.getName());
			if (hasMethod(methodName, paramType)) {
				return;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("[PropertyInterType] Creating setter "
						+ targetClass.getName() + "#" + methodName);
			}

			final StringBuilder src = new StringBuilder(512);
			src.append("{");
			if (trace) {
				src.append("org.seasar.framework.log.Logger logger =");
				src
						.append("org.seasar.framework.log.Logger.getLogger($class);");
				src.append("if(logger.isDebugEnabled()){");
				src
						.append("logger.debug(\"CALL \" + $class.getSuperclass().getName() + \"#");
				src.append(methodName);
				src.append("(\" + $1 + \")\");}");
			}
			src.append("this.");
			src.append(targetFieldName);
			src.append(" = $1;}");

			final CtMethod ctMethod = CtNewMethod.make(Modifier.PUBLIC,
					toCtClass(void.class), methodName,
					toCtClassArray(new Class[] { paramType }),
					toCtClassArray(null), src.toString(), enhancedClass);

			final FieldInfo fieldInfo = ctField.getFieldInfo();
			final MethodInfo methodInfo = ctMethod.getMethodInfo();

			final SignatureAttribute fieldSignature = SignatureAttribute.class
					.cast(fieldInfo.getAttribute(SignatureAttribute.tag));
			if (fieldSignature != null) {
				final SignatureAttribute methodSignature = new SignatureAttribute(
						constPool, "(" + fieldSignature.getSignature() + ")V");
				methodInfo.addAttribute(methodSignature);
			}

			final AnnotationsAttribute fieldAnnotations = AnnotationsAttribute.class
					.cast(fieldInfo
							.getAttribute(AnnotationsAttribute.visibleTag));
			if (fieldAnnotations != null) {
				final AnnotationsAttribute methodAnnotations = new AnnotationsAttribute(
						constPool, AnnotationsAttribute.visibleTag);
				for (final Annotation annotation : fieldAnnotations
						.getAnnotations()) {
					methodAnnotations.addAnnotation(annotation);
				}
				methodInfo.addAttribute(methodAnnotations);
			}

			enhancedClass.addMethod(ctMethod);
		} catch (final ClassNotFoundException e) {
			throw new ClassNotFoundRuntimeException(e);
		} catch (final NotFoundException e) {
			throw new NotFoundRuntimeException(e);
		} catch (final CannotCompileException e) {
			throw new CannotCompileRuntimeException(e);
		}
	}

	private List<Field> getTargetFields(final Class<?> targetClass) {
		final List<Field> targetFields = new ArrayList<Field>();

		for (final Field field : getFields(targetClass)) {
			if (field.isSynthetic()) {
				continue;
			}
			if (Modifier.isPrivate(field.getModifiers())) {
				continue;
			}
			targetFields.add(field);
		}

		return targetFields;
	}

	private Field[] getFields(final Class<?> targetClass) {
		final Class<?> superClass = targetClass.getSuperclass();
		Field[] superFields = new Field[0];
		if (superClass != null) {
			superFields = getFields(superClass);
		}

		final Field[] currentFields = targetClass.getDeclaredFields();

		final Field[] fields = new Field[superFields.length
				+ currentFields.length];
		System.arraycopy(superFields, 0, fields, 0, superFields.length);
		System.arraycopy(currentFields, 0, fields, superFields.length,
				currentFields.length);

		return fields;
	}

	private String createMethodName(final String fieldName, final String prefix) {
		final String methodName = prefix + StringUtil.capitalize(fieldName);
		if (methodName.endsWith("_")) {
			return methodName.substring(0, methodName.length() - 1);
		} else {
			return methodName;
		}
	}

	private boolean hasMethod(final String methodName,
			final Class<?>... paramType) {
		try {
			getTargetClass().getMethod(methodName, paramType);
			return true;
		} catch (final NoSuchMethodException e) {
			return false;
		}
	}

	public PropertyType getPropertyType(final Class<?> clazz,
			final PropertyType defaultPropertyType) {
		return getPropertyTypeInternal(clazz, defaultPropertyType);
	}

	public PropertyType getPropertyType(final Field field,
			final PropertyType defaultPropertyType) {
		return getPropertyTypeInternal(field, defaultPropertyType);
	}

	/**
	 * 指定された{@link AnnotatedElement 注釈可能な要素} ({@link Class}または{@link Field}) が
	 * {@link Property}で注釈されていれば、 その<code>{@link Property#value() value}</code>
	 * で指定された値を返します。 指定された要素が注釈されていなければデフォルト値を返します。
	 * 
	 * @param element
	 *            注釈可能な要素
	 * @param defaultPropertyType
	 *            デフォルト値
	 * @return 指定された{@link AnnotatedElement 注釈可能な要素} ({@link Class}または
	 *         {@link Field}) が{@link Property}で注釈されていれば、 その
	 *         <code>{@link Property#value() value}</code>で指定された値
	 */
	private PropertyType getPropertyTypeInternal(
			final AnnotatedElement element,
			final PropertyType defaultPropertyType) {
		final PropertyType propertyType;
		if (element.isAnnotationPresent(Property.class)) {
			final Property property = element.getAnnotation(Property.class);
			propertyType = property.value();
		} else {
			propertyType = defaultPropertyType;
		}
		return propertyType;
	}

}
