<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="Home" />
	<c:param name="jsFiles" value="comments" />
</c:import>

<body id="home">

<div id="content">
<div class="wrap">

<h1 class="roundTop">
	メンバーのひとりごと
	<a href="${contextPath}/privateFeed/rss2">
		<img src="${contextPath}/image/feed.png" class="feed" />
	</a>
</h1>
<div class="box">
<c:import url="/import/errorList.jsp" />

<t:form action="comment" method="post" value="{$action}">
	<t:textarea name="comment" id="commentArea"></t:textarea>
	<img src="${contextPath}/image/smiley/regular_smile.gif" alt="絵文字一覧の表示" title="絵文字一覧の表示" id="changeSmileyPanelButton" class="toggleButtonOff" />
	<input type="submit" value="送信" class="buttonS" />
	<div id="smileyInputPanel" style="display:none;"></div>
</t:form>

<c:import url="/import/comments.jsp">
	<c:param name="returnPage" value="home" />
</c:import>
</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>
