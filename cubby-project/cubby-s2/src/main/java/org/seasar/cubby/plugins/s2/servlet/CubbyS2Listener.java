package org.seasar.cubby.plugins.s2.servlet;

import javax.el.ELResolver;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import org.seasar.cubby.plugins.s2.el.S2BeanELResolver;

public class CubbyS2Listener implements ServletContextListener {

	public void contextInitialized(final ServletContextEvent event) {
		final ServletContext context = event.getServletContext();
		final JspApplicationContext jspContext = JspFactory.getDefaultFactory()
				.getJspApplicationContext(context);
		final ELResolver elResolver = new S2BeanELResolver();
		jspContext.addELResolver(elResolver);
		event.getServletContext().log("Registered " + elResolver);
	}

	public void contextDestroyed(final ServletContextEvent event) {
	}

}
