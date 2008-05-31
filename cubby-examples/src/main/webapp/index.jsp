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
<dd>ドキュメントの2分間チュートリアルと同じ内容です。<br/>入力値の受け取りと表示を行います。</dd>
<dt><a href="todo/">Todoサンプルアプリケーション</a></dt>
<dd>ドキュメントの2分間チュートリアルと同じ内容です。<br/>ログイン、一覧、追加、編集、削除など基本的なアプリケーションのサンプルです。</dd>
<dt><a href="components/">コンポーネント一覧</a></dt>
<dd>Cubbyで用意されたJSPカスタムタグ、JSPファンクションの一覧です。</dd>
<dt><a href="mayaa/components">コンポーネント一覧(Mayaa版)</a></dt>
<dd>上記コンポーネント一覧をMayaaで書き換えたバージョンです。</dd>
<dt><a href="components/array">配列のActionへのバインディング</a></dt>
<dd>同じパラメータ名の入力値をActionの配列のフィールドにバインディングするサンプルです。</dd>
<dt><a href="dispatch/">複数ボタンのフォーム(&#64;OnSubmit)</a>(v1.1.0以降)</dt>
<dd>押されたSbumitボタンによって、実行するアクションメソッドを切り替える@Submitの利用サンプルです。</dd>
<dt><a href="link/">クラス名＋メソッド名でのリンク生成</a>(v1.1.0以降)</dt>
<dd>アクションメソッドへのリンク文字列を生成するlinkカスタムタグの利用サンプルです。</dd>
<dt><a href="fileupload/">ファイルアップロード</a></dt>
<dd>ファイルアップロードのサンプルです。org.apache.commons.fileupload.FileItemにバインディングします。<br/>ファイル名などファイルアップロードの付加情報が取得可能です。</dd>
<dt><a href="fileupload2/">ファイルアップロード (入力ストリーム)</a></dt>
<dd>ファイルアップロードのサンプルです。java.io.InputStreamにバインディングします。</dd>
<dt><a href="fileupload3/">ファイルアップロード (バイト配列)</a></dt>
<dd>ファイルアップロードのサンプルです。バイト配列にバインディングします。</dd>
<dt><a href="image/">画像ダウンロード</a></dt>
<dd>画像ファイルなどバイナリ形式のレスポンスを返す場合のサンプルです。</dd>
<dt><a href="import/">他のページのインポート</a></dt>
<dd>JSTLのc:importを使用した他のページのインポートサンプルです。</dd>
<dt><a href="token/">2重サブミット防止（token）</a></dt>
<dd>tokenタグを使用した2重サブミット防止のサンプルです。</dd>
<dt><a href="feed/">Feed配信</a></dt>
<dd>Feedの配信サンプルです。Feedライブラリとして<a href="http://incubator.apache.org/abdera/">Apache Abdera</a>を使用します。</dd>
<dt><a href="ajax/">Ajax(JSON)</a></dt>
<dd>CubbyでJSONによるAjaxのサンプルです。</dd>
</dl>
<!--
	<li><a href="whiteboard/">ホワイトボード</a></li>
-->
</body>
</html>
