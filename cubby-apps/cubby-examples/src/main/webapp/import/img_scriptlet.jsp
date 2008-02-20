<%@page import="java.util.List"%>
<%@page import="org.seasar.framework.container.SingletonS2Container"%>
<%@page import="org.seasar.cubby.examples.todo.dao.TodoTypeDao"%>
<%@page import="org.seasar.cubby.examples.todo.entity.TodoType"%>
<%
	TodoTypeDao todoTypeDao = SingletonS2Container
			.getComponent(TodoTypeDao.class);
	List<TodoType> types = todoTypeDao.seletAll();
	pageContext.setAttribute("types", types);
%>
<br />
<c:forEach var="type" items="${types}">
	<li>${type.name}</li>
</c:forEach>
<hr />