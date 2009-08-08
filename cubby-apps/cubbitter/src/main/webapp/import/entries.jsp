<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${contextPath}/script/entries.js"></script>

<c:if test="${!empty loginAccount && !empty param.mypage}">
	<t:form action="${contextPath}/${loginAccount.name}/entry/add?r=${r}" method="post" value="${action}">
		<t:textarea name="text" id="entryArea" />
		<img src="${contextPath}/image/smiley/regular_smile.gif" alt="絵文字一覧の表示" title="絵文字一覧の表示" id="changeSmileyPanelButton" class="toggleButtonOff" />
		<input type="submit" value="送信" class="buttonS" />
		<div id="smileyInputPanel" style="display:none;"></div>
	</t:form>
</c:if>

<div class="smileyOnOff">
	<img src="${contextPath}/image/smiley/regular_smile.gif"
		id="changeSmileyButton" class="toggleButtonOff" alt="絵文字を表示する"
		title="絵文字を表示する" />
</div>

<c:if test="${!empty entries}">
	<table class="entries">
		<c:forEach var="entry" items="${entries}">
			<tr>
				<td class="icon">
					<a href="${contextPath}/${entry.account.name}/entry/">
						<img src="${contextPath}/${entry.account.name}/medium.jpg" class="iconM"
							alt="${f:out(entry.account.fullName)}"
							title="${f:out(entry.account.fullName)}" />
					</a>
				</td>
				<td class="entry">
					<a href="${contextPath}/${entry.account.name}/entry/" title="${f:out(entry.account.fullName)}" class="memberName">${entry.account.name}</a>
					<span class="entry">${f:out(entry.text)}</span>
					&nbsp;
					<a class="time" href="${contextPath}/entry/${entry.id}">
						<fmt:formatDate value="${entry.post}" pattern="yyyy/MM/dd(E) HH:mm:ss" />
					</a>

					<c:if test="${!empty loginAccount}">
						<c:choose>
							<c:when test="${f:contains(loginAccount.favorites, entry)}">
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
	
						<c:if test="${!empty param.mypage && entry.account.id != loginAccount.id}">
							<img src="${contextPath}/image/arrow.png" alt="${entry.account.name}に返信" title="${entry.account.name}に返信" class="reply" />
						</c:if>

						<c:if test="${entry.account.id == loginAccount.id}">
							<a href="${contextPath}/${loginAccount.name}/entry/remove/${entry.id}?r=${r}">
								<img src="${contextPath}/image/bin.png" alt="ひとりごとの削除" title="ひとりごとの削除" class="delete" />
							</a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${!empty pager}">
	<div class="pager">
		<c:if test="${pager.prev}">
			<c:choose>
				<c:when test="${pager.prevPageNo == 1}">
					<a href="." style="border: 1px solid #ccc; padding: 3px">&lt;&lt;前</a>
				</c:when>
				<c:otherwise>
					<a href="?pageNo=${pager.prevPageNo}" style="border: 1px solid #ccc; padding: 3px">&lt;&lt;前</a>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${pager.next}">
			<a href="?pageNo=${pager.nextPageNo}" style="border: 1px solid #ccc; padding: 3px">次&gt;&gt;</a>
		</c:if>
	</div>
</c:if>
