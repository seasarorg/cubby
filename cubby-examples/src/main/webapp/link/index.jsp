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
		<t:link tag="a" attr="href"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="foo">
			hyper reference (simple)
		</t:link>
		- link to "<t:link actionClass="org.seasar.cubby.examples.other.web.link.LinkAction" actionMethod="foo" />"
	</li>
	<li>
		<t:link tag="a" attr="href"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="bar">
			<t:param name="id" value="123" />
			<t:param name="token" value="abc" />
			hyper reference (with parameters)
		</t:link>
		- link to "<t:link actionClass="org.seasar.cubby.examples.other.web.link.LinkAction" actionMethod="bar">
			<t:param name="id" value="123" />
			<t:param name="token" value="abc" />
		</t:link>"
	</li>
	<li>
		<t:link tag="a" attr="href"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="bar">
			<t:param name="id" value="456" />
			<t:param name="token" value="d/e f" />
			<t:link tag="img" attr="src"
					actionClass="org.seasar.cubby.examples.other.web.link.ImageAction"
					actionMethod="image">
				<t:param name="token" value="nested link" />
			</t:link>
		</t:link>
	</li>
	<li>
		<t:form method="POST" value="${action}"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="bar">
			<t:param name="id" value="7890" />
			<fieldset>
				<legend>form ("action" attribute)</legend>
				<label for="token">token:</label><t:input name="token" type="text" />
				<t:input name="submit" value="submit" type="submit" />
				<t:link tag="img" attr="src"
						actionClass="org.seasar.cubby.examples.other.web.link.ImageAction"
						actionMethod="image">
					<t:param name="token" value="@ link in form" />
				</t:link>
			</fieldset>
		</t:form>
	</li>
	<li>
		<t:link tag="a" attr="href"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="foo"
                protocol="https">
			hyper reference (with protocol)
		</t:link>
		- link to "<t:link actionClass="org.seasar.cubby.examples.other.web.link.LinkAction" actionMethod="foo" protocol="https"/>"
	</li>
	<li>
		<t:link tag="a" attr="href"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="foo"
                port="9999">
			hyper reference (with port)
		</t:link>
		- link to "<t:link actionClass="org.seasar.cubby.examples.other.web.link.LinkAction" actionMethod="foo" port="9999"/>"
	</li>
	<li>
		<t:form method="POST" value="${action}"
				actionClass="org.seasar.cubby.examples.other.web.link.LinkAction"
				actionMethod="bar" protocol="https">
			<t:param name="id" value="7890" />
			<fieldset>
				<legend>form ("action" attribute with protocol)</legend>
				<label for="token">token:</label><t:input name="token" type="text" />
				<t:input name="submit" value="submit" type="submit" />
			</fieldset>
		</t:form>
	</li>
</ul>
<hr/>
<ul>
<li>id : ${id}</li>
<li>token : ${token}</li>
</ul>
</body>
</html>
