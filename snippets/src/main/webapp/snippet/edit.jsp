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
<h1><a href="${contextPath}">Snippets</a>::<a href="${contextPath}/${langPath}">${langPath}</a>::<a href="${contextPath}/${langPath}/${id}">${title}(${updated})</a></h1>
</div>
<div id="sidebar">
<c:import url="/common/lang_list.jsp"/>
</div>
<div id="main">
<c:import url="/common/errors.jsp"/>
<t:form action="${contextPath}/${langPath}/save" value="${action}" method="post">
<t:input type="hidden" name="languageId" value="${languageId}"/><br/>
<t:input type="text" name="title" size="100"/><br/>
<t:textarea name="content" cols="100" rows="20"/><br/>
<input type="submit" value="regist"/>
</t:form>
</div>
</body>
</html>
