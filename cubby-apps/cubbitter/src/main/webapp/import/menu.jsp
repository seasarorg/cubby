<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div id="menu">
<div class="round box">
<a href="${contextPath}/" id="logo" class="roundA"><img src="${contextPath}/image/logo.png" alt="cubbitter" title="cubbitter" /></a>
<t:form action="${contextPath}/search/" method="get" value="">
	<t:input type="text" id="member" name="keyword" />
	<input type="submit" value="検索" class="buttonS" />
</t:form>

<ul class="mainMenu">
	<c:choose>
		<c:when test="${empty loginAccount}">
			<li>
				<t:form action="${contextPath}/login/" method="post" value="" id="loginForm">
				<table>
					<tr>
						<th>ユーザID</th>
						<td><t:input type="text" name="loginName" id="loginName" /></td>
					</tr>
					<tr>
						<th>パスワード</th>
						<td><t:input type="password" name="loginPassword"
							id="loginPassword" /></td>
					</tr>
				</table>
				<div id="loginButtonBox">
					<t:input type="submit" name="login" value="ログイン" id="loginButton" />
				</div>
				<div id="loginErrorMessageBox">
					<ul id="loginErrorMessageList"></ul>
				</div>
			</t:form>
			</li>
			<li><a href="${contextPath}/register/" class="roundA menu">新規登録</a></li>
		</c:when>
		<c:otherwise>
			<li>
				<a href="${contextPath}/${loginAccount.name}/">
					<img src="${contextPath}/${loginAccount.name}/small.jpg" class="iconS"
						alt="${f:out(loginAccount.fullName)}"
						title="${f:out(loginAccount.fullName)}" />
				</a>
				${loginAccount.name}
			</li>
			<c:if test="${!empty loginAccount.requests}">
				<li><a id="request" class="roundA" href="${contextPath}/${loginAccount.name}/request/">${fn:length(loginAccount.requests)}件のフォロー要求があります</a></li>
			</c:if>
			<li><a href="${contextPath}/${loginAccount.name}/friend/" class="menu">ともだちのひとりごと</a></li>
			<li><a href="${contextPath}/${loginAccount.name}/reply/" class="menu">あなた宛てのひとりごと</a></li>
			<li><a href="${contextPath}/${loginAccount.name}/entry/" class="menu">あなたのひとりごと</a></li>
			<li><a href="${contextPath}/" class="menu">みんなのひとりごと</a></li>
			<li><a href="${contextPath}/setting/" class="menu">設定</a></li>
			<li><a href="${contextPath}/logout/" class="menu">ログアウト</a></li>
		</c:otherwise>
	</c:choose>
</ul>
<c:if test="${empty account && !empty loginAccount}">
	<c:set var="account" value="${loginAccount}" />
</c:if>
<c:if test="${!empty account}">
	<ul class="mainMenu">
		<c:if test="${account != loginAccount}">
			<li>
				<a href="${contextPath}/${account.name}/">
					<img src="${contextPath}/${account.name}/small.jpg" class="iconS"
						alt="${f:out(account.fullName)}"
						title="${f:out(account.fullName)}" />
				</a>
				${account.name}
				<c:choose>
					<c:when test="${empty loginAccount}"></c:when>
					<c:when test="${account.id == loginAccount.id}">
						&nbsp;<span class="alert1">* It's You! *</span>
					</c:when>
					<c:when test="${f:contains(loginAccount.followings, account)}">
						&nbsp;<span class="alert1">* フォロー中 *</span>
					</c:when>
					<c:when test="${f:contains(account.requests, loginAccount)}">
						&nbsp;<span class="alert1">* フォロー要求中 *</span>
					</c:when>
				</c:choose>
				<ul class="profile">
					<li>
						<c:choose>
							<c:when test="${empty loginAccount}"></c:when>
							<c:when test="${f:contains(loginAccount.followings, account)}">
								<a href="${contextPath}/${loginAccount.name}/following/remove/${account.name}?r=${r}" class="action">フォローをやめる</a>
							</c:when>
							<c:when test="${f:contains(account.requests, loginAccount)}">
								<a href="${contextPath}/${loginAccount.name}/following/remove/${account.name}?r=${r}" class="action">フォロー要求を解除</a>
							</c:when>
							<c:when test="${account.open}">
								<a href="${contextPath}/${loginAccount.name}/following/add/${account.name}?r=${r}" class="action">フォローする</a>
							</c:when>
							<c:otherwise>
								<a href="${contextPath}/${loginAccount.name}/following/add/${account.name}?r=${r}" class="action">フォローを要求する</a>
							</c:otherwise>
						</c:choose>
					</li>
					<li><span class="label">名前：</span>${f:out(account.fullName)}</li>
					<c:if test="${!empty account.web}"><li><span class="label">Web：</span><a href="${account.web}">${account.web}</a></li></c:if>
					<c:if test="${!empty account.location}"><li><span class="label">住所：</span>${f:out(account.location)}</li></c:if>
					<c:if test="${!empty account.biography}"><li><span class="label">紹介：</span>${f:out(account.biography)}</li></c:if>
				</ul>
			</li>
		</c:if>
		<c:if test="${account == loginAccount || account.open || f:contains(loginAccount.followings, account)}">
			<li>
				<a href="${contextPath}/${account.name}/following/" class="menu">${account.name}がフォロー</a>
				<c:forEach var="following" items="${account.followings}">
					<a href="${contextPath}/${following.name}/" title="${f:out(following.fullName)}">
						<img src="${contextPath}/${following.name}/small.jpg" class="iconS" alt="${f:out(following.fullName)}" />
					</a>
				</c:forEach>
			</li>
			<li><a href="${contextPath}/${account.name}/follower/" class="menu">${account.name}をフォロー</a></li>
			<li><a href="${contextPath}/${account.name}/favorite/" class="menu">${account.name}のお気に入り</a></li>
		</c:if>
	</ul>
</c:if>
</div>
</div>
<c:if test="${empty loginAccount}">
<script type="text/javascript" src="${contextPath}/script/login.js"></script>
</c:if>
