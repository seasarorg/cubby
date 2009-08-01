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
<c:import url="/common/menu.jsp"></c:import>
</div>
<div id="main">
<div id="sidebar">
<h3>Page List</h3>
<ul>
<c:forEach var="p" items="${action.pages}">
<li><a href="${contextPath}/pages/${p.name}">${p.name}</a></li>
</c:forEach>
</ul>
</div>
<div id="content">
<div style="text-align: right;"">
<a href="javascript:editPage();">edit</a><br/>
</div>
<c:import url="common/notice.jsp"></c:import>
<div id="wiki-content">
${action.pageContent}
</div>
<div id="wiki-content-edit" class="wiki-content-edit"  style="display:none">
<t:textarea name="content" value="${action.page.content}" rows="20"></t:textarea>
</div>
</div>
</div>
<script type="text/javascript">
<!--
function editPage() {
	document.getElementById('wiki-content').style.display='none';
	document.getElementById('wiki-content-edit').style.display='';
}
-->
</script>
</body>
</html>
