<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Todoリスト</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${contextPath}/js/scriptaculous.js?Load=effect"></script>
</head>
<body>
<c:import url="header.jsp"/>
<div id="content">
<script language="javascript">
<!--
function doDelete(name, id) {
  if(confirm('「' + name + '」削除します。よろしいですか？')) {
    location.href = '${contextPath}/todo/delete?id=' + id;
  }
}
-->
</script>
<c:import url="/common/notice.jsp"/>
<h2>Todoの一覧</h2>
<c:import url="/common/errors.jsp"/>
<div class="menu">
[<a href="${contextPath}/todo/create">新規作成</a>]
</div>
<t:form action="${contextPath}/todo/" method="post" value="${todoConditionDto}">
<table>
  <tr>
  	<th>キーワード</th>
    <td>
    	<t:input type="text" name="keyword" size="10" maxsize="10"/>
    </td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>
	<t:select id="typeId" name="typeId"
		items="${action.todoTypes}" labelProperty="name" valueProperty="id"/>
  </tr>
  <tr>
    <th>期限日</th>
    <td>
      <t:input type="text" id="limitDate" name="limitDate" size="10" maxsize="10"/>
    </td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="検索"/></td>
  </tr>
</table>
検索条件:${f:out(action.queryString)}
</t:form>
<table>
  <tr>
    <th>内容</th>
    <th>優先度</th>
    <th>期限日</th>
    <th>アクション</th>
  </tr>
  	<c:forEach var="todo" items="${action.todoList}" varStatus="status">
		<tr class="${f:odd(s.index, 'odd,even')}">
			<td><a href="${contextPath}/todo/${f:out(todo.id)}">${f:out(todo.text)}</a></td>
			<td>${f:out(todo.todoType.name)}</td>
			<td>${f:dateFormat(todo.limitDate, 'yyyy-MM-dd')}</td>
			<td>[<a href="javascript:doDelete('${f:out(todo.text)}',${f:out(todo.id)});">削除</a>]</td>
	    </tr>
  	</c:forEach>
</table>
</div><!-- End of id="content" -->
<c:import url="fotter.jsp" />
</body>
</html>