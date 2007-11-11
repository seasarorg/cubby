<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>InputTagText Test</title>
</head>
<body>
<h1>InputTagText Test</h1>
<c:import url="/common/errors.jsp"/>
<t:form action="post" value="${action}">
	<table>
		<tr>
			<td>Byte value:</td>
			<td><t:input type="text" name="byteValue" /></td>
		</tr>
		<tr>
			<td>Integer value:</td>
			<td><t:input type="text" name="integerValue" /></td>
		</tr>
<%--
		<tr>
			<td>Character value:</td>
			<td><t:input type="text" name="characterValue" /></td>
		</tr>
--%>
		<tr>
			<td>Long value:</td>
			<td><t:input type="text" name="longValue" /></td>
		</tr>
		<tr>
			<td>Float value:</td>
			<td><t:input type="text" name="floatValue" /></td>
		</tr>
		<tr>
			<td>Double value:</td>
			<td><t:input type="text" name="doubleValue" /></td>
		</tr>
		<tr>
			<td>Boolean value:</td>
			<td><t:input type="text" name="booleanValue" /></td>
		</tr>
		<tr>
			<td>String value:</td>
			<td><t:input type="text" name="stringValue" /></td>
		</tr>
	</table>
	<input type="submit" value="submit" />
	<br />
</t:form>
</body>
</html>
