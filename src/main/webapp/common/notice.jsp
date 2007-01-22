<c:if test="${f:containsKey(flash, 'notice')}">
<div class="notice">${f:out(flash['notice'])}</div>
</c:if>