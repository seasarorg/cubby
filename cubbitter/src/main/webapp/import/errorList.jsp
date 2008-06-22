<c:if test="${!empty errors.all}">
	<div class="round message">
	<ul>
		<c:forEach var="error" varStatus="s" items="${errors.all}">
			<li>${fn:replace(error, "
", "<br/>")}</li>
		</c:forEach>
	</ul>
	</div>
</c:if>
<c:if test="${f:containsKey(flash, 'notice')}">
	<div class="round message">
	<ul>
		<li>${f:out(flash['notice'])}</li>
	</ul>
	</div>
</c:if>