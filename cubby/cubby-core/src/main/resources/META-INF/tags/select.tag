<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby" prefix="f" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="items" type="java.util.Collection" rtexprvalue="true" required="true" %>
<%@ attribute name="label" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="value" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="selected" type="java.lang.Object" rtexprvalue="true" required="true" %>
<%@ attribute name="emptyOption" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="emptyOptionLabel" type="java.lang.String" rtexprvalue="true" required="false" %>
<select ${f:toAttr(dyn)} <c:if test="${fieldErrors[dyn['name']] != null}">class="fieldError"</c:if>>
<c:if test="${emptyOption}"><option value="">${f:out(emptyOptionLabel)}</option></c:if>
<c:forEach items="${items}" var="item">
<option value="${f:property(item, value)}"<c:if test="${selected == f:property(item, value)}"> selected="selected"</c:if>>${f:property(item, label)}</option>
</c:forEach>
</select>
