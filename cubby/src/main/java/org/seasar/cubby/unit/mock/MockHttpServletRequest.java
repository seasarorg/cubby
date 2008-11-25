package org.seasar.cubby.unit.mock;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface MockHttpServletRequest extends HttpServletRequest {
    /**
     * パラメータを追加します。
     * 
     * @param name
     * @param value
     */
    void addParameter(String name, String value);

    /**
     * 配列のパラメータを追加します。
     * 
     * @param name
     * @param values
     */
    void addParameter(String name, String[] values);

    /**
     * {@link Cookie}を追加します。
     * 
     * @param cookie
     */
    void addCookie(Cookie cookie);

    /**
     * ヘッダを追加します。
     * 
     * @param name
     * @param value
     */
    void addHeader(String name, String value);

    /**
     * <code>authType</code>を設定します。
     * 
     * @param authType
     */
    void setAuthType(String authType);

    /**
     * 日付のヘッダーを追加します。
     * 
     * @param name
     * @param value
     */
    void addDateHeader(String name, long value);

    /**
     * <code>int</code>のヘッダを追加します。
     * 
     * @param name
     * @param value
     */
    void addIntHeader(String name, int value);

    /**
     * <code>pathInfo</code>を設定します。
     * 
     * @param pathInfo
     */
    void setPathInfo(String pathInfo);

    /**
     * <code>pathTranslated</code>を設定します。
     * 
     * @param pathTranslated
     */
    void setPathTranslated(String pathTranslated);

    /**
     * <code>queryString</code>を設定します。
     * 
     * @param queryString
     */
    void setQueryString(String queryString);

    /**
     * <code>contentLength</code>を設定します。
     * 
     * @param contentLength
     */
    void setContentLength(int contentLength);

    /**
     * <code>contentType</code>を設定します。
     * 
     * @param contentType
     */
    void setContentType(String contentType);

    /**
     * パラメータを設定します。
     * 
     * @param name
     * @param value
     */
    void setParameter(String name, String value);

    /**
     * 配列のパラメータを設定します。
     * 
     * @param name
     * @param values
     */
    void setParameter(String name, String[] values);

    /**
     * プロトコルを設定します。
     * 
     * @param protocol
     */
    void setProtocol(String protocol);

    /**
     * <code>scheme</code>を設定します。
     * 
     * @param scheme
     */
    void setScheme(String scheme);

    /**
     * サーバ名を設定します。
     * 
     * @param serverName
     */
    void setServerName(String serverName);

    /**
     * サーバの<code>port</code>を設定します。
     * 
     * @param serverPort
     */
    void setServerPort(int serverPort);

    /**
     * リモートアドレスを設定します。
     * 
     * @param remoteAddr
     */
    void setRemoteAddr(String remoteAddr);

    /**
     * リモートホストを設定します。
     * 
     * @param remoteHost
     */
    void setRemoteHost(String remoteHost);

    /**
     * {@link Locale}を設定します。
     * 
     * @param locale
     */
    void setLocale(Locale locale);

    /**
     * メソッドを設定します。
     * 
     * @param method
     */
    void setMethod(String method);

    /**
     * ローカルアドレスを設定します。
     * 
     * @param localAddr
     */
    void setLocalAddr(String localAddr);

    /**
     * ローカル名を設定します。
     * 
     * @param localName
     */
    void setLocalName(String localName);

    /**
     * ローカル<code>port</code>を設定します。
     * 
     * @param localPort
     */
    void setLocalPort(int localPort);

    /**
     * リモート<code>port</code>を設定します。
     * 
     * @param remotePort
     */
    void setRemotePort(int remotePort);	

}
