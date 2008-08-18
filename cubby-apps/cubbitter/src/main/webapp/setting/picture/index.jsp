<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="設定 - アイコン" />
</c:import>

<body id="setting">

<div id="content">
<div class="wrap">

<h1 class="roundTop">設定</h1>


<div id="submenu">
	<ul id="nav">
		<li><a href="${contextPath}/setting/" class="roundTopA">プロフィール</a></li>
		<li><a href="${contextPath}/setting/password" class="roundTopA">パスワード</a></li>
		<li><a href="${contextPath}/setting/picture" class="roundTopA active">アイコン</a></li>
	</ul>
</div>

<br style="clear:both" />

<div class="box">

<c:import url="/import/errorList.jsp" />

<t:form method="post" action="update" enctype="multipart/form-data"
        value="${action}">

<div class="round table" style="width:400px">
	<table class="content" frame="void" rules="all">
		<tr>
			<th>
				
				<img src="${contextPath}/${loginAccount.name}/medium.jpg" />
				
			</th>
			<td>
				<t:input type="file" name="file" style="width:100%" />
				<div class="alert1">拡張子が「jpg」「png」「gif」のファイルのみアップロードできます。<div>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" value="更新" class="buttonS" /></td>
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
