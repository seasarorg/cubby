<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<t:form action="${contextPath}/login/" method="post" value=""
	id="loginForm">
	<table>
		<tr>
			<th scope="col">ユーザID</th>
			<td><t:input type="text" name="loginName" id="memberName" /></td>
		</tr>
		<tr>
			<th scope="col">パスワード</th>
			<td><t:input type="password" name="loginPassword" id="password" /></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td><input type="submit" value="ログイン" id="loginButton" /></td>
		</tr>
	</table>
</t:form>
<script>
Event.observe(window, "load", addEventLogin, false);

function addEventLogin() {
//	var loginForm = $("loginForm");
	new Form.Element.Observer("loginName"), 1,
		function(element, value) {
			if (value.length < 1) {
				
			}
		}
	);
}

function login() {
	var loginName = $F("loginName");
	var loginPassword = $F("loginPassword");
	try {
//		if (loginName.length < 1) {
//			alert("ユーザIDを入力してください。");
//			return false;
//		}
//		if (loginPassword.length < 1) {
//			alert("パスワードを入力してください。");
//			return false;
//		}
		var url = contextPath + "/login/check?loginName="
				+ encodeURIComponent(loginName) + "&loginPassword="
				+ encodeURIComponent(loginPassword);
		new Ajax.Request(url, {
			method :"get",
			onSuccess :checkLoginResult,
			onFailure :errorFunc
		});
	} catch (e) {
		alert(e.toString());
	}
	return false;
}

// ログインチェック結果
function checkLoginResult(httpObj) {
	var ret = eval("(" + httpObj.responseText + ")");
	if (ret.isError) {
		alert(ret.errorMessage);
	} else {
		$("loginForm").submit();
	}
}
</script>
