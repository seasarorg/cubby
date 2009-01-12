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
package org.seasar.cubby.plugins.s2.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;

import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;
import org.seasar.cubby.spi.beans.PropertyDesc;

public class S2BeanELResolver extends ELResolver {

	private final boolean readOnly;

	public S2BeanELResolver() {
		this(false);
	}

	public S2BeanELResolver(boolean readOnly) {
		super();
		this.readOnly = readOnly;
	}

	@Override
	public Object getValue(final ELContext context, final Object base,
			final Object property) throws NullPointerException,
			PropertyNotFoundException, ELException {
		if (context == null) {
			throw new NullPointerException();
		}
		if (base == null || property == null) {
			return null;
		}

		final String propertyName = property.toString();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
		if (!beanDesc.hasPropertyDesc(propertyName)) {
			return null;
		}

		try {
			final PropertyDesc propertyDesc = beanDesc
					.getPropertyDesc(propertyName);
			final Object value = propertyDesc.getValue(base);
			context.setPropertyResolved(true);
			return value;
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public Class<?> getType(final ELContext context, final Object base,
			final Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null || property == null) {
			return null;
		}

		final String propertyName = property.toString();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
		if (!beanDesc.hasPropertyDesc(propertyName)) {
			return null;
		}

		try {
			final PropertyDesc propertyDesc = beanDesc
					.getPropertyDesc(propertyName);
			final Class<?> propertyType = propertyDesc.getPropertyType();
			context.setPropertyResolved(true);
			return propertyType;
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public void setValue(final ELContext context, final Object base,
			final Object property, final Object value) {
		if (context == null) {
			throw new NullPointerException();
		}
		if (base == null || property == null) {
			return;
		}

		final String propertyName = property.toString();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
		if (!beanDesc.hasPropertyDesc(propertyName)) {
			return;
		}

		if (this.readOnly) {
			// TODO message
			// throw new PropertyNotWritableException(message(context,
			// "resolverNotWriteable", new Object[] { base.getClass()
			// .getName() }));
			throw new PropertyNotWritableException("resolverNotWriteable");
		}

		try {
			final PropertyDesc propertyDesc = beanDesc
					.getPropertyDesc(propertyName);
			propertyDesc.setValue(base, value);
			context.setPropertyResolved(true);
		} catch (final Exception e) {
		}
	}

	@Override
	public boolean isReadOnly(final ELContext context, final Object base,
			final Object property) {
		if (context == null) {
			throw new NullPointerException();
		}
		if (base == null || property == null) {
			return false;
		}

		final String propertyName = property.toString();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
		if (!beanDesc.hasPropertyDesc(propertyName)) {
			return true;
		}

		if (this.readOnly) {
			return true;
		}

		try {
			final PropertyDesc propertyDesc = beanDesc
					.getPropertyDesc(propertyName);
			final boolean readOnly = !propertyDesc.isWritable();
			context.setPropertyResolved(true);
			return readOnly;
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(
			final ELContext context, final Object base) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			return null;
		}

		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(base.getClass());
		try {
			final List<FeatureDescriptor> descriptors = new ArrayList<FeatureDescriptor>();
			for (final PropertyDesc propertyDesc : beanDesc.getPropertyDescs()) {
				final String propertyName = propertyDesc.getPropertyName();
				final FeatureDescriptor descriptor = new FeatureDescriptor();
				descriptor.setDisplayName(propertyName);
				descriptor.setExpert(false);
				descriptor.setHidden(false);
				descriptor.setName(propertyName);
				descriptor.setPreferred(true);
				descriptor.setValue(RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);
				descriptor.setValue(TYPE, propertyDesc.getPropertyType());
				descriptors.add(descriptor);
			}

			return descriptors.iterator();
		} catch (final Exception e) {
			//
		}
		return null;
	}

	@Override
	public Class<?> getCommonPropertyType(final ELContext context,
			final Object base) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base != null) {
			return Object.class;
		}

		return null;
	}

}
