<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@ taglib uri="http://www.seasar.org/cubby/helper-functions" prefix="hf" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="items" type="java.lang.Object" rtexprvalue="true" required="true" %>
<%@ attribute name="labelProperty" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="valueProperty" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="value" type="java.lang.Object" rtexprvalue="true" required="false" %>
<%@ attribute name="emptyOption" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="emptyOptionLabel" type="java.lang.String" rtexprvalue="true" required="false" %>
<c:set var="value" value="${hf:formValue(value, __form, dyn['name'], pageContext.request)}"/>
<c:if test="${fieldErrors[dyn['name']] != null}">${hf:addClassName(dyn, "fieldError")}</c:if>
<select ${hf:toAttr(dyn)}>
<c:if test="${emptyOption != false}"><option value="">${f:out(emptyOptionLabel)}</option></c:if>
<c:forEach items="${items}" var="item">
<option value="${hf:property(item, valueProperty)}" ${hf:selected(hf:property(item, valueProperty),value)}>${hf:property(item, labelProperty)}</option>
</c:forEach>
</select>
