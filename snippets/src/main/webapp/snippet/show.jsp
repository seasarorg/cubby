<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <link href="${contextPath}/css/prettify.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script src="${contextPath}/js/prettify.js" type="text/javascript"></script> 
  <title>Snippets</title>
</head>
<body onload="prettyPrint()">
<div id="header">
<h1><a href="${contextPath}">Snippets</a>::<a href="${contextPath}/${langPath}">${langPath}</a></h1>
</div>
<c:import url="/common/notice.jsp"/>
<div id="sidebar">
<a href="${contextPath}/${langPath}/${id}/edit">edit</a>
<c:import url="/common/lang_list.jsp"/>
</div>
<div id="main">
<h2>${title}(${updated})</h2>
<pre class="prettyprint">
${content}
</pre>
<h4>comments</h4>
<c:forEach var="c" items="${comments}">
	<li>${c.name} ${c.text}(${c.updated})</li>
</c:forEach>
<t:form action="${contextPath}/${langPath}/${id}/addcomment" value="${action}" method="post">
<t:input name="name" type="text" size="20"/>
<t:input name="text" type="text" size="80"/>
<t:input name="comment" type="submit" value="Post"/>
</t:form>
</div>
</body>
</html>
