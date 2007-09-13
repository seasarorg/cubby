<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<t:template extend="/template.jsp">
<jsp:attribute name="title">Todo編集確認</jsp:attribute>
<jsp:attribute name="content">
<script language="javascript">
<!--
function doBack() {
	document.forms[0].action='${contextPath}/todo/confirm_back';
	document.forms[0].submit();
}
-->
</script>
<h2>Todo編集確認</h2>
以下の内容で登録しますがよろしいですか？
<div class="menu">
[<a href="javascript:doBack()">戻る</a>]
</div>
<t:form action="${contextPath}/todo/save" method="post" value="${action}">
<t:input type="hidden" name="id"/>
<t:input type="hidden" name="text"/>
<t:input type="hidden" name="typeId"/>
<t:input type="hidden" name="limitDate" value="${limitDate}"/>
<t:input type="hidden" name="memo"/>
<table border="1">
  <tr>
    <th>内容</th>
    <td>${f:out(text)}</td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>${f:out(todoType.name)}</td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>${f:out(limitDate)}</td>
  </tr>
  <tr>
    <th>メモ</th>
    <td>${f:out(memo)}</td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="登録"/></td>
  </tr>
</table>
</t:form>
</jsp:attribute>
</t:template>