<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="parts" %>
<%@ attribute name="extend" type="java.lang.String" rtexprvalue="true" required="true" %>
<c:set var="parts" value="${parts}" scope="request"/>
<c:import url="${extend}"/>
