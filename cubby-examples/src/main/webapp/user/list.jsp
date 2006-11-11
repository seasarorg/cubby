<t:template extend="/todo/template.jsp">
<jsp:attribute name="title">ユーザ一覧</jsp:attribute>
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
<h1>ユーザ一覧</h1>
${f:out(flash['message'])}
<div class="menu">
[<a href="javascript:alert('実装されていません。')">新規作成</a>]
[<a href="${contextPath}/todo/list">Todoリストへ</a>]
</div>
<table border="1">
  <tr>
    <th>ID</th>
    <th>名称</th>
  </tr>
  	<c:forEach var="item" items="${items}">
	    <tr class="odd">
	    	 <td><a href="show?id=${f:out(item.id)}">${f:out(item.id)}</a></td>
			<td>${f:out(item.name)}</td>
		 <td>[<a href="javascript:alert('実装されていません。')">削除</a>]</td>
	    </tr>
  	</c:forEach>
</table>
</jsp:attribute>
</t:template>