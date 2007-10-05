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
<t:form method="post" action="${contextPath}/components/" value="${form}">
<h1>コンポーネント一覧</h1>
<h2>Input Text</h2>
<label for="userName">Name:</label><t:input type="text" name="userName" id="userName"><input type="text"/></t:input>

<h2>Input Text(date)</h2>
<label for="date">Date:</label><t:input type="text" name="date" id="date"><input type="text"/></t:input>

<h2>Input Text(int)</h2>
<label for="intValue">Int(1-10):</label><t:input type="text" name="intValue" id="intValue"><input type="text"/></t:input>

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

<h2>messages</h2>
<span>${messages['msg.sample1']}</span><br/>
<span>${f:out(messages['msg.sample1'])}</span>
<br/>
<input type="submit" value="登録"/>
</t:form>
</body>
</html>


