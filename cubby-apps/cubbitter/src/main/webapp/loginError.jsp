<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="ログイン" />
</c:import>
<body>

<div id="content">
	<div class="wrap">
	    <h1 class="roundTop">ログインに失敗しました</h1>
	    <div class="box">
		    <c:import url="/import/errorList.jsp" />
	    </div>
	</div>
</div>

<c:import url="/import/menu.jsp" />

</body>

</html>
