<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<t:form action="${contextPath}/login/" method="post" value="" id="loginForm">
<table>
<tr><th>ユーザID</th><td><t:input type="text" name="loginName" id="memberName" /></td></tr>
<tr><th>パスワード</th><td><t:input type="password" name="loginPassword" id="password" /></td></tr>
<tr><th>&nbsp;</th><td><input type="submit" value="ログイン" id="loginButton" /></td></tr>
</table>
</t:form>
