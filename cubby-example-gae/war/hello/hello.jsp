<%--

    Copyright 2004-2009 the Seasar Foundation and the Others.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied. See the License for the specific language
    governing permissions and limitations under the License.

--%>
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
  <title>Cuuby archetype sample app : /hello/message</title>
</head>
<body>
<div id="banner">
<img src="${contextPath }/img/logo.gif" alt="Cubby" />
</div>
<h1>Cuuby archetype sample app : /hello/message</h1>
${messages['msg.dummy']}<br/>
<c:import url="/common/errors.jsp"/>
<c:import url="/common/notice.jsp"/>
post message : [${message}]
<br/>
<a href="back">Back(redirect and flash message)</a>
</body>
</html>
