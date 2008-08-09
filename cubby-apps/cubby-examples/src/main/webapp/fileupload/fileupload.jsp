<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
	<title>components.jsp</title>
	<link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[<a href="${contextPath}/">戻る</a>]
<t:form method="post" action="${contextPath}/fileupload/upload" enctype="multipart/form-data"
	value="${action}">

<h1>ファイルアップロード</h1>
拡張子が「png」「jpg」のファイルのみアップロードできます。<br/>
<c:import url="/common/errors.jsp"/>
<label for="filename">ファイルラベル:</label>
<t:input type="text" name="filename"/><br/>

<label for="file">ファイル:</label>
<t:input type="file" name="file"/><br/>

<input type="submit" value="アップロード"/>
</t:form>
</body>
</html>


