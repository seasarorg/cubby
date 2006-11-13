<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@ taglib uri="http://www.seasar.org/cubby/helper-functions" prefix="hf" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="value" type="java.lang.String" rtexprvalue="true" required="false" %>
<c:set var="value" value="${hf:formValue(value, __form, dyn['name'], pageContext.request)}"/>
<c:if test="${fieldErrors[dyn['name']] != null}">${hf:addClassName(dyn, "fieldError")}</c:if>
<textarea ${hf:toAttr(dyn)}>${value}</textarea>