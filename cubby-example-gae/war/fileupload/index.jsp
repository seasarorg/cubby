<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.seasar.org/cubby/tags" prefix="t" %>
<%@ taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath }/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <title>Cuuby archetype sample app : fileupload</title>
</head>
<body>
<div id="banner">
<img src="${contextPath }/img/logo.gif" alt="Cubby" />
</div>
<c:import url="/common/errors.jsp"/>
<c:import url="/common/notice.jsp"/>
<t:form method="post" action="${contextPath}/fileupload/upload" enctype="multipart/form-data" value="${form}">
<h1>Cuuby archetype sample app : /fileupload/</h1>
拡張子が「png」「jpg」のファイルのみアップロードできます。<br/>
<label for="file">ファイル:</label>
<t:input type="file" name="file"/><br/>
<input type="submit" value="アップロード"/>
</t:form>
</body>
</html>