<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<c:import url="/import/htmlHeader.jsp">
	<c:param name="jsFiles" value="login,comments" />
</c:import>
<body>

<div id="content">
<div class="wrap">
	<h1 class="roundTop">
		みんなのひとりごと
		<a href="${contextPath}/publicFeed/rss2">
			<img src="${contextPath}/image/feed.png" class="feed" />
		</a>
	</h1>
	<div class="box">
		<c:import url="/import/comments.jsp">
			<c:param name="returnPage" value="top" />
		</c:import>
	</div>
</div>
</div>

<c:choose>
	<c:when test="${empty sessionScope.user}">
		<c:import url="/import/noLoginMenu.jsp" />
	</c:when>
	<c:otherwise>
		<c:import url="/import/defaultMenu.jsp" />
	</c:otherwise>
</c:choose>

</body>
</html>