/// ログインチェック処理 ///

Event.observe(window, "load", addEventLogin, false);

function addEventLogin(){
	if($("loginButton")){
		$("loginButton").onclick = login;
	}
}

// ログイン処理
function login(){
	try{
		if($F("memberName").length < 1){
			alert("ユーザIDを入力してください。");
			return false;
		}
		if($F("password").length < 1){
			alert("パスワードを入力してください。");
			return false;
		}
		var url = contextPath + "/login/check?memberName=" + encodeURIComponent($F("memberName")) + "&password=" + encodeURIComponent($F("password"));
		new Ajax.Request(url, {method:"get", onSuccess:checkLoginResult, onFailure:errorFunc});
	}catch(e){
		alert(e.toString());
	}
	return false;
}

// ログインチェック結果
function checkLoginResult(httpObj){
	var ret = eval("(" + httpObj.responseText + ")");
	if(ret.isError){
		alert(ret.errorMessage);
	}else{
		$("loginForm").submit();
	}
}
