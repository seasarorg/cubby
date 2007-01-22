<t:template extend="/todo/template.jsp">
<jsp:attribute name="title">Todoリスト</jsp:attribute>
<jsp:attribute name="content">
<script language="javascript">
<!--
function doDelete(name, id) {
  if(confirm('「' + name + '」削除します。よろしいですか？')) {
    location.href = 'delete?id=' + id;
  }
}
-->
</script>
<c:import url="/common/notice.jsp"/>
<h2>Todoの一覧</h2>
<div class="menu">
[<a href="create">新規作成</a>]
</div>
<t:form action="list" method="post" value="${findTodoDto}">
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
  	<c:forEach var="todo" items="${todoList}" varStatus="status">
	    <tr class="<t:odd var="${status.index}"/>">
	    	 <td><a href="${contextPath}/todo/${f:out(todo.id)}">${f:out(todo.text)}</a></td>
			<td>${f:out(todo.type.name)}</td>
			<td>${f:dateFormat(todo.limitDate, 'yyyy-MM-dd')}</td>
			<td>[<a href="javascript:doDelete('${f:out(todo.text)}',${f:out(todo.id)});">削除</a>]</td>
	    </tr>
  	</c:forEach>
</table>
</jsp:attribute>
</t:template>