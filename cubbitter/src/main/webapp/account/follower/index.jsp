<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="${account.name}をフォローしている人" />
</c:import>

<body>

<div id="content">
<div class="wrap">

<h1 class="roundTop">${account.name}をフォローしている人</h1>

<div class="box">
	<c:choose>
		<c:when test="${empty account.followers}">
			<div class="message">${account.name}をフォローしている人はいません。</div>
		</c:when>
		<c:otherwise>
			<ul id="message">
				<c:forEach var="follower" varStatus="s" items="${account.followers}">
					<table class="comments">
						<tr>
							<td class="icon">
								<a href="${contextPath}/${follower.name}/">
									<img src="${contextPath}/${follower.name}/medium.jpg" class="iconM" alt="${follower.name}" />
								</a>
							</td>
							<td class="comment">
								<a href="${contextPath}/${follower.name}/" class="memberName">${follower.name}</a>
								 / ${f:out(follower.fullName)}
								<c:if test="${f:contains(follower.requests, account)}">
									&nbsp;<a href="${contextPath}/${account.name}/follower/${follower.name}/add?r=${r}" class="action">承認</a>
								</c:if>
<%--
									<c:if test="${empty follower.request}">
										&nbsp;<a href="${contextPath}/followers/add/${item.memberId}" class="action">追加</a>
									</c:if>
--%>
							</td>
						</tr>
					</table>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>
