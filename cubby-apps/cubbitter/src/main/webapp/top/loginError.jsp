<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="ログイン" />
	<c:param name="jsFiles" value="login" />
</c:import>
<body>

<div id="content">
	<div class="wrap">
	    <h1 class="roundTop">ログイン</h1>
	    <div class="box">
		    <c:import url="/import/errorList.jsp" />
		    <div class="round table" style="width:300px">
		    	<c:import url="/import/login.jsp" />
		    </div>
	    </div>
	</div>
</div>

<c:import url="/import/noLoginMenu.jsp">
	<c:param name="hiddenLoginBox" value="true" />
</c:import>

</body>

</html>
