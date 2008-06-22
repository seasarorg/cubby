<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="設定" />
</c:import>

<body id="setting">

<div id="content">
<div class="wrap">

<h1 class="roundTop">追加リクエスト</h1>

<div class="box"><c:choose>
	<c:when test="${empty requestedMemberList}">
		<div class="message">追加リクエストはありません。</div>
	</c:when>
	<c:otherwise>
		<table class="comments">
			<c:forEach var="item" varStatus="s" items="${requestedMemberList}">

				<tr>
					<td class="icon"><a href="/cubbitter/member/gotou"> <img
						src="/cubbitter/picture/m/${item.memberId}" class="iconM"
						alt="${f:out(item.fullName)}" title="${f:out(item.fullName)}" />
					</a></td>

					<td class="comment"><a
						href="${contextPath}/member/${item.memberName}">${item.memberName}</a>
					/ ${f:out(item.fullName)} &nbsp;&nbsp;→ <a
						href="accept/${item.memberId}" class="action">容認</a> | <a
						href="deny/${item.memberId}" class="action">拒否</a></td>
				</tr>

			</c:forEach>
		</table>
	</c:otherwise>
</c:choose></div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>