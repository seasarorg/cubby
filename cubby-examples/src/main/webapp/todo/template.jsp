<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>${parts['title']}</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${contextPath}/js/scriptaculous.js?Load=effect"></script>
</head>
<body>
<div id="header">
	<div id="header_info">
	${authenticationDto.user.name}さんこんにちは | <a href="${contextPath}/todo/logout">ログアウト</a>
	</div>
	<h1>Todo</h1>
</div>
<div id="content">
${parts['content']}
</div><!-- End of id="content" -->
<div id="footer">
© Copyright The Seasar Foundation and the others 2006-2007, all rights reserved.
</div><!-- End of id="footer" -->
</body>
</html>