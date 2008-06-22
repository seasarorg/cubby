<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<% request.setAttribute("contextPath", request.getContextPath()); %>
<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="Comment" />
	<%-- 
	<c:param name="jsFiles" value="comments" />
	<c:param name="cssFiles" value="comment" />
	 --%>
</c:import>
<body style="background-color:#99cc99;text-align:center;">
<a href="${contextPath}/"><img src="${contextPath}/image/logo.png" /></a>
<div class="roundB" style="width:500px;background-color:#ffffff;margin:0 auto;padding:80px 0">
	このページは存在しません。
	<br /><br /><br />
	<a href="${contextPath}/">ホーム</a>
</div>

</body>

</html>
