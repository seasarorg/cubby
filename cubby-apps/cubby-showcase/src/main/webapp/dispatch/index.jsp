<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<title>components.jsp</title>
<link href="${contextPath}/css/default.css" rel="stylesheet"
	type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[
<a href="${contextPath}/">戻る</a>
]
<t:form action="${contextPath}/dispatch/execute" method="post"
	value="${action}">
	<h1>複数ボタンのフォーム</h1>
	<c:import url="/common/errors.jsp" />

	<label for="button1">name="button1" value="value1"</label>
	<t:input name="button1" value="value1" type="submit" />
	<br />
	<label for="button2">name="button2" value="value2"</label>
	<t:input name="button2" value="value2" type="submit" />
	<br />
	<label for="button3">name="button3" value="value3"</label>
	<t:input name="button3" value="value3" type="submit" />
	<br />
</t:form>
message: ${action.message}
<br />
value of button1: ${button1}
<br />
value of button2: ${button2}
<br />
value of button3: ${button3}
<br />
</body>
</html>
