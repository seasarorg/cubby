<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
	<title>ホワイトボード - トップ</title>
	<link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[<a href="${contextPath}/">戻る</a>]
<t:form method="post" action="/cubby-examples/whiteboard/upload" enctype="multipart/form-data"
	value="${form}">

<h1>ファイルアップロード</h1>
<c:import url="/common/errors.jsp"/>
<label for="file">ファイル:</label>
<t:input type="file" name="file" id="file"/><br/>

<input type="submit" value="アップロード"/>
</t:form>
</body>
</html>


