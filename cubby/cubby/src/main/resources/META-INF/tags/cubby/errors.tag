<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@ taglib uri="http://www.seasar.org/cubby/helper-functions" prefix="hf" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="items" type="java.util.Collection" rtexprvalue="true" required="false" %>
<c:if test="${items == null}">
<c:set var="items" value="${allErrors}"/>
</c:if>
<c:if test="${fn:length(items) > 0}">
  <div class="errors" ${hf:toAttr(dyn)}>
    <ul>
    <c:forEach var="error" varStatus="s" items="${items}">
      <li>${fn:replace(error, "
", "<br/>")}</li>
    </c:forEach>
    </ul>
  </div>
</c:if>
