package org.seasar.cubby.wiki.service;

public class WikiService {
	public String render(String content) {
		if (content == null) {
			return "";
		}
		return content
			.replaceAll("&", "&amp;")          // &
			.replaceAll("<", "&lt;")           // <
			.replaceAll(">", "&gt;")           // >
			.replaceAll("\\n", "<br/>\n")           // 改行
			.replaceAll("([A-Z][a-z]+[A-Z][a-z]+)", // ページリンク
					"<a href=\"$1\">$1</a>")
			.replaceAll("(?m)^[*](.*)$", "<h2>$1</h2>") // ヘッダー
			.replaceAll("(?m)^-(.*)$", "<li>$1</li>");  // リスト
	}
}