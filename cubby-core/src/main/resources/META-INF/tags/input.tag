<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="type" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="checkedValue" type="java.lang.Object" rtexprvalue="true" required="false" %>
<input type="${type}" ${f:toAttr(dyn)} ${f:checked(dyn['value'],checkedValue)} <c:if test="${fieldErrors[dyn['name']] != null}">class="fieldError"</c:if>/>