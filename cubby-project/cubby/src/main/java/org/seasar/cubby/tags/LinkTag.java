package org.seasar.cubby.tags;

import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.framework.container.SingletonS2Container;

public class LinkTag extends DynamicAttributesTagSupport {

	private Map<String, List<String>> parameters = new HashMap<String, List<String>>();

	private String tag;

	private String attribute;

	private String action;

	private String method;

	public void setTag(final String tag) {
		this.tag = tag;
	}

	public void setAttribute(final String attribute) {
		this.attribute = attribute;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	void addParameter(String name, String value) {
		if (!parameters.containsKey(name)) {
			parameters.put(name, new ArrayList<String>());
		}
		List<String> values = parameters.get(name);
		values.add(value);
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> forName(String className)
			throws ClassNotFoundException {
		return (Class<T>) Class.forName(className);
	}

	@Override
	public void doTag() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		this.getJspBody().invoke(writer);

		final Class<? extends Action> actionClass;
		try {
			actionClass = forName(action);
		} catch (ClassNotFoundException e) {
			throw new JspTagException(e);
		}

		final Map<String, String[]> parameters = new HashMap<String, String[]>();
		for (Entry<String, List<String>> entry : this.parameters.entrySet()) {
			parameters.put(entry.getKey(), entry.getValue().toArray(
					new String[0]));
		}

		final PathResolver pathResolver = SingletonS2Container
				.getComponent(PathResolver.class);
		final String contextPath = (String) getJspContext().getAttribute(
				ATTR_CONTEXT_PATH, PageContext.REQUEST_SCOPE);
		final String redirectPath = contextPath
				+ pathResolver.toRedirectPath(actionClass, method, parameters);
		getDynamicAttribute().put(attribute, redirectPath);

		final JspWriter out = getJspContext().getOut();
		try {
			out.write("<");
			out.write(tag);
			out.write(" ");
			out.write(toAttr(getDynamicAttribute()));
			out.write(">");
			out.write(writer.toString());
			out.write("</");
			out.write(tag);
			out.write(">");
		} catch (IOException e) {
			throw new JspTagException(e);
		}
	}

}
