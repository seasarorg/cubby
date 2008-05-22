<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja" xml:lang="ja">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <link href="${contextPath}/css/prettify.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
  <script src="${contextPath}/js/prettify.js" type="text/javascript"></script> 
  <script src="js/jquery-1.2.5.min.js" type="text/javascript" ></script>
  <title>Snippets</title>
</head>
<body>
<div id="header">
<h1><a href="${contextPath}">Snippets</a>::<a href="${contextPath}/${language.path}">${language.name}</a></h1>
</div>
<div id="sidebar">
<a href="${contextPath}/${language.path}/create">post snippet!</a>
<c:import url="/common/lang_list.jsp"/>
</div>
<div id="main">
<c:import url="common/notice.jsp"/>
<t:form action="" value="${action}" method="get">
<t:input id="search_query" name="query" type="input" size="50" value="aa"/>
<t:input name="submit" type="submit" value="Search"/>
</t:form>
<div id="result"></div>
<h4>last update 5</h4>
<c:forEach var="s" items="${snippets}">
	<li>${f:dateFormat(s.updated, 'yyyy-MM-dd HH:mm:ss')}&nbsp;<a href="${contextPath}/${language.path}/${s.id}">${s.title}</a> <a href="javascript:openSnippetDetail(${s.id});">[open]</a><span id="detail_${s.id}"></span></li>
</c:forEach>
</div>
<script type="text/javascript">
function openSnippetDetail(id) {
  $.getJSON("${contextPath}/${langPath}/" + id + "/json", null, function(data){
    $("<pre class=\"prettyprint\"/>").text(data.content).appendTo("#detail_" + id);
    prettyPrint();
  });
}
function search() {
  var query = $("#search_query").val();
  $.getJSON("${contextPath}/${langPath}/search/" + query, {}, function(snippets){
  	var div = $("<div></div>");
    $.each(snippets, function(i,snippet){
      var li = $("<li/>");
      li.appendTo(div);
      $("<a/>").attr("href", "${contextPath}/${langPath}/" + snippet.id).text(snippet.title).appendTo(li);
      var code = "";
      var count = 0;
      $.each(snippet.content.split("\n"), function(j, line) {
        if (line.indexOf(query) != -1) {
          code += line + "\n";
          count++;
          if (count >= 3) {
          	return false;
          }
        }
      });
      $("<pre class=\"prettyprint\"/>").text(code).appendTo(li);
    });
    $("#result").html(div);
    prettyPrint();
  });
}

$(function(){
  var lastQuery = $("#search_query").val();
  $("#search_query").keyup(function(){
    var currentQuery = $("#search_query").val();
    if (lastQuery != currentQuery) {
      search();
      lastQuery = currentQuery;
    }
  });
});
</script>
</body>
</html>
