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
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/event/event.js" ></script>
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/dom/dom.js"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/logger/logger.js"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/dragdrop/dragdrop.js" ></script>
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/animation/animation.js" ></script>
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/connection/connection.js" ></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/whiteboard/js/yui/container/assets/container.css" />
<script type="text/javascript" src="${contextPath}/whiteboard/js/yui/container/container.js"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/Cubby.js"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/Event.js"></script>
<script type="text/javascript" src="${contextPath}/whiteboard/whiteboard.js"></script>

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


