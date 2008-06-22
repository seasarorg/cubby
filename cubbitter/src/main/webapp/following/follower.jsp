<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="登録されている人" />
</c:import>

<body>

<div id="content">
<div class="wrap">

<h1 class="roundTop">メンバー</h1>

<div id="submenu">
	<ul id="nav">
		<li><a href="${contextPath}/following/" class="roundTopA">登録している人</a></li>
		<li><a href="${contextPath}/following/follower" class="roundTopA active">登録されている人</a></li>
		<li><a href="${contextPath}/following/presearch" class="roundTopA">検索</a></li>
	</ul>
</div>

<br style="clear:both" />

<div class="box">

<c:choose>
<c:when test="${empty followerList}">
	<div class="message">登録されている人はいません。</div>
</c:when>
<c:otherwise>

	<ul id="message">
		<c:forEach var="item" varStatus="s" items="${followerList}">
			<table class="comments">
			<tr>
				<td class="icon">
					<a href="${contextPath}/member/${item.memberName}">
						<img src="${contextPath}/picture/m/${item.memberId}" class="iconM" alt="${item.memberName}" />
					</a>
				</td>
				<td class="comment">
					<a href="${contextPath}/member/${item.memberName}" class="memberName">${item.memberName}</a> / ${f:out(item.fullName)}
					<c:if test="${empty item.request}">
						&nbsp;<a href="${contextPath}/following/add/${item.memberId}/follower" class="action">追加</a>
					</c:if>
				</td>
			</tr>
			</table>
		</c:forEach>
	</ul>
	
</c:otherwise>
</c:choose>
</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>