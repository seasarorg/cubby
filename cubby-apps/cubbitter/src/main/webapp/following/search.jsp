<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="検索" />
	<c:param name="jsFiles" value="comments" />
</c:import>

<body>

<div id="content">
<div class="wrap">
<h1 class="roundTop">メンバー</h1>

<div id="submenu">
	<ul id="nav">
		<li><a href="${contextPath}/following/" class="roundTopA">登録している人</a></li>
		<li><a href="${contextPath}/following/follower" class="roundTopA">登録されている人</a></li>
		<li><a href="${contextPath}/following/presearch" class="roundTopA active">検索</a></li>
	</ul>
</div>

<br style="clear:both" />

<div class="box">
<t:form action="${contextPath}/following/search" method="post" value="${action}" id="memsearch">
	<t:input type="text" name="keyword" id="keyword" />
	<input type="submit" value="探す" class="buttonS" />
</t:form>

<c:import url="/import/errorList.jsp" />

<table class="comments">
<c:forEach var="item" varStatus="s" items="${searchResultList}">
<tr>
<td class="icon">
	<a href="${contextPath}/member/${item.memberName}">
		<img src="${contextPath}/picture/m/${item.memberId}" class="iconM" alt="${item.memberName}" />
	</a>
</td>

<td class="comment">
	<a href="${contextPath}/member/${item.memberName}" class="memberName">${item.memberName}</a> / ${f:out(item.fullName)}
	
	<c:choose>
		<c:when test="${sessionScope.user.memberId == item.memberId}">
			&nbsp;<span class="alert1">* It's You! *</span>
		</c:when>
		<c:when test="${empty item.request}">
			&nbsp;<a href="${contextPath}/following/add/${item.memberId}/${encodedParam}" class="action">追加</a>
		</c:when>
		<c:when test="${item.request}">
			&nbsp;<span class="alert1">*追加リクエスト中*</span>
		</c:when>
	</c:choose>
	
	<table class="searchAttribute">
		<c:if test="${!empty item.web}">
			<tr><th>URL</th><td><a href="${item.web}">${item.web}</a></td></tr>
		</c:if>
		<c:if test="${!empty item.biography}">
			<tr><th>紹介</th><td>${f:out(item.biography)}</td></tr>
		</c:if>
		<tr>
			<th>最新</th>
			<c:choose>
				<%-- 非公開で自分のリストになければコメントは見せない --%>
				<c:when test="${item.open == false && item.request != false }">
					<td><span class="time">${messages['member.msg.noFollowing']}</span></td>
				</c:when>
				<%-- コメントがない場合 --%>
				<c:when test="${empty item.comment}">
					<td><span class="time">${messages['member.msg.noComment']}</span></td>
				</c:when>
				<c:otherwise>
					<td><span class="comment">${f:out(item.comment)}</span>&nbsp;<span class="time">${item.postTime}</span></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>
</td>
</tr>
</c:forEach>
</table>
<div class="pager">
	<c:if test="${pagerViewHelper.prev}"><a href="${contextPath}/following/search/${pageNo-1}/${encodeKeyword}" style="border:1px solid #ccc; padding:3px">&lt;&lt; 前</a></c:if>
	<c:if test="${pagerViewHelper.next}"><a href="${contextPath}/following/search/${pageNo+1}/${encodeKeyword}" style="border:1px solid #ccc; padding:3px">次 &gt;&gt;</a></c:if>
</div>

</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>