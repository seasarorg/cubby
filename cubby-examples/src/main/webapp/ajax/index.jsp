<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Cubby Examples - Ajax</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <link rel="alternate" type="application/atom+xml" title="Cubby Examples - Atom" 
  	href="http://localhost:8080/cubby-examples/feed/atom" />
</head>
<script type="text/javascript">
function loadTextFromString() {
	new Ajax.Request("textFromString", {
		method: 'get', 
		onComplete: function(req) {
			$("textFromStringResult").innerHTML = req.responseText;
		}
	});
}
function loadJsonFromBean() {
	new Ajax.Request("jsonFromBean", {
		method: 'get', 
		onComplete: function(req) {
			eval("var obj = " + req.responseText);
			$("jsonFromBeanResult").innerHTML = obj.text;
		}
	});
}
function loadJsonFromMap() {
	new Ajax.Request("jsonFromMap", {
		method: 'get', 
		onComplete: function(req) {
			eval("var obj = " + req.responseText);
			$("jsonFromMapResult").innerHTML = obj.text;
		}
	});
}
function loadJsonFromCollection() {
	new Ajax.Request("jsonFromCollection", {
		method: 'get', 
		onComplete: function(req) {
			eval("var array = " + req.responseText);
			$("jsonFromCollectionResult").innerHTML = array.join(",");
		}
	});
}
</script>
<body>
<h1>Cubby Exmaples - Ajax</h1>
<h2>Text</h2>
<input type="button" value="call" onclick="loadTextFromString()"/>Result:<span id="textFromStringResult"></span>
<h2>Json from Bean</h2>
<input type="button" value="call" onclick="loadJsonFromBean()"/>Result:<span id="jsonFromBeanResult"></span>
<h2>Json from Map</h2>
<input type="button" value="call" onclick="loadJsonFromMap()"/>Result:<span id="jsonFromMapResult"></span>
<h2>Json from Collection</h2>
<input type="button" value="call" onclick="loadJsonFromCollection()"/>Result:<span id="jsonFromCollectionResult"></span>
</body>
</html>
