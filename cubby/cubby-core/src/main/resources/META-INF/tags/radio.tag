<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="true" %>
<%@ attribute name="items" type="java.util.Collection" rtexprvalue="true" required="true" %>
<%@ attribute name="name" rtexprvalue="true" required="true" %>
<%@ attribute name="checkedValue" type="java.lang.Object" rtexprvalue="true" required="true" %>
<c:forEach items="${items}" var="item" varStatus="status">
<input type="radio" name="${name}" id="${name}-${status.index}" value="${item.value}" ${f:checked(item.value,checkedValue)}/><label for="${name}-${status.index}" style="cursor:pointer">${item.name}</label>
</c:forEach>
