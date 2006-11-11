<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="value" type="java.lang.String" rtexprvalue="true" required="true" %>
<textarea ${f:toAttr(dyn)} <c:if test="${fieldErrors[dyn['name']] != null}">class="fieldError"</c:if>>${value}</textarea>