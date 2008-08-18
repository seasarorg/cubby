/// ログインチェック処理 ///

Event.observe(window, "load", addEventLogin, false);

function addEventLogin() {
	var loginButton = $("loginButton");
	if (loginButton) {
		loginButton.onclick = login;
	}
}

// ログイン処理
function login() {
	var loginName = $F("loginName");
	var loginPassword = $F("loginPassword");
	try {
		if (loginName.length < 1) {
			alert("ユーザIDを入力してください。");
			return false;
		}
		if (loginPassword.length < 1) {
			alert("パスワードを入力してください。");
			return false;
		}
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
