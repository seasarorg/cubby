// cookieの有効期限（日）
var COOKIE_EXPIRE_DAY = 30;

/// コメント変換処理 ///
Event.observe(window, "load", addEventComments, false);

function addEventComments(){

	// 絵文字リスト
	var smileyList = getSmileyList();
	
	// 入力パネル
	if($("smileyInputPanel")){
		loadSmileyInputPanel(smileyList);
	}

	// コンバータ
	var converterList = Array();
	converterList.push(new AnchorConverter());
	converterList = converterList.concat(smileyList);
	converterList.push(new MemberAnchorConverter());
	
	// コメントを変換
	convertComments(converterList);

	// Smiley表示・非表示ボタン
	if($("changeSmileyButton")){
		Event.observe($("changeSmileyButton"), "click", changeSmileyVisible, false);
	}
	
	// Smiley入力パネル表示・非表示ボタン
	if($("changeSmileyPanelButton")){
		Event.observe($("changeSmileyPanelButton"), "click", changeSmileyPanelVisible, false);
		// reply矢印イベント登録
		addArrowClickEvent();
	}
}

//コメントを置き換える
function convertComments(converterList){
	var spans = $$("span.comment");
	for(var i=0; i<spans.length; i++){
		for(var j=0; j<converterList.length; j++){
			converterList[j].convert(spans[i]);
		}
	}
}
//絵文字コンバータ取得
function getSmileyList(){
	var visibleSmiley = true;
	
	// cookieから読む
	if(document.cookie.indexOf("visibleSmiley=0") > -1){	
		visibleSmiley = false;
	}else if($("changeSmileyButton")){
		setToggleImageParam($("changeSmileyButton"), "toggleButtonOn", "絵文字を表示しない");
	}
	
	//絵文字リスト取得
	return createSmileyConverterList(visibleSmiley);
}
//絵文字入力パネル読み込み
function loadSmileyInputPanel(smileyList){
	for(var i=0; i<smileyList.length; i++){
		if(i>0 && smileyList[i-1].image == smileyList[i].image){
			continue;
		}
		$("smileyInputPanel").appendChild(smileyList[i].createInputImg());
	}
	// 絵文字一覧を表示する
	if(document.cookie.indexOf("visibleSmileyPanel=1") > -1){	
		$("smileyInputPanel").style.display = "block";
		setToggleImageParam($("changeSmileyPanelButton"), "toggleButtonOn", "絵文字一覧を隠す");
	}
}

//絵文字ON・OFF切り替え時
function changeSmileyVisible(event){
	var button = Event.element(event);
	
	var visible;
	
	if(button.className == "toggleButtonOff"){
		setToggleImageParam(button, "toggleButtonOn", "絵文字を表示しない");
		visible = true;
		setVisibleSmiley(1);
	}else{
		setToggleImageParam(button, "toggleButtonOff", "絵文字を表示する");
		visible = false;
		setVisibleSmiley(0);
	}
	
	// smileyの場所を取得
	var spans = $$("span.smiley");
	
	for(var i=0; i<spans.length; i++){
		var nodes = spans[i].childNodes;
		
		for(var j=0; j<nodes.length; j++){
			if(nodes[j].nodeType == 1){
				var name = nodes[j].nodeName;
				if((visible && name == "IMG") || (!visible && name == "SPAN")){
					//表示する
					nodes[j].style.display = "inline";
				}else if((visible && name == "SPAN") || (!visible && name == "IMG")){
					//非表示にする
					nodes[j].style.display = "none";
				}
			}
		}
	}
}
// Smiley入力パネル表示・非表示ボタン
function changeSmileyPanelVisible(event){
	var img = Event.element(event);
	
	var div = $("smileyInputPanel");
	if(div.style.display == "none"){
		div.style.display = "block";
		setToggleImageParam(img, "toggleButtonOn", "絵文字一覧を隠す");
		setVisibleSmileyPanel(1);
	}else{
		div.style.display = "none";
		setToggleImageParam(img, "toggleButtonOff", "絵文字一覧の表示");
		setVisibleSmileyPanel(0);
	}
}

//Cookieに保存する
function setVisibleSmiley(flag){
	setCookie("visibleSmiley", flag);
}
function setVisibleSmileyPanel(flag){
	setCookie("visibleSmileyPanel", flag);
}
function setCookie(paramName, flag){
	expires = new Date();
    expires.setTime(expires.getTime() + (COOKIE_EXPIRE_DAY * 1000 * 60 * 60 * 24));
    expires = expires.toGMTString();
　　document.cookie = paramName + "=" + flag + ";path=" + contextPath + ";expires=" + expires;
}
//Imageにaltとtitleを設定
function setToggleImageParam(image, className, alt){
	image.className = className;
	image.alt = alt;
	image.title = alt;
}


// *** タグコンバータクラス *** //
var TagConverter = function(isRegExp, pattern, createNodeFunc) {
	this.isRegExp = isRegExp;
	this.pattern = pattern;
	this.createNodeFunc = createNodeFunc
};
TagConverter.prototype = {
	convert : function(pNode) {
		this.insertNodes(pNode, 0);
	},
	insertNodes : function(pNode, start) {
		var nodes = pNode.childNodes;
		var node = null, i, patternIndex

		// patternを検索
		for(i=start; i<nodes.length; i++){
			if(nodes[i].nodeType == 3){
				if(this.isRegExp){
					patternIndex = nodes[i].nodeValue.search(this.pattern);
				}else{
					patternIndex = nodes[i].nodeValue.indexOf(this.pattern);
				}
				if(patternIndex > -1){
					node = nodes[i];
					break;
				}	
			}
		}
		
		//見つからなければ終了
		if(node == null){
			return;
		}
		
		// マッチした文字
		var matchStr;
		if(this.isRegExp){
			matchStr = RegExp.$1;
		}else{
			matchStr = this.pattern;
		}
		
		// ノードを作成（テキストノード＋Newエレメント＋テキストノード）
		var nodes = Array();
		var text1 = document.createTextNode(node.nodeValue.substring(0,patternIndex));
		var text2 = document.createTextNode(node.nodeValue.substring(patternIndex + matchStr.length));
		var newNode = this.createNodeFunc(matchStr);
		
		// ノードを追加
		pNode.insertBefore(text2, node);
		pNode.insertBefore(newNode, text2);
		pNode.insertBefore(text1, newNode);
		
		// 元のテキストノードを削除
		pNode.removeChild(node);
		
		this.insertNodes(pNode, i+1);
	}
};
// *** アンカーコンバータ ***
var AnchorConverter = function() {
	this.isRegExp = true;
};
AnchorConverter.prototype =	new TagConverter();
AnchorConverter.prototype.pattern = new RegExp("(https?://[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+)");
AnchorConverter.prototype.createNodeFunc = function(text){
	var a = document.createElement("a");
	a.href = text;
	a.target = "_blank";
	a.appendChild(document.createTextNode(text));
	return a;
};
// *** ユーザIDリンクコンバータ ***
var MemberAnchorConverter = function(){
	this.isRegExp = true;
};
MemberAnchorConverter.prototype =	new TagConverter();
MemberAnchorConverter.prototype.pattern = new RegExp("(@[_a-zA-Z0-9]+)");
MemberAnchorConverter.prototype.createNodeFunc = function(text){
	var memberName = text.substring(1);
	
	var a = document.createElement("a");
	a.href = contextPath + "/member/" + memberName;
	a.appendChild(document.createTextNode(memberName));
	
	var span = document.createElement("span");
	span.appendChild(document.createTextNode("@"));
	span.appendChild(a);
	
	return span;
};

// *** 絵文字コンバータ ***
var SmileyConverter = function(pattern, image, title){
	this.isRegExp = false;	
	this.pattern = pattern;
	this.image = image;
	this.title = title + " " + pattern;
	this.visibleSmiley = false;
};
SmileyConverter.prototype =	new TagConverter();
SmileyConverter.prototype.createNodeFunc =function(text){
	var span1 = document.createElement("span");
   	if(this.visibleSmiley){
   		span1.style.display = "none";
   	}
    span1.appendChild(document.createTextNode(this.pattern));
    
    var img = this.createImg();
    if(!this.visibleSmiley){
    	img.style.display = "none";
    }
    
   	var span2 = document.createElement("span");
    span2.className = "smiley";
    span2.appendChild(span1);
    span2.appendChild(img);
    
    return span2;
};
SmileyConverter.prototype.createInputImg = function(){
	var img = this.createImg();
	Event.observe(img, "click", inputSmileyIcon.bindAsEventListener(this, this.pattern), false);
	return img;
};
SmileyConverter.prototype.createImg = function(){
	var img = document.createElement("img");
	img.src = contextPath + "/image/smiley/" + this.image;
	img.alt = this.title;
	img.title = this.title;
	return img;
};

//絵文字の追加
function inputSmileyIcon(event, value){
	$("commentArea").value = $F("commentArea") + value;
	$("commentArea").focus();
}

// reply矢印イベント登録
function addArrowClickEvent(){
	//矢印の場所を取得
	var spans = $$("img.reply");
	for(var i=0; i<spans.length; i++){
		Event.observe(spans[i], "click", addReplyTo, false);
	}
}

function addReplyTo(event, value){
	var span = Event.element(event);
	var nodes = span.parentNode.childNodes;
	var name;
	for(i=0; i<nodes.length; i++){
		if(nodes[i].nodeType == 1 && nodes[i].nodeName == "A"){
			name = nodes[i].firstChild.nodeValue;
			break;
		}
	}
	if(!name){
		return;
	}
	
	// 元の@～を削除
	var str = $F("commentArea").replace(/^@[_a-zA-Z0-9]+\s*/, '');
	$("commentArea").value = "@" + name + " " + str;
	$("commentArea").focus();
}


// 置き換えデータ
function createSmileyConverterList(visibleSmiley){
	var arr = new Array(
		new SmileyConverter(":-)", "regular_smile.gif", "スマイル"),
		new SmileyConverter(":)", "regular_smile.gif", "スマイル"),
		new SmileyConverter(":-D", "teeth_smile.gif", "口を開けたスマイル"),
		new SmileyConverter(":d", "teeth_smile.gif", "口を開けたスマイル"),
		new SmileyConverter(":-O", "omg_smile.gif", "驚いたスマイル"),
		new SmileyConverter(":o", "omg_smile.gif", "驚いたスマイル"),
		new SmileyConverter(":-P", "tounge_smile.gif", "舌を出したスマイル"),
		new SmileyConverter(":p", "tounge_smile.gif", "舌を出したスマイル"),
		new SmileyConverter(";-)", "wink_smile.gif", "ウインクをしたスマイル"),
		new SmileyConverter(";)", "wink_smile.gif", "ウインクをしたスマイル"),
		new SmileyConverter(":-(", "sad_smile.gif", "悲しいスマイル"),
		new SmileyConverter(":(", "sad_smile.gif", "悲しいスマイル"),
		new SmileyConverter(":-S", "confused_smile.gif", "混乱したスマイル"),
		new SmileyConverter(":s", "confused_smile.gif", "混乱したスマイル"),
		new SmileyConverter(":-|", "whatchutalkingabout_smile.gif", "がっかりしたスマイル"),
		new SmileyConverter(":|", "whatchutalkingabout_smile.gif", "がっかりしたスマイル"),
		new SmileyConverter(":'(", "cry_smile.gif", "泣いているスマイル"),
		new SmileyConverter(":$", "embaressed_smile.gif", "恥ずかしがるスマイル"),
		new SmileyConverter(":-$", "embaressed_smile.gif", "恥ずかしがるスマイル"),
		new SmileyConverter("(H)", "shades_smile.gif", "サングラスをしたスマイル"),
		new SmileyConverter(":-@", "angry_smile.gif", "怒ったスマイル"),
		new SmileyConverter(":@", "angry_smile.gif", "怒ったスマイル"),
		new SmileyConverter("(A)", "angel_smile.gif", "天使"),
		new SmileyConverter("(a)", "angel_smile.gif", "天使"),
		new SmileyConverter("(L)", "heart.gif", "赤いハート"),
		new SmileyConverter("(l)", "heart.gif", "赤いハート"),
		new SmileyConverter("(U)", "broken_heart.gif", "ブロークン ハート"),
		new SmileyConverter("(u)", "broken_heart.gif", "ブロークン ハート"),
		new SmileyConverter("(K)", "kiss.gif", "赤い唇"),
		new SmileyConverter("(k)", "kiss.gif", "赤い唇"),
		new SmileyConverter("(G)", "present.gif", "プレゼント"),
		new SmileyConverter("(g)", "present.gif", "プレゼント"),
		new SmileyConverter("(F)", "rose.gif", "赤いバラ"),
		new SmileyConverter("(f)", "rose.gif", "赤いバラ"),
		new SmileyConverter("(W)", "wilted_rose.gif", "しおれたバラ"),
		new SmileyConverter("(w)", "wilted_rose.gif", "しおれたバラ"),
		new SmileyConverter("(P)", "camera.gif", "カメラ"),
		new SmileyConverter("(p)", "camera.gif", "カメラ"),
		new SmileyConverter("(~)", "film.gif", "映画フィルム"),
		new SmileyConverter("(T)", "phone.gif", "受話器"),
		new SmileyConverter("(t)", "phone.gif", "受話器"),
		new SmileyConverter("(@)", "kittykay.gif", "猫"),
		new SmileyConverter("(&)", "bowwow.gif", "犬"),
		new SmileyConverter("(C)", "coffee.gif", "コーヒー カップ"),
		new SmileyConverter("(c)", "coffee.gif", "コーヒー カップ"),
		new SmileyConverter("(I)", "lightbulb.gif", "電球"),
		new SmileyConverter("(i)", "lightbulb.gif", "電球"),
		new SmileyConverter("(S)", "moon.gif", "半月"),
		new SmileyConverter("(s)", "moon.gif", "半月"),
		new SmileyConverter("(*)", "star.gif", "星"),
		new SmileyConverter("(8)", "musical_note.gif", "8 分音符"),
		new SmileyConverter("(E)", "envelope.gif", "封筒"),
		new SmileyConverter("(e)", "envelope.gif", "封筒"),
		new SmileyConverter("(^)", "cake.gif", "誕生日ケーキ"),
		new SmileyConverter("(O)", "clock.gif", "時計"),
		new SmileyConverter("(o)", "clock.gif", "時計"),
		new SmileyConverter("(Y)", "thumbs_up.gif", "賛成"),
		new SmileyConverter("(y)", "thumbs_up.gif", "賛成"),
		new SmileyConverter("(N)", "thumbs_down.gif", "反対"),
		new SmileyConverter("(n)", "thumbs_down.gif", "反対"),
		new SmileyConverter("(B)", "beer_yum.gif", "ビール ジョッキ"),
		new SmileyConverter("(b)", "beer_yum.gif", "ビール ジョッキ"),
		new SmileyConverter("(D)", "martini_shaken.gif", "マティーニ グラス"),
		new SmileyConverter("(d)", "martini_shaken.gif", "マティーニ グラス"),
		new SmileyConverter("(X)", "girl_handsacrossamerica.gif", "女の子"),
		new SmileyConverter("(x)", "girl_handsacrossamerica.gif", "女の子"),
		new SmileyConverter("(Z)", "guy_handsacrossamerica.gif", "男の子"),
		new SmileyConverter("(z)", "guy_handsacrossamerica.gif", "男の子"),
		new SmileyConverter("(6)", "devil_smile.gif", "悪魔"),
		new SmileyConverter(":-[", "bat.gif", "吸血コウモリ"),
		new SmileyConverter(":[", "bat.gif", "吸血コウモリ"),
		new SmileyConverter("(})", "girl_hug.gif", "抱擁 (右)"),
		new SmileyConverter("({)", "dude_hug.gif", "抱擁 (左)"),
		new SmileyConverter("(M)", "messenger1.gif", "Windows Messenger のアイコン"),
		new SmileyConverter("(m)", "messenger1.gif", "Windows Messenger のアイコン")
	);
	for(var i=0; i<arr.length; i++){
		arr[i].visibleSmiley = visibleSmiley;
	}
	return arr;
}

