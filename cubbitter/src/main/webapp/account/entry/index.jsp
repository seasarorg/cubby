<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="${account.name}" />
	<c:param name="jsFiles" value="comments" />
</c:import>

<body>

<div id="content">
<div class="wrap">

<h1 class="roundTop">
	<c:choose>
		<c:when test="${account == loginAccount}">
			あなたのひとりごと
		</c:when>
		<c:otherwise>
			<img src="${contextPath}/${account.name}/large.jpg" class="iconL" alt="${account.name}" />
			${account.name}さんのひとりごと
			<c:if test="${!empty loginAccount && account != loginAccount && !f:contains(loginAccount.followings, account)}">
				<c:choose>
					<c:when test="${f:contains(account.requests, loginAccount)}">&nbsp;<span class="alert1">*追加リクエスト中*</span>&nbsp;<a href="${contextPath}/${loginAccount.name}/following/remove/${account.name}?r=${r}" class="action">解除</a></c:when>
					<c:when test="${account.open}">&nbsp;<a href="${contextPath}/${loginAccount.name}/following/add/${account.name}?r=${r}" class="action">メンバーに追加</a></c:when>
					<c:otherwise>&nbsp;<a href="${contextPath}/${loginAccount.name}/following/add/${account.name}?r=${r}" class="action">追加リクエスト</a></c:otherwise>
				</c:choose>
			</c:if>
		</c:otherwise>
	</c:choose>
</h1>

<div class="box">

<c:import url="/import/errorList.jsp" />

<c:import url="/import/entries.jsp" />

</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>
