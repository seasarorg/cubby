<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="検索結果" />
	<c:param name="jsFiles" value="comments" />
</c:import>

<body>

<div id="content">
<div class="wrap">
<h1 class="roundTop">"${keyword}"の検索結果</h1>

<%--
<div id="submenu">
	<ul id="nav">
		<li><a href="${contextPath}/following/" class="roundTopA">あなたがフォローしている人</a></li>
		<li><a href="${contextPath}/follower/" class="roundTopA">あなたをフォローしている人</a></li>
	</ul>
</div>

<br style="clear:both" />
--%>

<div class="box">
<%--
<t:form action="${contextPath}/following/search" method="post" value="${action}" id="memsearch">
	<t:input type="text" name="keyword" id="keyword" />
	<input type="submit" value="探す" class="buttonS" />
</t:form>
--%>

<c:import url="/import/errorList.jsp" />

<table class="comments">
<c:forEach var="account" varStatus="s" items="${accounts}">
<tr>
<td class="icon">
	<a href="${contextPath}/${account.name}/">
		<img src="${contextPath}/${account.name}/medium.jpg" class="iconM" alt="${account.name}" />
	</a>
</td>

<td class="comment">
	<a href="${contextPath}/${account.name}/" class="memberName">${account.name}</a> / ${f:out(account.fullName)}
	<c:choose>
		<c:when test="${empty loginAccount}">
		</c:when>
		<c:when test="${account.id == loginAccount.id}">
			&nbsp;<span class="alert1">* It's You! *</span>
		</c:when>
		<c:when test="${f:contains(loginAccount.followings, account)}">
			&nbsp;<span class="alert1">* フォロー中 *</span>
		</c:when>
		<c:when test="${f:contains(account.requests, loginAccount)}">
			&nbsp;<span class="alert1">* 追加リクエスト中 *</span>
		</c:when>
		<c:when test="${!f:contains(loginAccount.followings, account) && !f:contains(loginAccount.followings, account)}">
			&nbsp;<a href="${contextPath}/${loginAccount.name}/following/add/${account.name}?r=${r}" class="action">追加</a>
		</c:when>
	</c:choose>
	
	<table class="searchAttribute">
		<c:if test="${!empty account.web}">
			<tr><th>URL</th><td><a href="${account.web}">${account.web}</a></td></tr>
		</c:if>
		<c:if test="${!empty account.biography}">
			<tr><th>紹介</th><td>${f:out(account.biography)}</td></tr>
		</c:if>
		<tr>
			<th>最新</th>
			<c:choose>
				<%-- 非公開で自分のリストになければコメントは見せない --%>
				<c:when test="${account.open == false && f:contains(loginAccount.followings, account)}">
					<td><span class="time">${messages[member.msg.noFollowing]}</span></td>
				</c:when>
				<%-- コメントがない場合 --%>
				<c:when test="${empty account.entries}">
					<td><span class="comment">${messages[member.msg.noComment]}</span></td>
				</c:when>
				<c:otherwise>
					<c:forEach var="entry" items="${account.entries}" end="0">
						<td><span class="comment">${f:out(entry.text)}</span>&nbsp;<span class="time">${entry.post}</span></td>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>
</td>
</tr>
</c:forEach>
</table>
<%--
<div class="pager">
	<c:if test="${pagerViewHelper.prev}"><a href="${contextPath}/following/search/${pageNo-1}/${encodeKeyword}" style="border:1px solid #ccc; padding:3px">&lt;&lt; 前</a></c:if>
	<c:if test="${pagerViewHelper.next}"><a href="${contextPath}/following/search/${pageNo+1}/${encodeKeyword}" style="border:1px solid #ccc; padding:3px">次 &gt;&gt;</a></c:if>
</div>
--%>

</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>
