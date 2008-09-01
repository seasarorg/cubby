<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="${entry.account.name}: ${entry.text}" />
	<c:param name="cssFiles" value="comment" />
</c:import>
<body>

<div class="body">

<div class="roundB balloon1">
	<span class="comment">${entry.text}</span><br />
	<span class="time"><fmt:formatDate value="${entry.post}" pattern="yyyy/MM/dd(E) HH:mm:ss"/></span>
	<c:if test="${!empty loginAccount}">
		<c:choose>
			<c:when test="${f:contains(loginAccount.favorites, entry)}}">
				<a href="${contextPath}/${loginAccount.name}/favorite/remove/${entry.id}?r=${r}" class="favorite">
					<img src="${contextPath}/image/star.png" alt="お気に入りから削除" title="お気に入りから削除" />
				</a>
			</c:when>
			<c:otherwise>
				<a href="${contextPath}/${loginAccount.name}/favorite/add/${entry.id}?r=${r}" class="favorite">
					<img src="${contextPath}/image/bullet_star.png" alt="お気に入りに追加" title="お気に入りに追加" />
				</a>
			</c:otherwise>
		</c:choose>
		<c:if test="${account == loginAccount}">
			<a href="${contextPath}/${loginAccount.name}/entry/remove/${entry.id}?r=${r}">
				<img src="${contextPath}/image/bin.png" alt="ひとりごとの削除" title="ひとりごとの削除" class="delete" />
			</a>
		</c:if>
	</c:if>
	
</div>
<div class="round balloon2">&nbsp;</div>
<div class="round balloon3">&nbsp;</div>
<div class="round balloon4">&nbsp;</div>
<div class="member">
	<a href="${contextPath}/${entry.account.name}/">
	<img src="${contextPath}/${entry.account.name}/large.jpg" class="iconL" />
	${entry.account.fullName}</a>
</div>

</div>

</body>

</html>
