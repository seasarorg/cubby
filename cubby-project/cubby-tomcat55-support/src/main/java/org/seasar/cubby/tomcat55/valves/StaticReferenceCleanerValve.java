package org.seasar.cubby.tomcat55.valves;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.StringManager;
import org.apache.catalina.valves.ValveBase;
import org.apache.commons.logging.Log;

public class StaticReferenceCleanerValve extends ValveBase {

	private static final String info = "org.seasar.cubby.tomcat55.valves.StaticReferenceCleanerValve/1.0";

	private static StringManager sm = StringManager
			.getManager(Constants.Package);

	private String methods;

	private String maps;

	public String getInfo() {
		return info;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(final String methods) {
		this.methods = methods;

		this.methodNames = new ArrayList();
		final StringTokenizer tokenizer = new StringTokenizer(methods, ",");
		while (tokenizer.hasMoreTokens()) {
			this.methodNames.add(tokenizer.nextToken());
		}
	}

	public String getMaps() {
		return maps;
	}

	public void setMaps(final String maps) {
		this.maps = maps;

		this.mapNames = new ArrayList();
		final StringTokenizer tokenizer = new StringTokenizer(maps, ",");
		while (tokenizer.hasMoreTokens()) {
			this.mapNames.add(tokenizer.nextToken());
		}
	}

	private List methodNames = Collections.EMPTY_LIST;

	private List mapNames = Collections.EMPTY_LIST;

	public void invoke(final Request request, final Response response)
			throws IOException, ServletException {
		try {
			getNext().invoke(request, response);
		} finally {
			clean();
		}
	}

	private void clean() {
		final Log log = getContainer().getLogger();
		for (final Iterator i = methodNames.iterator(); i.hasNext();) {
			final StringTokenizer tokenizer = new StringTokenizer((String) i
					.next(), "#");
			final String className = tokenizer.nextToken();
			final String methodName = tokenizer.nextToken();

			try {
				final Class clazz = Class.forName(className);
				final Method method = clazz.getMethod(methodName, new Class[0]);
				method.invoke(null, null);
				if (log.isDebugEnabled()) {
					log.debug(sm.getString(
							"staticReferenceCleanerValve.methodInvokeSucceed",
							method));
				}
			} catch (final Exception e) {
				if (log.isWarnEnabled()) {
					log.warn(sm.getString(
							"staticReferenceCleanerValve.methodInvokeFailed",
							className, methodName), e);
				}
			}
		}

		for (final Iterator i = mapNames.iterator(); i.hasNext();) {
			final StringTokenizer tokenizer = new StringTokenizer((String) i
					.next(), "#");
			final String className = tokenizer.nextToken();
			final String mapFieldName = tokenizer.nextToken();

			try {
				final Class clazz = Class.forName(className);
				final Field field = clazz.getDeclaredField(mapFieldName);
				field.setAccessible(true);
				final Map map = (Map) field.get(null);
				map.clear();
				if (log.isDebugEnabled()) {
					log.debug(sm.getString(
							"staticReferenceCleanerValve.mapFieldClearSucceed",
							field));
				}
			} catch (final Exception e) {
				if (log.isWarnEnabled()) {
					log.warn(sm.getString(
							"staticReferenceCleanerValve.mapFieldClearFailed",
							className, maps), e);
				}
			}
		}

	}

}
