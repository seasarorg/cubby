<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
<title>components.jsp</title>
<style>
h2 {
	border-left: solid 10px green;
	border-bottom: solid 1px green;
}
th {
	background: green;
}
.odd {
	background: #FED;
}
.even {
	background: #FFF;
}
</style>
</head>
<body>
<h2>File upload</h2>
<label for="file">File:</label>
アップロード完了しました。<br/>
ファイルラベル：${form.filename}<br/>
ファイル名：${form.file.name}<br/>
ファイルサイズ：${form.file.size}<br/>
</body>
</html>


