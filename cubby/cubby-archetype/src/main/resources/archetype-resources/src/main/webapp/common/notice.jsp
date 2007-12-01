#set($dollar = '$')
<c:if test="${dollar}{f:containsKey(flash, 'notice')}">
<div id="notice" class="notice">${dollar}{f:out(flash['notice'])}</div>
</c:if>
