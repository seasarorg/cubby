<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div id="menu">
<div class="round box">
<a href="${contextPath}/top" id="logo"><img src="${contextPath}/image/logo.png" alt="cubbitter" title="cubbitter" /></a> 


<c:choose>
  <c:when test="${f:containsKey(sessionScope, 'user')}">
  <ul class="mainMenu">
 	<li><a href="${contextPath}/home/" class="roundA menu">ホーム</a></li>
 	<li><a href="${contextPath}/logout/" class="roundA menu">ログアウト</a></li>
	</ul>
  </c:when>
  <c:otherwise>
  	<div id="login"><c:import url="/import/login.jsp" /></div>
	<ul class="mainMenu"><li><a href="${contextPath}/register/" class="roundA menu">新規登録</a></li></ul>
  </c:otherwise>
</c:choose>

</div>

<c:if test="${!empty loginAccount}">
	<div class="round box">
	<ul class="memberInfo">
	<li>
		<h2>プロフィール</h2>
		<ul class="profile">
		<li><span class="label">名前：</span>${f:out(loginAccount.fullName)}</li>
		<c:if test="${!empty loginAccount.web}"><li><span class="label">Web：</span><a href="${loginAccount.web}">${loginAccount.web}</a></li></c:if>
		<c:if test="${!empty loginAccount.location}"><li><span class="label">住所：</span>${f:out(loginAccount.location)}</li></c:if>
		<c:if test="${!empty loginAccount.biography}"><li><span class="label">紹介：</span>${f:out(loginAccount.biography)}</li></c:if>
		</ul>
	</li>
	<li><h2>${loginAccount.name}さんのともだち</h2>
		<ul class="following">
		<c:forEach var="following" varStatus="s" items="${loginAccount.followings}">
			<li>
				<a href="${contextPath}/member/${following.name}" class="memberName">
				<img src="${contextPath}/picture/${following.name}/small.jpg" class="iconS" alt="${following.name}" title="${following.name}" />
				${following.name}</a>
			</li>
		</c:forEach>
		</ul>
	</li>
	
	</ul>
	</div>
</c:if>

</div>
