package org.seasar.cubby.unit.mock;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface MockHttpServletResponse extends HttpServletResponse {

    /**
     * {@link Cookie}の配列を返します。
     * 
     * @return {@link Cookie}の配列
     */
    Cookie[] getCookies();

    /**
     * ステータスを返します。
     * 
     * @return ステータス
     */
    int getStatus();

    /**
     * メッセージを返します。
     * 
     * @return メッセージ
     */
    String getMessage();

    /**
     * ヘッダの{@link Enumeration}を返します。
     * 
     * @param name
     * @return ヘッダの{@link Enumeration}
     */
    Enumeration getHeaders(String name);

    /**
     * ヘッダを返します。
     * 
     * @param name
     * @return ヘッダ
     */
    String getHeader(String name);

    /**
     * ヘッダ名の{@link Enumeration}を返します。
     * 
     * @return ヘッダ名の{@link Enumeration}
     */
    Enumeration getHeaderNames();

    /**
     * ヘッダを<code>int</code>で返します。
     * 
     * @param name
     * @return ヘッダのintの値
     */
    int getIntHeader(String name);

    void setCharacterEncoding(String characterEncoding);

    /**
     * コンテンツの長さを返します。
     * 
     * @return コンテンツの長さ
     */
    int getContentLength();

    String getContentType();

    /**
     * <code>response</code>を文字列として返します。
     * 
     * @return <code>response</code>の文字列表現
     */
    String getResponseString();

    /**
     * <code>response</code>をバイトの配列で返します。
     * 
     * @return <code>response</code>のバイト配列表現
     */
    byte[] getResponseBytes();	
	
}
