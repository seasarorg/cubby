<div id="menu">
<div class="round box">
<a href="${contextPath}/top" id="logo"><img src="${contextPath}/image/logo.png" alt="cubbitter" title="cubbitter" /></a> 


<c:choose>
  <c:when test="${f:containsKey(sessionScope, 'user')}">
  <ul class="mainMenu">
 	<li><a href="${contextPath}/home/" class="roundA menu">ホーム</a></li>
 	<li><a href="${contextPath}/login/logout" class="roundA menu">ログアウト</a></li>
	</ul>
  </c:when>
  <c:otherwise>
  	<div id="login"><c:import url="/import/login.jsp" /></div>
	<ul class="mainMenu"><li><a href="${contextPath}/register/" class="roundA menu">新規登録</a></li></ul>
  </c:otherwise>
</c:choose>

</div>

<c:if test="${!empty member}">
	<div class="round box">
	<ul class="memberInfo">
	<li>
		<h2>プロフィール</h2>
		
		<ul class="profile">
		<li><span class="label">名前：</span>${f:out(member.fullName)}</li>
		<c:if test="${!empty member.web}"><li><span class="label">Web：</span><a href="${member.web}">${member.web}</a></li></c:if>
		<c:if test="${!empty member.location}"><li><span class="label">住所：</span>${f:out(member.location)}</li></c:if>
		<c:if test="${!empty member.biography}"><li><span class="label">紹介：</span>${f:out(member.biography)}</li></c:if>
		</ul>
	</li>
	<li><h2>${member.memberName}さんのメンバー</h2>
		<ul class="following">
		<c:forEach var="item" varStatus="s" items="${followingList}">
			<li>
				<a href="${contextPath}/member/${item.memberName}" class="memberName">
				<img src="${contextPath}/picture/s/${item.memberId}" class="iconS" alt="${item.memberName}" title="${item.memberName}" />
				${item.memberName}</a>
			</li>
		</c:forEach>
		</ul>
	</li>
	
	</ul>
	</div>
</c:if>

</div>