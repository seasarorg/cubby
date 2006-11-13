<c:if test="${fn:length(allErrors) > 0}">
  <div class="errors">
    <ul>
    <c:forEach var="error" varStatus="s" items="${allErrors}">
      <li>${fn:replace(error, "
", "<br/>")}</li>
    </c:forEach>
    </ul>
  </div>
</c:if>
