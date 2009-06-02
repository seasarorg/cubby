/// 共通処理 ///
contextPath = location.pathname.substring(0, location.pathname.indexOf("/", 1));

Event.observe(window, "load", roundCorners, false);

function roundCorners(){
	$$('a.roundA').each(function (val) {
		Rico.Corner.round(val, {compact:true, color:"transparent"})
	});
	$$('a.roundTopA').each(function (val) {
		Rico.Corner.round(val, {corners:"top", compact:true, color:"transparent"})
	});

	$$('.round').each(function (val) {
		Rico.Corner.round(val, {compact:true})
	});
	$$('.roundTop').each(function (val) {
		Rico.Corner.round(val, {corners:"top", compact:true})
	});
	$$('.roundB').each(function (val) {
		Rico.Corner.round(val, {compact:false})
	});
}

function errorFunc(httpObj){
	alert("エラーが発生しました。HTTPステータス：" + httpObj.status);
}