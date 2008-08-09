<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Cubby Examples</title>
  <meta http-equiv="Content-Style-Type" content="text/css" />
  <meta http-equiv="Content-Script-Type" content="text/JavaScript" />
  <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" media="screen,projection" charset="utf-8" />
<style type="text/css">
<!--
dl { 
	padding-left: 2em; 
}
dt { 
	font-size: 1.4em;
	margin-top: 1em; 
}
dd { 
	margin-left: 1em; 
}
-->
</style>
</head>
<body>
<h1>Cubby Exmaples</h1>
<dl>
<dt><a href="hello/">Hello World</a></dt>
<dd>
ドキュメントの2分間チュートリアルと同じ内容です。<br/>入力値の受け取りと表示を行います。
(<a href="sourceviewer/?title=Hello%20World
&dirs=hello
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fhello">ソース表示</a>)
</dd>
<dt><a href="todo/">Todoサンプルアプリケーション</a></dt>
<dd>
ドキュメントの2分間チュートリアルと同じ内容です。<br/>ログイン、一覧、追加、編集、削除など基本的なアプリケーションのサンプルです。
(<a href="sourceviewer/?title=Todo
&dirs=WEB-INF%2Fsrc%2Fmain%2Fresources%2Fddl.sql
&dirs=todo
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Ftodo">ソース表示</a>)
</dd>
<dt><a href="components/">コンポーネント一覧</a></dt>
<dd>
Cubbyで用意されたJSPカスタムタグ、JSPファンクションの一覧です。
(<a href="sourceviewer/?title=Components
&dirs=components%2Fcomponents.jsp
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FComponentsAction.java
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FColor.java
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FFormDto.java
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FHobby.java
">ソース表示</a>)
</dd>
<dt><a href="mayaa/components">コンポーネント一覧(Mayaa版)</a></dt>
<dd>
上記コンポーネント一覧をMayaaで書き換えたバージョンです。
(<a href="sourceviewer/?title=Components for Mayaa
&dirs=components%2Fmayaa
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FMayaaComponentsAction.java
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FColor.java
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FFormDto.java
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FHobby.java
">ソース表示</a>)
</dd>
<dt><a href="components/array">配列のActionへのバインディング</a></dt>
<dd>
同じパラメータ名の入力値をActionの配列のフィールドにバインディングするサンプルです。
(<a href="sourceviewer/?title=Array
&dirs=components%2Farray.jsp
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fcomponents%2FArrayAction.java
">ソース表示</a>)
</dd>
<dt><a href="dispatch/">複数ボタンのフォーム(&#64;OnSubmit)</a>(v1.1.0以降)</dt>
<dd>
押されたSbumitボタンによって、実行するアクションメソッドを切り替える@OnSubmitの利用サンプルです。
(<a href="sourceviewer/?title=@OnSubmit
&dirs=dispatch
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fdispatch
">ソース表示</a>)
</dd>
<dt><a href="link/">クラス名＋メソッド名でのリンク生成</a>(v1.1.0以降)</dt>
<dd>
アクションメソッドへのリンク文字列を生成するlinkカスタムタグの利用サンプルです。
(<a href="sourceviewer/?title=@OnSubmit
&dirs=link
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Flink
">ソース表示</a>)
</dd>
<dt><a href="fileupload/">ファイルアップロード</a></dt>
<dd>
ファイルアップロードのサンプルです。org.apache.commons.fileupload.FileItemにバインディングします。<br/>ファイル名などファイルアップロードの付加情報が取得可能です。
(<a href="sourceviewer/?title=FileUpload1
&dirs=fileupload
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Ffileupload%2FFileUploadAction.java
">ソース表示</a>)
">ソース表示</a>)
</dd>
<dt><a href="fileupload2/">ファイルアップロード (入力ストリーム)</a></dt>
<dd>
ファイルアップロードのサンプルです。java.io.InputStreamにバインディングします。
(<a href="sourceviewer/?title=FileUpload2
&dirs=fileupload2
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Ffileupload%2FInputStreamUploadAction.java
">ソース表示</a>)
</dd>
<dt><a href="fileupload3/">ファイルアップロード (バイト配列)</a></dt>
<dd>
ファイルアップロードのサンプルです。バイト配列にバインディングします。
(<a href="sourceviewer/?title=FileUpload3
&dirs=fileupload3
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Ffileupload%2FByteArrayUploadAction.java
">ソース表示</a>)
</dd>
<dt><a href="image/">画像ダウンロード</a></dt>
<dd>
画像ファイルなどバイナリ形式のレスポンスを返す場合のサンプルです。
(<a href="sourceviewer/?title=Image
&dirs=image
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fimage
">ソース表示</a>)
</dd>
<dt><a href="import/">他のページのインポート</a></dt>
<dd>
JSTLのc:importを使用した他のページのインポートサンプルです。
(<a href="sourceviewer/?title=Import
&dirs=import
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2FimportTag
">ソース表示</a>)
</dd>
<dt><a href="token/">2重サブミット防止（token）</a></dt>
<dd>
tokenタグを使用した2重サブミット防止のサンプルです。
(<a href="sourceviewer/?title=Token
&dirs=token
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Ftoken
">ソース表示</a>)
</dd>
<dt><a href="feed/">Feed配信</a></dt>
<dd>
Feedの配信サンプルです。Feedライブラリとして<a href="http://incubator.apache.org/abdera/">Apache Abdera</a>を使用します。
(<a href="sourceviewer/?title=Feed
&dirs=feed
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Ffeed
">ソース表示</a>)
</dd>
<dt><a href="ajax/">Ajax(JSON)</a></dt>
<dd>
CubbyでJSONによるAjaxのサンプルです。
(<a href="sourceviewer/?title=Ajax
&dirs=ajax
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fajax
">ソース表示</a>)
</dd>
<dt><a href="sourceviewer/?title=Source%20Viewer&dirs=sourceviewer&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fsourceviewer">ソースビューア</a></dt>
<dd>
このサンプルで使用されているソースビューア自身です。テキスト形式のAjaxレスポンスを使用しています。
(<a href="sourceviewer/?title=Source%20Viewer
&dirs=sourceviewer
&dirs=WEB-INF%2Fsrc%2Fmain%2Fjava%2Forg%2Fseasar%2Fcubby%2Fexamples%2Fother%2Fweb%2Fsourceviewer
">ソース表示</a>)
</dd>
<dt><a href="cubby-admin">Cubby Adminツール</a></dt>
<dd>
Cubby Adminツールです。アプリケーションのルーティング情報などをテストするツールなどがあります。web.xmlにCubbyAdminServletを登録して利用します。
(<a href="sourceviewer/?title=Cubby%20Admin%20Tool
&dirs=WEB-INF%2Fweb.xml
">ソース表示</a>)
</dd>
</dl>


<!--
	<li><a href="whiteboard/">ホワイトボード</a></li>
-->
</body>
</html>
