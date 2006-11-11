<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="items" type="java.util.Collection" rtexprvalue="true" required="true" %>

<c:if test="${fn:length(items) > 0}">
  <div class="errors" ${f:toAttr(dyn)}>
    <ul>
    <c:forEach var="error" varStatus="s" items="${items}">
      <li>${error}</li>
    </c:forEach>
    </ul>
  </div>
</c:if>
