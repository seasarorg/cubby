<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <title>Cubby Wiki | ${page.name}</title>
</head>
<body>
<div id="banner">
<h1>${page.name}</h1>
</div>
<div id="menu">
<a href="${contextPath}">Home</a>
<a href="${contextPath}/pages/edit/">New</a>
<a href="${contextPath}/pages/edit/${page.name}">Edit</a>
</div>
<div id="sidebar">
<h3>Page List</h3>
<ul>
<c:forEach var="p" items="${action.pages}">
<li><a href="${contextPath}/pages/${p.name}">${p.name}</a></li>
</c:forEach>
</ul>
</div>
<div id="content">
<c:import url="common/notice.jsp"></c:import>
${action.pageContent}
</div>
</body>
</html>
