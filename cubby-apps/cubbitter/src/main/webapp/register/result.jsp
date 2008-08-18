<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="新規アカウント登録完了" />
</c:import>

<body>

<div id="content">
<div class="wrap">
<h1 class="roundTop">登録完了</h1>

<div class="box">
	<c:import url="/import/errorList.jsp" />

	<div class="round message">
	以下の情報で登録完了しました。
	</div>
	
	<div class="round table" style="width:300px;">
		<table frame="void" rules="all">
		<tr><th>ユーザID</th><td class="content">${regMemberName}</td></tr>
		<tr><th>Eメールアドレス</th><td class="content">${regEmail}</td></tr>
		</table>
	</div>
</div>
</div>
</div>

<c:import url="/import/defaultMenu.jsp" />
</body>

</html>
