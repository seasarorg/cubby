<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="${member.memberName}" />
	<c:param name="jsFiles" value="comments" />
</c:import>

<body>

<div id="content">
<div class="wrap">

<h1 class="roundTop">

<img src="${contextPath}/picture/l/${member.memberId}" class="iconL" alt="${member.memberName}" />
${member.memberName}さん
<c:if test="${visibleCommentList}">のひとりごと</c:if>
<c:choose>
<c:when test="${button==1}">&nbsp;<a href="${contextPath}/following/add/${member.memberId}/member" class="action">メンバーに追加</a></c:when>
<c:when test="${button==2}">&nbsp;<a href="${contextPath}/following/add/${member.memberId}/member" class="action">追加リクエスト</a></c:when>
<c:when test="${button==3}">&nbsp;<span class="alert1">*追加リクエスト中*</span>&nbsp;<a href="${contextPath}/following/del/${member.memberId}/member" class="action">解除</a></c:when>
</c:choose>

</h1>

<div class="box">

<c:import url="/import/errorList.jsp" />

<c:set var="isUser" value="${f:containsKey(sessionScope, 'user') && sessionScope.user.memberId == member.memberId}" />
<c:set var="opParam" value="member/${member.memberName}/${pageNo}" />

<c:if test="${!empty commentList}">
	<div class="smileyOnOff">
		<img src="${contextPath}/image/smiley/regular_smile.gif" id="changeSmileyButton" class="toggleButtonOff" alt="絵文字を表示する" title="絵文字を表示する" />
	</div>
	<table class="comments">
	<c:forEach var="item" varStatus="s" items="${commentList}">
		<tr><td>
			<span class="comment">${f:out(item.comment)}</span>&nbsp;
			<a class="time" href="${contextPath}/comment/${item.commentId}">
				<fmt:formatDate value="${item.postTime}" pattern="yyyy/MM/dd(E) HH:mm:ss"/>
			</a>
			<c:choose>
				<c:when test="${empty item.favorite}">
				</c:when>
				<c:when test="${item.favorite}">
					<a href="${contextPath}/operation/fav/del/${item.commentId}/${opParam}" class="favorite">
						<img src="${contextPath}/image/star.png" alt="お気に入りから削除" title="お気に入りから削除" />
					</a>
				</c:when>
				<c:otherwise>
					<a href="${contextPath}/operation/fav/add/${item.commentId}/${opParam}" class="favorite">
						<img src="${contextPath}/image/bullet_star.png" alt="お気に入りに追加" title="お気に入りに追加" />
					</a>
				</c:otherwise>
			</c:choose>
			<c:if test="${isUser}">
				<a href="${contextPath}/operation/comment/del/${item.commentId}/${opParam}">
					<img src="${contextPath}/image/bin.png" alt="ひとりごとの削除" title="ひとりごとの削除" class="delete" />
				</a>
			</c:if>
		</td></tr>
	</c:forEach>
	</table>
	<div class="pager">
		<c:if test="${pagerViewHelper.prev}"><a href="${contextPath}/member/${member.memberName}/${pageNo-1}" style="border:1px solid #ccc; padding:3px">&lt;&lt; 前</a></c:if>
		<c:if test="${pagerViewHelper.next}"><a href="${contextPath}/member/${member.memberName}/${pageNo+1}" style="border:1px solid #ccc; padding:3px">次 &gt;&gt;</a></c:if>
	</div>
</c:if>
</div>
</div>
</div>

<c:import url="/import/memberMenu.jsp" />

</body>

</html>