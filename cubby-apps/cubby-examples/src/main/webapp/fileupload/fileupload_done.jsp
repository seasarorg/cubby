<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
	<title>components.jsp</title>
	<link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
</head>
<body>
[<a href="${contextPath}/fileupload/">戻る</a>]
<h2>ファイルアップロード結果</h2>
<label for="file">File:</label>
アップロード完了しました。<br/>
ファイルラベル：${action.filename}<br/>
ファイル名：${action.file.name}<br/>
ファイルサイズ：${action.file.size}<br/>
</body>
</html>


