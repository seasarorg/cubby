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
// TODO ログを出す
public class CubbyConfigurationImpl implements CubbyConfiguration {

	private static final RequestParser DEFAULT_REQUEST_PARSER = new DefaultRequestParserImpl();

	private final Logger logger = Logger.getLogger(this.getClass());

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
