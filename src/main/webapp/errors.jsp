<c:if test="${fn:length(actionErrors) > 0}">
  <div class="errors">
    <ul>
    <c:forEach var="error" varStatus="s" items="${actionErrors}">
      <li>${fn:replace(error, "
", "<br/>")}</li>
    </c:forEach>
    </ul>
  </div>
</c:if>
