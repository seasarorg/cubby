<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">

<c:import url="/import/htmlHeader.jsp">
	<c:param name="title" value="ともだちのひとりごと" />
</c:import>

<body id="home">

<div id="content">
<div class="wrap">

<h1 class="roundTop">ともだちのひとりごと</h1>
<div class="box">
<c:import url="/import/errorList.jsp" />

<%--
<t:form action="${contextPath}/${account.name}/entry/add?r=${r}" method="post" value="${action}">
	<t:textarea name="text" id="entryArea"></t:textarea>
	<img src="${contextPath}/image/smiley/regular_smile.gif" alt="絵文字一覧の表示" title="絵文字一覧の表示" id="changeSmileyPanelButton" class="toggleButtonOff" />
	<input type="submit" value="送信" class="buttonS" />
	<div id="smileyInputPanel" style="display:none;"></div>
</t:form>
--%>

<c:import url="/import/entries.jsp">
	<c:param name="mypage" value="true" />
</c:import>
</div>

</div>
</div>

<c:import url="/import/menu.jsp" />

</body>

</html>
