package org.seasar.cubby.internal.util;

public class HtmlUtils {

	/**
	 * 指定された文字列をHTMLとしてエスケープします。
	 * <p>
	 * <table>
	 * <thead>
	 * <tr>
	 * <th>変換前</th>
	 * <th>変換後</th>
	 * </tr>
	 * </thead> <tbody>
	 * <tr>
	 * <td>&amp;</td>
	 * <td>&amp;amp;</td>
	 * </tr>
	 * <tr>
	 * <td>&lt;</td>
	 * <td>&amp;lt;</td>
	 * </tr>
	 * <tr>
	 * <td>&gt;</td>
	 * <td>&amp;gt;</td>
	 * </tr>
	 * <tr>
	 * <td>&quot;</td>
	 * <td>&amp;quot;</td>
	 * </tr>
	 * <tr>
	 * <td>&#39</td>
	 * <td>&amp;#39</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * </p>
	 * 
	 * @param str
	 * @return エスケープされた文字列
	 */
	public static String escapeHtml(final Object str) {
		if (str == null) {
			return "";
		}
		String text;
		if (str instanceof String) {
			text = (String) str;
		} else {
			text = str.toString();
		}
		text = StringUtils.replace(text, "&", "&amp;");
		text = StringUtils.replace(text, "<", "&lt;");
		text = StringUtils.replace(text, ">", "&gt;");
		text = StringUtils.replace(text, "\"", "&quot;");
		text = StringUtils.replace(text, "'", "&#39;");
		return text;
	}

}
