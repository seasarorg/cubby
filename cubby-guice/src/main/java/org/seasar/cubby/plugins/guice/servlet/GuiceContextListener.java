package org.seasar.cubby.plugins.guice.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.cubby.plugins.guice.ModuleFactory;

public class GuiceContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		final ServletContext servletContext = event.getServletContext();
		final String moduleClassName = servletContext
				.getInitParameter("cubby.guice.module");
		ModuleFactory.setModuleClassName(moduleClassName);
	}

	public void contextDestroyed(ServletContextEvent event) {
	}

}