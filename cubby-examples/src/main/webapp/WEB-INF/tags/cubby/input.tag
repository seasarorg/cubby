<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="type" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="value" type="java.lang.Object" rtexprvalue="true" required="false" %>
<%@ attribute name="checkedValue" type="java.lang.Object" rtexprvalue="true" required="false" %>
<c:if test="${fieldErrors[dyn['name']] != null}">${f:addClassName(dyn, "fieldError")}</c:if>
<c:choose>
<c:when test="${type == 'checkbox' || type == 'radio'}">
<c:set var="checkedValue" value="${f:formValue(checkedValue, __form, dyn['name'], pageContext.request)}"/>
<input type="${type}" value="${value}" ${f:toAttr(dyn)} ${f:checked(value,checkedValue)} />
</c:when>
<c:otherwise>
<c:set var="value" value="${f:formValue(value, __form, dyn['name'], pageContext.request)}"/>
<input type="${type}" value="${f:convertFieldValue(value, __form, dyn['name'])}" ${f:toAttr(dyn)} />
</c:otherwise>
</c:choose>
