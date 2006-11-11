<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>${parts['title']}</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
<div id="header">
	<div id="header_info">
	${user.name}さんこんにちは | <a href="${contextPath}/logout">ログアウト</a>
	</div>
	<h1>Todo</h1>
</div>
<div id="content">
${parts['content']}
</div><!-- End of id="content" -->
<div id="footer">
© Copyright The Seasar Foundation and the others 2006, all rights reserved.
</div><!-- End of id="footer" -->
</body>
</html>