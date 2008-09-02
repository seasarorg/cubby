<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<title>components.jsp</title>
<link href="${contextPath}/css/default.css" rel="stylesheet"
	type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[
<a href="${contextPath}/">戻る</a>
]
<h1>コンバータ</h1>
<c:import url="/common/errors.jsp" />

<c:if test="${!empty book}">
	<table>
		<thead></thead>
		<tbody>
			<tr>
				<th scope="col">タイトル</th>
				<td>${f:out(book.title)}</td>
			</tr>
			<tr>
				<th scope="col">ISBN</th>
				<td>${f:out(book.isbn13)}</td>
			</tr>
		</tbody>
	</table>
</c:if>

<ul>
	<c:forEach var="book" items="${books}">
		<li><a href="${book.isbn13}">${f:out(book.title)}</a></li>
	</c:forEach>
</ul>
<a href="978-4798006284">誤ったリンク(データベースに該当データがないので、バリデータによって 404 NOT FOUND を返します)</a>
</body>
</html>
