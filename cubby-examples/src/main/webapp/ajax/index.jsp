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
function loadText() {
	new Ajax.Request("text", {
		method: 'get', 
		onComplete: function(req) {
			$("textResult").innerText = req.responseText;
		}
	});
}
function loadJson() {
	new Ajax.Request("json", {
		method: 'get', 
		onComplete: function(req) {
			eval("var obj = " + req.responseText);
			$("jsonResult").innerText = obj.text;
		}
	});
}
</script>
<body>
<h1>Cubby Exmaples - Ajax</h1>
<h2>Text result</h2>
<input type="button" value="call" onclick="loadText()"/>Result:<span id="textResult"></span>
<h2>Json result</h2>
<input type="button" value="call" onclick="loadJson()"/>Result:<span id="jsonResult"></span>
</body>
</html>
