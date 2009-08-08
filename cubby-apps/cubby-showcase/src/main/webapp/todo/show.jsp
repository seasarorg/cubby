<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Todo詳細</title>
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
    <th>種別</th>
    <td>${f:out(todoType.name)}</td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>${f:dateFormat(limitDate, 'yyyy-MM-dd')}</td>
  </tr>
  <tr>
  <th>メモ</th>
  <td>${f:out(memo)}</td>
  </tr>
</table>
</div><!-- End of id="content" -->
<c:import url="fotter.jsp" />
</body>
</html>
