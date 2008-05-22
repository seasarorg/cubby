<h3>Language List</h3>
<c:forEach var="lang" items="${action.languages}">
	<li><a href="${contextPath}/${lang.path}">${lang.name}</a></li>
</c:forEach>
