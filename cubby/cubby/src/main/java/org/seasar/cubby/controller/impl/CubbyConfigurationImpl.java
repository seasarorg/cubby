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
package org.seasar.cubby.controller.impl;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.action.impl.FormatPatternImpl;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.log.Logger;

/**
 * 
 * @author baba
 * 
 */
public class CubbyConfigurationImpl implements CubbyConfiguration {

	private static final Logger logger = Logger
			.getLogger(CubbyConfiguration.class);

	private static final RequestParser DEFAULT_REQUEST_PARSER = new DefaultRequestParserImpl();

	private final RequestParser requestParser;

	private final FormatPattern formatPattern;

	public RequestParser getRequestParser() {
		return requestParser;
	}

	public FormatPattern getFormatPattern() {
		return formatPattern;
	}

	public CubbyConfigurationImpl(final S2Container container) {
		final S2Container root = container.getRoot();

		if (root.hasComponentDef(RequestParser.class)) {
			final ComponentDef componentDef = root
					.getComponentDef(RequestParser.class);
			this.requestParser = (RequestParser) componentDef.getComponent();
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0009", new Object[] { RequestParser.class,
						this.requestParser });
			}
		} else {
			this.requestParser = DEFAULT_REQUEST_PARSER;
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0008", new Object[] { RequestParser.class,
						this.requestParser });
			}
		}

		if (root.hasComponentDef(FormatPattern.class)) {
			final ComponentDef componentDef = root
					.getComponentDef(FormatPattern.class);
			this.formatPattern = (FormatPattern) componentDef.getComponent();
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0009", new Object[] { FormatPattern.class,
						this.formatPattern });
			}
		} else {
			this.formatPattern = new FormatPatternImpl();
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0008", new Object[] { FormatPattern.class,
						this.formatPattern });
			}
		}

	}

}
