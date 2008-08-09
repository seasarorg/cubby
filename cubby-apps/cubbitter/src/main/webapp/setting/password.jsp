<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="設定 - パスワード変更" />
	<c:param name="jsFiles" value="memberNameCheck" />
</c:import>

<body id="passwordSetting">

<div id="content">
<div class="wrap">

<h1 class="roundTop">設定</h1>

<div id="submenu">
	<ul id="nav">
		<li><a href="${contextPath}/setting/" class="roundTopA">アカウント</a></li>
		<li><a href="${contextPath}/setting/password" class="roundTopA active">パスワード</a></li>
		<li><a href="${contextPath}/setting/picture" class="roundTopA">アイコン</a></li>
	</ul>
</div>
<br style="clear:both" />

<div class="box">
<c:import url="/import/errorList.jsp" />

<t:form action="updatePassword" method="post" value="${action}">
<div class="round table" style="width:600px">

<table class="content" frame="void" rules="all">
<col style="width:130px" />
<col style="width:auto" />
<tr>
	<th>パスワード</th>
	<td>
		<t:input type="password" name="password" id="password" />
		<span id="alertPasswordChar" class="alert1">6文字以上入力してください。</span>
	</td>
</tr>
<tr>
	<th>確認パスワード</th>
	<td>
		<t:input type="password" name="password2" id="password2" />
		<span id="alertVerifyPassword" class="alert2" style="display:none">確認パスワードが一致していません。</span>
	</td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td>
		<input type="submit" value="更新" class="buttonS" />
	</td>
</tr>
</table>

</div>
</t:form>

</div>

</div>
</div>

<c:import url="/import/defaultMenu.jsp" />

</body>

</html>