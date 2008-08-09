<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="Comment" />
	<c:param name="jsFiles" value="comments" />
	<c:param name="cssFiles" value="comment" />
</c:import>
<body>

<div class="body">

<div class="roundB balloon1">
	<span class="comment">${comment.comment}</span><br />
	<span class="time"><fmt:formatDate value="${comment.postTime}" pattern="yyyy/MM/dd(E) HH:mm:ss"/></span>
	<c:choose>
		<c:when test="${empty comment.favorite}">
		</c:when>
		<c:when test="${comment.favorite}">
			<a href="${contextPath}/operation/fav/del/${comment.commentId}/comment" class="favorite">
				<img src="${contextPath}/image/star.png" alt="お気に入りから削除" title="お気に入りから削除" />
			</a>
		</c:when>
		<c:otherwise>
			<a href="${contextPath}/operation/fav/add/${comment.commentId}/comment" class="favorite">
				<img src="${contextPath}/image/bullet_star.png" alt="お気に入りに追加" title="お気に入りに追加" />
			</a>
		</c:otherwise>
	</c:choose>
	<c:if test="${f:containsKey(sessionScope, 'user') && comment.memberId==sessionScope.user.memberId}">
		<a href="${contextPath}/operation/comment/del/${comment.commentId}/comment">
			<img src="${contextPath}/image/bin.png" alt="ひとりごとの削除" title="ひとりごとの削除" class="delete" />
		</a>
	</c:if>
	
</div>
<div class="round balloon2">&nbsp;</div>
<div class="round balloon3">&nbsp;</div>
<div class="round balloon4">&nbsp;</div>
<div class="member">
	<a href="${contextPath}/member/${comment.memberName}">
	<img src="${contextPath}/picture/l/${comment.memberId}" class="iconL" />
	${comment.fullName}</a>
</div>

</div>

</body>

</html>