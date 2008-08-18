<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="設定 - プロフィール" />
	<c:param name="jsFiles" value="memberNameCheck" />
</c:import>

<body id="setting">

<div id="content">
<div class="wrap">

<h1 class="roundTop">設定</h1>

<div id="submenu">
	<ul id="nav">
		<li><a href="${contextPath}/setting/" class="roundTopA active">プロフィール</a></li>
		<li><a href="${contextPath}/setting/password" class="roundTopA">パスワード</a></li>
		<li><a href="${contextPath}/setting/picture" class="roundTopA">アイコン</a></li>
	</ul>
</div>
<br style="clear:both" />

<div class="box">
<c:import url="/import/errorList.jsp" />

<t:form action="update" method="post" value="${action}">
<div class="round table" style="width:600px">

<table class="content" frame="void" rules="all">
<col style="width:130px" />
<col style="width:auto" />
<tr>
	<th>${messages['setting.memberName']}</th>
	<td>
		<t:input type="text" name="name" id="name" />
		<span id="alertNameChar" class="alert1">英数字または"_"のみ使用できます。</span>
		<span id="alertNameOverlap" class="alert2" style="display:none"></span>
	</td>
</tr>
<tr>
	<th>${messages['setting.fullName']}</th>
	<td><t:input type="text" name="fullName" id="fullName" /></td>
</tr>
<tr>
	<th>${messages['setting.email']}</th>
	<td><t:input type="text" name="mail" id="mail" /></td>
</tr>
<tr>
	<th>${messages['setting.web']}</th>
	<td><t:input type="text" name="web" id="web" /></td>
</tr>
<tr>
	<th>${messages['setting.biography']}</th>
	<td><t:input type="text" name="biography" id="biography" /></td>
</tr>
<tr>
	<th>${messages['setting.location']}</th>
	<td><t:input type="text" name="location" id="location" /></td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td><t:input type="checkbox" name="open" id="open" value="true" /><label for="open">みんなのひとりごとにいれる</label></td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td>
		<input type="submit" value="更新" class="buttonS" />
		<input type="submit" name="delete" onclick="return confirm('ほんとにいいの？');" value="アカウントの削除" />
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
