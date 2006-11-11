<t:template extend="/todo/template.jsp">
<jsp:attribute name="title">Todo編集確認</jsp:attribute>
<jsp:attribute name="content">
<h1>Todo編集確認</h1>
以下の内容で登録しますがよろしいですか？
<div class="menu">
[<a href="javascript:doBack();">戻る</a>]
</div>
<t:form action="save" method="post" value="${todo}">
<t:input type="hidden" name="id"/>
<t:input type="hidden" name="text"/>
<t:input type="hidden" name="typeId"/>
<t:input type="hidden" name="limitDate"/>
<t:input type="hidden" name="memo"/>
<table border="1">
  <tr>
    <th>内容</th>
    <td>${f:out(todo.text)}</td>
  </tr>
  <tr>
    <th>優先度</th>
    <td>${f:out(todo.type.name)}</td>
  </tr>
  <tr>
    <th>期限日</th>
    <td>${f:dateFormat(todo.limitDate, 'yyyy-MM-dd')}</td>
  </tr>
  <tr>
    <th>メモ</th>
    <td>${f:out(todo.memo)}</td>
  </tr>
  <tr>
    <th></th>
    <td><input type="submit" value="登録"/></td>
  </tr>
</table>
</t:form>
</jsp:attribute>
</t:template>