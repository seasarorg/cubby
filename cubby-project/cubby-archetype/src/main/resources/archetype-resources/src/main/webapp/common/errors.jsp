#set($dollar = '$')
<c:if test="${dollar}{!empty errors.all}">
  <div id="errors" class="errors">
    <ul>
    <c:forEach var="error" varStatus="s" items="${dollar}{errors.all}">
      <li>${dollar}{fn:replace(error, "
", "<br/>")}</li>
    </c:forEach>
    </ul>
  </div>
</c:if>
