package org.seasar.cubby.tags;

import javax.servlet.http.HttpSession;

import org.jdom.Element;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class TokenTagTest extends JspTagTestCase {

	TokenTag tag;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new TokenTag();
		setupSimpleTag(tag);
	}

	@SuppressWarnings("unchecked")
	public void testDoTag1() throws Exception {
		MockServletContextImpl servletContext = new MockServletContextImpl("/cubby");
		ThreadContext.setRequest(new MockHttpServletRequestImpl(servletContext, "/servlet"));
		HttpSession session = ThreadContext.getRequest().getSession();

		tag.doTag();
		Element element = getResultAsElementFromContext();
		String message = "nameが指定されている場合";
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "input", element.getName());
		assertEquals(message, "hidden", element.getAttributeValue("type"));
		assertTrue(message, element.getAttributeValue("value").length() != 0);
		assertEquals(message, TokenHelper.DEFAULT_TOKEN_NAME, element
				.getAttributeValue("name"));
		assertTrue(message, TokenHelper.validateToken(session, element
				.getAttributeValue("value")));
	}

	@SuppressWarnings("unchecked")
	public void testDoTag2() throws Exception {
		MockServletContextImpl servletContext = new MockServletContextImpl("/cubby");
		ThreadContext.setRequest(new MockHttpServletRequestImpl(servletContext, "/servlet"));
		HttpSession session = ThreadContext.getRequest().getSession();

		tag.setName("cubby.token2");
		tag.doTag();
		Element element = getResultAsElementFromContext();
		String message = "nameが指定されている場合";
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "input", element.getName());
		assertEquals(message, "hidden", element.getAttributeValue("type"));
		assertTrue(message, element.getAttributeValue("value").length() != 0);
		assertEquals(message, "cubby.token2", element
				.getAttributeValue("name"));
		assertTrue(message, TokenHelper.validateToken(session, element
				.getAttributeValue("value")));
	}

	@SuppressWarnings("unchecked")
	public void testDoTag3() throws Exception {
		MockServletContextImpl servletContext = new MockServletContextImpl("/cubby");
		ThreadContext.setRequest(new MockHttpServletRequestImpl(servletContext, "/servlet"));
		HttpSession session = ThreadContext.getRequest().getSession();

		tag.setDynamicAttribute(null, "id", "token");
		tag.doTag();
		Element element = getResultAsElementFromContext();
		String message = "idが指定されている場合";
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "input", element.getName());
		assertEquals(message, "token", element.getAttributeValue("id"));
		assertEquals(message, "hidden", element.getAttributeValue("type"));
		assertTrue(message, element.getAttributeValue("value").length() != 0);
		assertEquals(message, TokenHelper.DEFAULT_TOKEN_NAME, element
				.getAttributeValue("name"));
		assertTrue(message, TokenHelper.validateToken(session, element
				.getAttributeValue("value")));
	}
}
