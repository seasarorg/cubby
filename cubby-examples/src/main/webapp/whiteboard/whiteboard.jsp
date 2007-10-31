<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
	<title>ホワイトボード - 表示</title>
	<link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
<style type="text/css">
body {
  margin: 0;
  padding: 0;
}
</style>

<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.3.0/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/yahoo/yahoo-min.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/dom/dom-min.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/event/event-min.js"></script> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/animation/animation-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/connection/connection-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.3.0/build/container/container-min.js"></script>

<script type="text/javascript" src="${contextPath}/whiteboard/Cubby.js" charset="utf-8"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/Event.js" charset="utf-8"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/whiteboard.js" charset="utf-8"></script>

</head>
<body id="body">
[<a href="${contextPath}/">戻る</a>]
<h2>ID=${action.id}</h2>
<div id="console"></div>
<img src="${contextPath}/whiteboard/api/image/${action.id}" />
</body>
<script type="text/javascript">
new Figure(document.body, { console: document.getElementById("console") });
/*
var sx;
var sy;
document.getElementById("body").onmousedown = function(e) {
	sx = e.pageX;
	sy = e.pageY;
}
document.getElementById("body").onmouseup = function(e) {
	if (sx && sy) {
		var ex = e.pageX;
		var ey = e.pageY;
		
		var x = ex - sx;
		var y = ey - sy;
		//alert("x=" + x + ",y=" + y);
		
		var leftX = Math.min(sx, ex);
		var topY = Math.min(sy, ey);
		//alert("top=" + topY + ",left=" + leftX);
		
		var div = document.createElement('div');
		//div.setAttribute("id", activity.id);
		//div.onclick =  clickActivityDiagram;
		//div.onmouseover =  mouseOverActivityDiagram;
		document.getElementById("body").appendChild(div);
		with (div.style) {
			backgroundColor = 'gray';
			width = Math.abs(x) + "px";
			height = Math.abs(y) + "px";
			left = leftX + 'px';
			top = topY + 'px';
			position = 'absolute';
			zIndex = '3';
		}
		var img = document.createElement('img');
		img.className = 'line';
		//img.setAttribute("id", "dummy");
		//img.setAttribute("activityId", activity.id);
		div.appendChild(img);
		img.width = Math.abs(x);
		img.height = Math.abs(y);
		img.src = "/cubby-examples/whiteboard/api/line/" + x + "/" + y + "/";
dd = new YAHOO.util.DD(div);
dd.setXConstraint(1000, 1000, TICK_SIZE);
dd.setYConstraint(1000, 1000, TICK_SIZE);
//dd.onDrag = onDragModel;
dd.onDragDrop = function(e) {
alert(e);
  }
	}
}
*/
</script>
</html>


