package org.seasar.cubby.guice_examples.action;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.plugins.guice.InjectorFactory;
import org.seasar.cubby.plugins.guice.unit.MockServletModule;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;

public class HelloActionTest {

	private HttpServletRequest request;

	private HttpServletResponse response;
	
	private HelloAction action;
	
	private void setAction(HelloAction action){
		this.action = action;
	}

	@Before
	public void before() throws Exception {

		InjectorFactory.setModuleClassName(MockExampleModule.class.getName());
		request = createMock(HttpServletRequest.class);
		response = createMock(HttpServletResponse.class);
		MockServletModule.setUpContext(request, response);
	}

	private ActionResult processAction(String originalPath) throws Exception {
		
		final HashMap<String,Object> parameters = new HashMap<String,Object>();

		expect(request.getAttribute(CubbyConstants.ATTR_ROUTING)).andReturn(
				null);
		expect(request.getServletPath()).andReturn("");
		expect(request.getPathInfo()).andReturn(originalPath);

		request.setAttribute(eq(CubbyConstants.ATTR_PARAMS), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {
			public Object answer() throws Throwable {
				
				Map<String,Object> object = (Map<String,Object>)getCurrentArguments()[1];
				parameters.putAll(object);				
				return null;
			}
		});
		expect(request.getSession(false)).andReturn(null);
		expect(
				request
						.getAttribute("Key[type=org.seasar.cubby.guice_examples.action.HelloAction, annotation=[none]]"))
				.andReturn(null);

		request
				.setAttribute(
						eq("Key[type=org.seasar.cubby.guice_examples.action.HelloAction, annotation=[none]]"),
						anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		});

		request.setAttribute(eq(CubbyConstants.ATTR_ACTION), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				setAction((HelloAction) getCurrentArguments()[1]);
				return null;
			}

		});

		request.setAttribute(eq(CubbyConstants.ATTR_ACTION_CONTEXT),
				anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		});

		
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION)).andReturn(action).times(1, 3);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andReturn(parameters).times(1,3);
		
		expect(request.getLocale()).andReturn(Locale.JAPAN);
		
		request.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true);
		expectLastCall();
		
		expect(request.getMethod()).andReturn("GET");
		expect(request.getParameterMap()).andReturn(
				new HashMap<String, Object[]>());
		replay(request, response);
//		verify(request,response);
		return CubbyRunner.processAction(request, response);
	}

	@Test
	public void index1() throws Exception {

		ActionResult actualResult = processAction("/hello/");
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);
	}

	@Test
	public void message1() throws Exception {

		ActionResult actualResult = processAction("/hello/message");
		CubbyAssert.assertPathEquals(Forward.class, "index.jsp", actualResult);

	}
	
	
	@Test
	public void message2() throws Exception{
		Map<String,Object[]> params = new HashMap<String,Object[]>();
		params.put("name", new String[]{"cubby"});
		expect(request.getParameterMap()).andReturn(params);
		ActionResult actualResult = processAction("/hello/message");
		CubbyAssert.assertPathEquals(Forward.class, "hello.jsp", actualResult);		
	}
	

}
