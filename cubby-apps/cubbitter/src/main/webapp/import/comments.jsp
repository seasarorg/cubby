<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div class="smileyOnOff">
	<img src="${contextPath}/image/smiley/regular_smile.gif" id="changeSmileyButton" class="toggleButtonOff" alt="絵文字を表示する" title="絵文字を表示する" />
</div>

<c:if test="${!empty commentList}">

<table class="comments">
<c:forEach var="item" varStatus="s" items="${commentList}">
	<tr>
		<td class="icon">
			<a href="${contextPath}/member/${item.memberName}">
				<img src="${contextPath}/picture/m/${item.memberId}" class="iconM" alt="${f:out(item.fullName)}" title="${f:out(item.fullName)}" />
			</a>
		</td>
		<td class="comment">
			<a href="${contextPath}/member/${item.memberName}" title="${f:out(item.fullName)}" class="memberName">${item.memberName}</a>
			<span class="comment">${f:out(item.comment)}</span>&nbsp;
			<a class="time" href="${contextPath}/comment/${item.commentId}">
				<fmt:formatDate value="${item.postTime}" pattern="yyyy/MM/dd(E) HH:mm:ss"/>
			</a>
			<c:choose>
				<c:when test="${empty item.favorite}">
				</c:when>
				<c:when test="${item.favorite}">
					<a href="${contextPath}/operation/fav/del/${item.commentId}/${param.returnPage}/${pageNo}" class="favorite">
						<img src="${contextPath}/image/star.png" alt="お気に入りから削除" title="お気に入りから削除" />
					</a>
				</c:when>
				<c:otherwise>
					<a href="${contextPath}/operation/fav/add/${item.commentId}/${param.returnPage}/${pageNo}" class="favorite">
						<img src="${contextPath}/image/bullet_star.png" alt="お気に入りに追加" title="お気に入りに追加" />
					</a>
				</c:otherwise>
			</c:choose>
			<c:if test="${param.returnPage=='home'}">
				<img src="${contextPath}/image/arrow.png" alt="${item.memberName}さん宛て" title="${item.memberName}さん宛て" class="reply"　/>
			</c:if>
			<c:if test="${f:containsKey(sessionScope, 'user') && item.memberId==sessionScope.user.memberId}">
				<a href="${contextPath}/operation/comment/del/${item.commentId}/${param.returnPage}/${pageNo}">
					<img src="${contextPath}/image/bin.png" alt="ひとりごとの削除" title="ひとりごとの削除" class="delete" />
				</a>
			</c:if>
		</td>
	</tr>
</c:forEach>
</table>
</c:if>

<div class="pager">
	<c:if test="${pagerViewHelper.prev}"><a href="${pageNo-1}" style="border:1px solid #ccc; padding:3px">&lt;&lt; 前</a></c:if>
	<c:if test="${pagerViewHelper.next}"><a href="${pageNo+1}" style="border:1px solid #ccc; padding:3px">次 &gt;&gt;</a></c:if>
</div>
