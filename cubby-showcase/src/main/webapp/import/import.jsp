<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>コンポーネント化されたJSPページのImport</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
</head>
<body>
<h1>コンポーネント化されたJSPページのImport</h1>
<h2>1.c:importを使用したサンプル<br/>
（S2やDBデータと絡まない単純なパターン）</h2>
<c:import url="img.jsp">
	<c:param name="x" value="100"/>
	<c:param name="y" value="100"/>
	<c:param name="imageUrl" value="http://www.seasar.org/images/seasar_logo_blue.gif"/>
</c:import>
<c:import url="img.jsp">
	<c:param name="x" value="200"/>
	<c:param name="y" value="200"/>
	<c:param name="imageUrl" value="http://www.seasar.org/images/seasar_banner.gif"/>
</c:import>
<h2>2.c:importを使用したサンプル<br/>
（S2やDBデータを読み込み先のJSPのスクリプトレットで直接使用(WTPの使用をおすすめ)）</h2>
<c:import url="img_scriptlet.jsp"/>
</body>
</html>
