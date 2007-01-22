<t:template extend="/todo/template.jsp">
<jsp:attribute name="title">Todo詳細</jsp:attribute>
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
<h2>Todo詳細</h2>
<div class="menu">
[<a href="${contextPath}/todo/list">一覧に戻る</a>]
[<a href="${contextPath}/todo/edit?id=${todo.id}">編集</a>]
[<a href="javascript:doDelete('${f:out(todo.text)}',${f:out(todo.id)})">削除</a>]
</div>
<table border="1">
  <tr>
    <th>内容</th>
    <td>${f:out(todo.text)}</td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>${f:out(todo.type.name)}</td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>${f:dateFormat(todo.limitDate, 'yyyy-MM-dd')}</td>
  </tr>
  <tr>
  <th>メモ</th>
  <td>${f:out(todo.memo)}</td>
  </tr>
</table>
</jsp:attribute>
</t:template>
