<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@ taglib uri="http://www.seasar.org/cubby/helper-functions" prefix="hf" %>
<%@ tag dynamic-attributes="dyn" %>
<%@ attribute name="value" type="java.lang.Object" rtexprvalue="true" required="true" %>
<c:set var="__form" value="${value}" scope="request"/>
<form ${hf:toAttr(dyn)}>
<jsp:doBody/>
</form>
<c:remove var="__form"/>