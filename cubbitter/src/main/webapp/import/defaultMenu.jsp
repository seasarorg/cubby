<div id="menu">
<div class="round box">
<a href="${contextPath}/home/" id="logo" class="roundA"><img src="${contextPath}/image/logo.png" alt="cubbitter" title="cubbitter" /></a>

<ul class="mainMenu">
<li><a href="${contextPath}/home/" class="roundA menu">ホーム</a></li>
<li><a href="${contextPath}/reply" class="roundA menu">わたし宛て<%-- <span class="count">(${replyCommentCount})</span>--%></a></li>
<li><a href="${contextPath}/favorite" class="roundA menu">お気に入り<%-- <span class="count">(${favoriteCommentCount})</span>--%></a></li>
<li><a href="${contextPath}/top/" class="roundA menu">みんなのひとりごと</a></li>
<li><a href="${contextPath}/member/${sessionScope.user.memberName}" class="roundA menu">わたしのページ</a></li>
<li><a href="${contextPath}/setting/" class="roundA menu">設定</a></li>
<li><a href="${contextPath}/login/logout" class="roundA menu">ログアウト</a></li>
</ul>
<ul class="mainMenu">
<li><a href="${contextPath}/following/" class="roundA menu">メンバー</a>
	
	<c:if test="${followRequestedCount>0}">
		<a id="request" class="roundA" href="${contextPath}/request/">追加リクエスト <span class="count">(${followRequestedCount})</span></a>
	</c:if>
	<t:form action="${contextPath}/following/search" method="post" value="">
		<t:input type="text" id="member" name="keyword" />
		<input type="submit" value="探す" class="buttonS" />
	</t:form>
	<ul class="following">
	<c:forEach var="item" varStatus="s" items="${followingList}">
		<li>
			<a href="${contextPath}/member/${item.memberName}" title="${f:out(item.fullName)}">
			<img src="${contextPath}/picture/s/${item.memberId}" class="iconS" alt="${f:out(item.fullName)}" />
			${item.memberName}</a>
		</li>
	</c:forEach>
	</ul>
</li>
</ul>
</div>
</div>