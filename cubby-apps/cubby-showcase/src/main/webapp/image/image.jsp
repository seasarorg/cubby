<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>direct response</title>
	<link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
	[<a href="${contextPath}/">戻る</a>]
	<h1>direct response</h1>
	<t:form value="${action}" method="post">
		<t:input type="text" name="message" />
		<input type="submit" />
	</t:form>
	<img src="${f:url(message)}.png" style="border: solid 1px"/>
</body>
</html>
