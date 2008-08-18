<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="新規アカウント登録" />
	<c:param name="jsFiles" value="memberNameCheck" />
</c:import>
	
<body id="register">

<div id="content">
<div class="wrap">

<h1 class="roundTop">新規登録</h1>
<div class="box">
<c:import url="/import/errorList.jsp" />

<t:form action="process" method="post" value="${action}">
<div class="round table" style="width:600px">

<table frame="void" rules="all">
	<col style="width:130px" />
	<col style="width:auto" />
	<tr>
		<th>ユーザID</th>
		<td>
			<t:input id="regMemberName" name="regMemberName" type="text" />
			<span id="alertNameChar" class="alert1" style="display:none">英数字または"_"のみ使用できます。</span>
			<span id="alertNameOverlap" class="alert2">a
				
			</span><!-- span id="checkedId" style="font-weight:bold"></span>は既に登録されています。  -->
		</td>
	</tr>
	<tr>
		<th>パスワード</th>
		<td>
			<t:input id="regPassword" name="regPassword" type="password" />
			<span id="alertPasswordChar" class="alert1">6文字以上入力してください。</span>
		</td>
	</tr>
	<tr>
		<th>Eメールアドレス</th>
		<td><t:input  id="regEmail" name="regEmail" type="text" /></td>
	</tr>
	<tr>
		<th>&nbsp;</th>
		<td><input type="submit" value="登録" class="buttonS" id="submitButton" /></td>
	</tr>
</table>

</div>
</t:form>
	
</div>

</div>
</div>

<c:import url="/import/noLoginMenu.jsp" />

</body>

</html>
