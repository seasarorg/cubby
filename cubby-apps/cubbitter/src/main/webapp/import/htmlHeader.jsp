<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<link rel="stylesheet" type="text/css" href="${contextPath}/style/style.css" />
<c:forTokens var="file" items="${param.cssFiles}" delims=",">
	<link rel="stylesheet" type="text/css" href="${contextPath}/style/${file}.css" />
</c:forTokens>
<link rel="shortcut icon" href="${contextPath}/image/favicon.ico"> 
<script type="text/javascript" src="${contextPath}/script/prototype.js"></script>
<script type="text/javascript" src="${contextPath}/script/rico.js"></script>
<c:forTokens var="file" items="${param.jsFiles}" delims=",">
	<script type="text/javascript" src="${contextPath}/script/${file}"></script>
</c:forTokens>
<script type="text/javascript" src="${contextPath}/script/common.js"></script>
<title>Cubbitter<c:if test="${!empty param.title}"> - ${param.title}</c:if></title>
<link rel="alternate" type="application/rss+xml" title="Cubbitter - みんなのひとりごと (rss2.0)" href="${contextPath}/rss2">
<link rel="alternate" type="application/atom+xml" title="Cubbitter - みんなのひとりごと (atom1.0)" href="${contextPath}/atom">
<c:if test="${!empty loginAccount}">
<link rel="alternate" type="application/rss+xml" title="Cubbitter - ともだちのひとりごと (rss2.0)" href="${contextPath}/${loginAccount.name}/friend/rss2">
<link rel="alternate" type="application/atom+xml" title="Cubbitter - ともだちのひとりごと (atom1.0)" href="${contextPath}/${loginAccount.name}/friend/atom">
</c:if>
<c:if test="${!empty account}">
<link rel="alternate" type="application/rss+xml" title="Cubbitter - ${account.name}ののひとりごと (rss2.0)" href="${contextPath}/${account.name}/entry/rss2">
<link rel="alternate" type="application/atom+xml" title="Cubbitter - ${account.name}ののひとりごと (atom1.0)" href="${contextPath}/${account.name}/entry//atom">
</c:if>
</head>
