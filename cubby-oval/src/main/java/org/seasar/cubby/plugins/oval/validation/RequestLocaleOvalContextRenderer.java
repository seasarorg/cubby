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
package org.seasar.cubby.plugins.oval.validation;

import java.lang.reflect.Method;

import net.sf.oval.context.ClassContext;
import net.sf.oval.context.ConstructorParameterContext;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.MethodEntryContext;
import net.sf.oval.context.MethodExitContext;
import net.sf.oval.context.MethodParameterContext;
import net.sf.oval.context.MethodReturnValueContext;
import net.sf.oval.context.OValContext;
import net.sf.oval.localization.context.OValContextRenderer;

import org.seasar.cubby.util.Messages;

public class RequestLocaleOvalContextRenderer implements OValContextRenderer {

	/**
	 * {@inheritDoc}
	 */
	public String render(final OValContext ovalContext) {
		final String fieldName;
		if (ovalContext instanceof ClassContext) {
			final ClassContext ctx = (ClassContext) ovalContext;
			fieldName = ctx.getClazz().getSimpleName();
		} else if (ovalContext instanceof FieldContext) {
			final FieldContext ctx = (FieldContext) ovalContext;
			fieldName = ctx.getField().getName();
		} else if (ovalContext instanceof ConstructorParameterContext) {
			final ConstructorParameterContext ctx = (ConstructorParameterContext) ovalContext;
			fieldName = ctx.getParameterName();
		} else if (ovalContext instanceof MethodParameterContext) {
			final MethodParameterContext ctx = (MethodParameterContext) ovalContext;
			fieldName = ctx.getParameterName();
		} else if (ovalContext instanceof MethodEntryContext) {
			final MethodEntryContext ctx = (MethodEntryContext) ovalContext;
			Method method = ctx.getMethod();
			if (isGetter(method)) {
				fieldName = toPropertyName(method);
			} else {
				fieldName = method.getName();
			}
		} else if (ovalContext instanceof MethodExitContext) {
			final MethodExitContext ctx = (MethodExitContext) ovalContext;
			Method method = ctx.getMethod();
			if (isGetter(method)) {
				fieldName = toPropertyName(method);
			} else {
				fieldName = method.getName();
			}
		} else if (ovalContext instanceof MethodReturnValueContext) {
			final MethodReturnValueContext ctx = (MethodReturnValueContext) ovalContext;
			Method method = ctx.getMethod();
			if (isGetter(method)) {
				fieldName = toPropertyName(method);
			} else {
				fieldName = method.getName();
			}
		} else {
			return ovalContext.toString();
		}

		final String fieldNameKey;

		OvalValidationContext context = OvalValidationContext.get();
		String resourceKeyPrefix = context.getResourceKeyPrefix();
		if (resourceKeyPrefix == null) {
			fieldNameKey = fieldName;
		} else {
			fieldNameKey = resourceKeyPrefix + fieldName;
		}

		String fieldNameMessage = Messages.getText(fieldNameKey);
		return fieldNameMessage;
	}

	protected boolean isGetter(Method method) {
		if (!method.getName().startsWith("get")) {
			return false;
		}
		if (method.getParameterTypes().length > 0) {
			return false;
		}
		if (method.getReturnType().equals(void.class)) {
			return false;
		}
		return true;
	}

	protected String toPropertyName(Method method) {
		String methodName = method.getName();
		String str1 = methodName.substring(3);
		String propertyName = Character.toUpperCase(str1.charAt(0))
				+ str1.substring(1);
		return propertyName;
	}

}
