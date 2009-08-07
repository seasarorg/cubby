<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Cubby Examples - ソースビューア - ${f:out(title)}</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <link href="${contextPath}/css/prettify.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script type="text/javascript" src="${contextPath}/js/prototype.js"></script>
  <script type="text/javascript" src="${contextPath}/js/prettify.js"></script>
</head>
<body>
<h1>Cubby Exmaples - ソースビューア - ${f:out(title)}</h1>
<ul id="paths">
	<c:forEach var="path" items="${action.paths}">
		<c:choose>
			<c:when test="${path.visible}">
				<li>
					<a href="javascript:void(0)"
						onclick="loadSource(this, '${f:out(path.path)}');">${f:out(path.path)}</a>
				</li>
			</c:when>
			<c:otherwise>
				<li>
					<span>${f:out(path.path)} を参照することができません。</span>
				</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</ul>
<pre id="code" class="prettyprint">
&nbsp;
</pre>
</body>
<script type="text/javascript">
	function loadSource(currentElem, path) {
		Element.getElementsByClassName('paths', 'selected').each(
				function(elem, index) {
					Element.removeClassName(elem, 'selected');
				});
		Element.addClassName(currentElem, 'selected');
		var myAjax = new Ajax.Request('getSource', {
			method :'get',
			parameters : {
				path :path
			},
			onComplete : function(req) {
				var sourceCode = req.responseText;
				if (Prototype.Browser.IE) {
					sourceCode = sourceCode.replace(/\x0D\x0A|\x0D|\x0A/g,
							'\n\r');
				} else {
					sourceCode = sourceCode
							.replace(/\x0D\x0A|\x0D|\x0A/g, '\n');
				}
				var code = $('code');
				code.replaceChild(document.createTextNode(sourceCode),
						code.firstChild);
				prettyPrint();
			}
		});
	}
</script>
</html>
