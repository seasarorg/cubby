<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Hello World - 入力画面</title>
</head>
<body>
<h1>Hello World - 入力画面</h1>
<t:form action="message" value="${action}" method="post">
	あなたの名前:
	<t:input type="text" name="name"/>
	<input type="submit" value="送信"/>
</t:form>
</body>
</html>
