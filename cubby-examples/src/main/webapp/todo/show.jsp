<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<t:template extend="template.jsp">
<jsp:attribute name="title">Todo詳細</jsp:attribute>
<jsp:attribute name="content">
<script language="javascript">
<!--
function doDelete(name, id) {
  if(confirm('「' + name + '」削除します。よろしいですか？')) {
    location.href = '${contextPath}/todo/delete?id=' + id;
  }
}
-->
</script>
<h2>Todo詳細</h2>
<div class="menu">
[<a href="${contextPath}/todo/">一覧に戻る</a>]
[<a href="${contextPath}/todo/edit?id=${id}">編集</a>]
[<a href="javascript:doDelete('${f:out(text)}',${f:out(id)})">削除</a>]
</div>
<table border="1">
  <tr>
    <th>内容</th>
    <td>${f:out(text)}</td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>${f:out(todoType.name)}</td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>${f:out(limitDate)}</td>
  </tr>
  <tr>
  <th>メモ</th>
  <td>${f:out(memo)}</td>
  </tr>
</table>
</jsp:attribute>
</t:template>
