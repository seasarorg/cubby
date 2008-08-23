/// 共通処理 ///
contextPath = location.pathname.substring(0, location.pathname.indexOf("/", 1));

Event.observe(window, "load", addEventCommon, false);

function addEventCommon(){
	//角丸
	Nifty("a.roundA", "transparent");
	Nifty("a.roundTopA", "top transparent");
	
	Nifty("*.round");
	Nifty("*.roundTop", "top");
	Nifty("*.roundB", "big");
}

function errorFunc(httpObj){
	alert("エラーが発生しました。HTTPステータス：" + httpObj.status);
}