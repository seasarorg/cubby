<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<title>components.jsp</title>
<style>
h2 {
	border-left: solid 10px green;
	border-bottom: solid 1px green;
}
th {
	background: green;
}
.odd {
	background: #FED;
}
.even {
	background: #FFF;
}
</style>
</head>
<body>
<t:form method="post" action="components" value="${form}">

<h2>Input Text</h2>
<label for="userName">Name:</label><t:input type="text" name="userName" id="userName"><input type="text"/></t:input>

<h2>Select</h2>
<label for="color">Color:</label>
<t:select name="color" size="3" 
	items="${colors}" labelProperty="name" valueProperty="value">
<select>
  <option value="red">赤</option>
  <option value="blue">青</option>
  <option value="yellow" selected="selected">黄色</option>
</select>
</t:select>

<h2>TextArea</h2> 
<label for="memo">memo:</label><br/>
<t:textarea name="memo" rows="5" cols="50"><textarea rows="5" cols="50"></textarea></t:textarea>

<h2>Checkbox</h2> 
<label for="memo">Check1:</label><br/>
<t:input type="checkbox" name="check1" value="true"/>

<h2>Checkbox(Array)</h2> 
<label for="check2">Check2:</label><br/>
<t:input type="checkbox" name="check2" value="1"/>
<t:input type="checkbox" name="check2" value="2"/>
<t:input type="checkbox" name="check2" value="3"/>

<h2>Odd & Null</h2>
<table border="1">
	<tr>
		<th>ID</th>
		<th>LABEL</th>
	</tr>
	<c:forEach var="item" varStatus="s" items="${colors}">
		<tr class="${f:odd(s.index, 'odd,even')}">
			<td>${item.value}</td>
			<td>${item.name}</td>
		</tr>
	</c:forEach>
	<t:null>
		<tr>
			<td>dummy1</td>
			<td>dummy1 name</td>
		</tr>
		<tr>
			<td>dummy2</td>
			<td>dummy2 name</td>
		</tr>
	</t:null>
</table>

<br/>
<input type="submit" value="登録"/>
</t:form>
</body>
</html>


