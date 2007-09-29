<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Todo編集</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${contextPath}/js/scriptaculous.js?Load=effect"></script>
</head>
<body>
<c:import url="header.jsp"/>
<h2>Todo編集</h2>
<c:import url="/common/errors.jsp"/>
[<a href="${contextPath}/todo/">一覧に戻る</a>]
<t:form action="confirm" method="post" value="${action}">
<t:input type="hidden" name="id" />
<table border="1">
  <tr>
    <th>タイトル</th>
    <td>
	<t:input type="text" maxlength="50" name="text"  /></td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>
		<t:select id="typeId" name="typeId"
			items="${todoTypes}" labelProperty="name" valueProperty="id" emptyOptionLabel="選択してください。" />
    </td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>
	<t:input type="text" maxlength="12" name="limitDate"/>(YYYY-MM-DD)</td>
  </tr>
  <tr>
    <th>メモ</th>
  <td>
    <t:textarea name="memo" value="${f:out(memo)}"/>
  </td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="次へ"/></td>
  </tr>
</table>
</t:form>
<div id="content">
</div><!-- End of id="content" -->
<c:import url="fotter.jsp" />
</body>
</html>
