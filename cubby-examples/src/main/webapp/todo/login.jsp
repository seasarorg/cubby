<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Todoログイン</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${contextPath}/js/scriptaculous.js?Load=effect"></script>
</head>
<body>
[<a href="${contextPath}/">戻る</a>]
<h1>Todoログイン</h1>
<c:import url="/common/errors.jsp"/>
<c:import url="/common/notice.jsp"/>
<t:form action="${contextPath}/todo/login/process" method="post" value="${action}">
<table border="1">
  <tr>
    <th>ユーザID</th>
    <td><t:input type="text" name="userId" maxlength="20" /></td>
  </tr>
  <tr>
    <th>パスワード</th>
    <td><t:input type="password" name="password" maxlength="20" /></td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="ログイン"/></td>
  </tr>
</table>
test/testでログインできます。
</t:form>
</body>
</html>