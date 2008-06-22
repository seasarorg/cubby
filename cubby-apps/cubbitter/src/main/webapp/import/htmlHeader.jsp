<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<link rel="stylesheet" type="text/css" href="${contextPath}/style/style.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/style/niftyCorners.css" />
<c:forTokens var="file" items="${param.cssFiles}" delims=",">
	<link rel="stylesheet" type="text/css" href="${contextPath}/style/${file}.css" />
</c:forTokens>
<link rel="shortcut icon" href="${contextPath}/image/favicon.ico"> 
<script type="text/javascript" src="${contextPath}/script/niftycube.js"></script>
<script type="text/javascript" src="${contextPath}/script/prototype-1.6.0.2.js"></script>
<c:forTokens var="file" items="${param.jsFiles}" delims=",">
	<script type="text/javascript" src="${contextPath}/script/${file}.js"></script>
</c:forTokens>
<script type="text/javascript" src="${contextPath}/script/common.js"></script>
<title>Cubbitter<c:if test="${!empty param.title}"> - ${param.title}</c:if></title>
<link rel="alternate" type="application/rss+xml" title="Cubbitter - みんなのひとりごと (rss1.0)" href="${contextPath}/publicFeed/rss1">
<link rel="alternate" type="application/rss+xml" title="Cubbitter - みんなのひとりごと (rss2.0)" href="${contextPath}/publicFeed/rss2">
<link rel="alternate" type="application/atom+xml" title="Cubbitter - みんなのひとりごと (atom1.0)" href="${contextPath}/publicFeed/atom">
<c:if test="${!empty sessionScope.user}">
<link rel="alternate" type="application/rss+xml" title="Cubbitter - メンバーのひとりごと (rss1.0)" href="${contextPath}/privateFeed/rss1">
<link rel="alternate" type="application/rss+xml" title="Cubbitter - メンバーのひとりごと (rss2.0)" href="${contextPath}/privateFeed/rss2">
<link rel="alternate" type="application/atom+xml" title="Cubbitter - メンバーのひとりごと (atom1.0)" href="${contextPath}/privateFeed/atom">
</c:if>
</head>