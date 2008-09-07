<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <title>Cubby Wiki | Edit ${page.name}</title>
</head>
<body>
<div id="banner">
<h1>${name}&nbsp;</h1>
</div>
<div id="menu">
<c:if test="${page == null}">
<a href="${contextPath}">Cancel</a>
</c:if>
<c:if test="${page != null}">
<a href="${contextPath}/pages/${page.name}">Cancel</a>
<a href="${contextPath}/pages/delete/${page.name}">Delete</a>
</c:if>
</div>
<c:import url="common/errors.jsp"></c:import>
<t:form actionClass="wiki.action.PageAction" actionMethod="save" method="post" value="${action}">
	<t:input type="hidden" name="id"/>
	<t:input type="text" name="name" size="100"/><br/>
	<t:textarea name="content" rows="10" cols="100"/><br/>
	<input type="submit" name="save" value="Save"/>
</t:form>
</body>
</html>
