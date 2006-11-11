<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="condition" type="org.seasar.dao.pager.PagerCondition" rtexprvalue="true" required="true" %>
<%@ attribute name="orderBy" required="true" %>
<%@ attribute name="href" required="true" %>
<c:if test="${condition.orderBy == orderBy}">
	<a href="${href}orderBy=<c:out value="${orderBy}"/>&process=search&offset=<c:out value="${condition.offset}"/>&
		sortType=<c:if test="${condition.sortType == 'desc'}">asc</c:if><c:if test="${condition.sortType != 'desc'}">desc</c:if>">
		<jsp:doBody />
	</a>
	<img src="${contextPath}/icons/<c:if test="${condition.sortType == 'desc'}">bullet_go_up.gif</c:if><c:if test="${condition.sortType != 'desc'}">bullet_go_down.gif</c:if>" valign="middle"/>
</c:if>
<c:if test="${condition.orderBy != orderBy}">
	<a href="${href}orderBy=<c:out value="${orderBy}"/>&process=search&offset=<c:out value="${condition.offset}"/>&
		sortType=asc">
		<jsp:doBody />
	</a>
</c:if>