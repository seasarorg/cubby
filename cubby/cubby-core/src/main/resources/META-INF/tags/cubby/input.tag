<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@ taglib uri="http://www.seasar.org/cubby/helper-functions" prefix="hf" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="type" type="java.lang.String" rtexprvalue="true" required="true" %>
<c:if test="${fieldErrors[dyn['name']] != null}">${hf:addClassName(dyn, "fieldError")}</c:if>
<c:choose>
<c:when test="${type == 'checkbox' || type == 'radio'}">
<c:set var="value" value="${dyn['value']}"/>
<c:set var="checkedValue" value="${hf:formValue(dyn, __form, pageContext.request, 'checkedValue')}"/>
<input type="${type}" value="${value}" ${hf:toAttr(dyn)} ${hf:checked(value,checkedValue)} />
</c:when>
<c:otherwise>
<c:set var="value" value="${hf:formValue(dyn, __form, pageContext.request, 'value')}"/>
<c:set var="checkedValue" value="${dyn['checkedValue']}"/>
<input type="${type}" value="${hf:convertFieldValue(value, __form, pageContext.request, dyn['name'])}" ${hf:toAttr(dyn)} />
</c:otherwise>
</c:choose>
