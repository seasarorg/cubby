<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
	<title>components.jsp</title>
	<link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[<a href="${contextPath}/">戻る</a>]
<c:import url="/common/errors.jsp"/>
<t:form method="post" action="${contextPath}/components/array_save" value="${action}">
<h1>配列</h1>
<table>
<tr>
	<th>色名称</th>
	<th>値</th>
</tr>
<c:forEach var="name" items="${action.name}" varStatus="s">
<tr>
	<td class="${f:odd(s.index, 'odd,even')}">
		<t:input name="name" type="text" value="${name}" size="5"/>
	</td>
	<td>
		<t:input name="value" type="text" value="${action.value[s.index]}" size="5"/>
	</td>
</tr>
</c:forEach>
</table>
<t:input type="checkbox" name="check" value="1"/>1
<t:input type="checkbox" name="check" value="2"/>2
<t:input type="checkbox" name="check" value="3"/>3
<br/>
<input type="submit" value="登録"/>
</t:form>
</body>
</html>


