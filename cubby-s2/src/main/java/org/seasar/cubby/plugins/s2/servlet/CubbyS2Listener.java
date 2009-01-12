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
