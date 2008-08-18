<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<div class="smileyOnOff">
	<img src="${contextPath}/image/smiley/regular_smile.gif"
		id="changeSmileyButton" class="toggleButtonOff" alt="絵文字を表示する"
		title="絵文字を表示する" />
</div>

<c:if test="${!empty entries}">
	<table class="comments">
		<c:forEach var="entry" varStatus="s" items="${entries}">
			<c:if test="${entry.account.open || f:contains(account.followings, entry.account)}">
				<tr>
					<td class="icon">
						<a href="${contextPath}/${entry.account.name}/">
							<img src="${contextPath}/${entry.account.name}/medium.jpg" class="iconM"
								alt="${f:out(entry.account.fullName)}"
								title="${f:out(entry.account.fullName)}" />
						</a>
					</td>
					<td class="comment">
						<a href="${contextPath}/${entry.account.name}/" title="${f:out(entry.account.fullName)}" class="memberName">${entry.account.name}</a>
						<span class="comment">${f:out(entry.text)}</span>
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
		
							<c:if test="${entry.account.id == loginAccount.id}">
								<a href="${contextPath}/${loginAccount.name}/entry/remove/${entry.id}?r=${r}">
									<img src="${contextPath}/image/bin.png" alt="ひとりごとの削除" title="ひとりごとの削除" class="delete" />
								</a>
							</c:if>
						</c:if>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</c:if>
<c:if test="${!empty pager}">
	<div class="pager">
		<c:if test="${pager.prev}">
			<a href="?pageNo=${pageNo - 1}" style="border: 1px solid #ccc; padding: 3px">&lt;&lt;前</a>
		</c:if>
		<c:if test="${pager.next}">
			<a href="?pageNo=${pageNo + 1}" style="border: 1px solid #ccc; padding: 3px">次&gt;&gt;</a>
		</c:if>
	</div>
</c:if>