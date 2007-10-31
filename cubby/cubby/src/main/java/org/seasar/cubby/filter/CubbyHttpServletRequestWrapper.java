package org.seasar.cubby.filter;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
import static org.seasar.cubby.CubbyConstants.ATTR_MESSAGES;
import static org.seasar.cubby.CubbyConstants.ATTR_OUTPUT_VALUES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.Populator;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

/**
 * 
 * @author baba
 */
public class CubbyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private final ActionContext context;

	private final Map<String, String> outputValues;

	public CubbyHttpServletRequestWrapper(final HttpServletRequest request,
			final ActionContext context) {
		super(request);

		this.context = context;

		if (context.isInitialized()) {
			final Object formBean = context.getFormBean();
			final Populator populator = context.getPopulator();
			this.outputValues = populator.describe(formBean);
		} else {
			this.outputValues = Collections.emptyMap();
		}
	}

	@Override
	public Object getAttribute(final String name) {
		final Object attribute;
		if (ATTR_CONTEXT_PATH.equals(name)) {
			attribute = this.getContextPath();
		} else if (ATTR_ACTION.equals(name)) {
			attribute = context.getAction();
		} else if (ATTR_OUTPUT_VALUES.equals(name)) {
			attribute = outputValues;
		} else if (ATTR_MESSAGES.equals(name)) {
			attribute = ThreadContext.getMessagesMap();
		} else {
			if (context.isInitialized()) {
				final ComponentDef componentDef = context.getComponentDef();
				final Class<?> concreteClass = componentDef.getConcreteClass();
				final BeanDesc beanDesc = BeanDescFactory
						.getBeanDesc(concreteClass);
				if (beanDesc.hasPropertyDesc(name)) {
					final PropertyDesc propertyDesc = beanDesc
							.getPropertyDesc(name);
					if (propertyDesc.isReadable()) {
						attribute = propertyDesc.getValue(context.getAction());
					} else {
						attribute = super.getAttribute(name);
					}
				} else {
					attribute = super.getAttribute(name);
				}
			} else {
				attribute = super.getAttribute(name);
			}
		}
		return attribute;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getAttributeNames() {
		final List attributeNames = new ArrayList();

		attributeNames.add(ATTR_ACTION);
		attributeNames.add(ATTR_OUTPUT_VALUES);

		final Class<?> concreteClass = context.getComponentDef()
				.getConcreteClass();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(concreteClass);
		for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
			PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
			if (propertyDesc.isReadable()) {
				attributeNames.add(propertyDesc.getPropertyName());
			}
		}

		Enumeration defaultAttributeNames = super.getAttributeNames();
		while (defaultAttributeNames.hasMoreElements()) {
			attributeNames.add((String) defaultAttributeNames.nextElement());
		}
		return new IteratorEnumeration(attributeNames.iterator());
	}

	static class IteratorEnumeration<T> implements Enumeration<T> {

		private final Iterator<T> iterator;

		public IteratorEnumeration(Iterator<T> iterator) {
			this.iterator = iterator;
		}

		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		public T nextElement() {
			return iterator.next();
		}

	}

}
