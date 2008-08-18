/// ユーザID重複チェック処理 ///

Event.observe(window, "load", addEventMemberNameCheck, false);

function addEventMemberNameCheck(){
	//登録画面
	if($("register")){
		addCheckNameEvent("regMemberName");
		addPasswordEvent("regPassword");
	}
	//設定画面
	else if($("setting")){
		addCheckNameEvent("memberName");
	}
	//パスワード画面
	else if($("passwordSetting")){
		addPasswordEvent("password");
		//パスワード確認
		new Form.Element.Observer("password2", 1, checkVerifyPassword);
	}
}

function addCheckNameEvent(memberNameId){
	new Form.Element.Observer(memberNameId, 1, checkName);
	checkName($(memberNameId), $F(memberNameId));
}
function addPasswordEvent(passwordId){
	new Form.Element.Observer(passwordId, 1, checkPassword);
	checkPassword($(passwordId), $F(passwordId));
}

function checkPassword(element, value){
	var spanMessage = $("alertPasswordChar");
	if(value.length == 0){
		spanMessage.style.display = "inline";
		spanMessage.className = "alert1";	
	}
	else if(value.length < 6){
		spanMessage.style.display = "inline";
		spanMessage.className = "alert2";
	}
	else{
		spanMessage.style.display = "none";
	}
	checkVerifyPassword($("password2"), $F("password2"));
}
function checkName(element, value){
	var spanMessage = $("alertNameChar");
	$("alertNameOverlap").style.display = "none";
	if(value.length == 0){
		//空の場合
		spanMessage.style.display = "inline";
		spanMessage.className = "alert1";
		return;	
	}
	else if(!value.match(/^[0-9a-zA-Z_]+$/)){
		//不正な文字が入っている場合
		spanMessage.style.display = "inline";
		spanMessage.className = "alert2";
		return;
	}
	spanMessage.style.display = "none";	
	//$("checkedId").innerHTML = value;
	
	new Ajax.Request("checkName/" + value, {method:"get", onSuccess:checkNameResult, onFailure:errorFunc});
}
function checkNameResult(httpObj){
	var ret = eval("(" + httpObj.responseText + ")");
	if(ret.isError){
		$("alertNameOverlap").innerHTML = ret.errorMessage;
		$("alertNameOverlap").style.display = "inline";
	}
}
function checkVerifyPassword(element, value){
	var span = $("alertVerifyPassword");
	if(value == $F("password")){
		span.style.display = "none";
	}else{
		span.style.display = "inline";
	}
}
