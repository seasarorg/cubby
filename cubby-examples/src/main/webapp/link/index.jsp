<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<title>components.jsp</title>
<link href="${contextPath}/css/default.css" rel="stylesheet"
	type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[
<a href="${contextPath}/">戻る</a>
]
<h1>クラス名＋メソッド名でのリンク</h1>
<ul>
	<li>
		<t:link tag="a" attribute="href"
				actionclass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionmethod="foo">
			simple hyper reference
		</t:link>
	</li>
	<li>
		<t:link tag="a" attribute="href"
				actionclass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionmethod="bar">
			<t:linkparam name="id" value="123" />
			<t:linkparam name="token" value="abc" />
			hyper reference with parameters
		</t:link>
	</li>
	<li>
		<t:link tag="a" attribute="href"
				actionclass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionmethod="bar">
			<t:linkparam name="id" value="456" />
			<t:linkparam name="token" value="def" />
			<t:link tag="img" attribute="src"
					actionclass="org.seasar.cubby.examples.other.web.link.ImageAction"
					actionmethod="image">
				<t:linkparam name="token" value="nested link" />
			</t:link>
		</t:link>
	</li>
</ul>
<ul>
<li>id : ${id}</li>
<li>token : ${token}</li>
</ul>
</body>
</html>
