<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="${account.name}がフォローしている人" />
</c:import>
<body>

<div id="content">
<div class="wrap">

<h1 class="roundTop">${account.name}がフォローしている人</h1>

<div class="box">
	<c:choose>
		<c:when test="${empty account.followings}">
			<div class="message">${account.name}がフォローしている人はいません。</div>
		</c:when>
		<c:otherwise>
			<ul id="message">
				<c:forEach var="following" items="${account.followings}">
					<table class="comments">
						<tr>
							<td class="icon">
								<a href="${contextPath}/${following.name}/">
									<img src="${contextPath}/${following.name}/medium.jpg" class="iconM" alt="${following.name}" />
								</a>
							</td>
							<td class="comment">
								<a href="${contextPath}/${following.name}/" class="memberName">${following.name}</a>
								 / ${f:out(following.fullName)}
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

<c:import url="/import/menu.jsp" />

</body>

</html>
