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
<t:form method="post" action="fileupload_upload" enctype="multipart/form-data"
	value="${form}">

<h2>File upload</h2>
<label for="filename">Name:</label>
<t:input type="text" name="filename" id="filename"/><br/>

<label for="file">File:</label>
<t:input type="file" name="file" id="file"/><br/>

<input type="submit" value="アップロード"/>
</t:form>
</body>
</html>


