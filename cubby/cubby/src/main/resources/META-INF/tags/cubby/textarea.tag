<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@ taglib uri="http://www.seasar.org/cubby/helper-functions" prefix="hf" %>
<%@ tag dynamic-attributes="dyn" %>
<c:set var="value" value="${hf:formValue(dyn, __form, pageContext.request, 'value')}"/>
<c:if test="${fieldErrors[dyn['name']] != null}">${hf:addClassName(dyn, "fieldError")}</c:if>
<textarea ${hf:toAttr(dyn)}>${f:out(value)}</textarea>