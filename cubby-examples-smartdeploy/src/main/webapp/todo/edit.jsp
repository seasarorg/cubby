<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<t:template extend="/template.jsp">
<jsp:attribute name="title">Todo編集</jsp:attribute>
<jsp:attribute name="content">
<h2>Todo編集</h2>
<t:errors />
[<a href="${contextPath}/todo/">一覧に戻る</a>]
<t:form action="confirm" method="post" value="${action}">
<t:input type="hidden" name="id" />
<table border="1">
  <tr>
    <th>タイトル</th>
    <td>
	<t:input type="text" maxlength="50" name="text"  /></td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>
		<t:select id="typeId" name="typeId"
			items="${todoTypes}" labelProperty="name" valueProperty="id" emptyOptionLabel="選択してください。" />
    </td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>
	<t:input type="text" maxlength="12" name="limitDate"/>(YYYY-MM-DD)</td>
  </tr>
  <tr>
    <th>メモ</th>
  <td>
    <t:textarea name="memo" value="${f:out(memo)}"/>
  </td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="次へ"/></td>
  </tr>
</table>
</t:form>
</jsp:attribute>
</t:template>