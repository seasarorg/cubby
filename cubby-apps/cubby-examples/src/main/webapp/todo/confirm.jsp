<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Todo編集確認</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${contextPath}/js/scriptaculous.js?Load=effect"></script>
</head>
<body>
<c:import url="header.jsp"/>
<div id="content">
<h2>Todo編集確認</h2>
以下の内容で登録しますがよろしいですか？
<div class="menu">
[<a href="javascript:doBack()">戻る</a>]
</div>
<t:form action="${contextPath}/todo/save" method="post" value="${action}">
<t:input type="hidden" name="id"/>
<t:input type="hidden" name="text"/>
<t:input type="hidden" name="typeId"/>
<t:input type="hidden" name="limitDate" value="${limitDate}"/>
<t:input type="hidden" name="memo"/>
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
    <td>${f:out(limitDate)}</td>
  </tr>
  <tr>
    <th>メモ</th>
    <td>${f:out(memo)}</td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="登録"/></td>
  </tr>
</table>
</t:form>
</div><!-- End of id="content" -->
<c:import url="fotter.jsp" />
</body>
</html>
