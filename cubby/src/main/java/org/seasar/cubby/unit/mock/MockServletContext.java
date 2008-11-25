package org.seasar.cubby.unit.mock;

import java.util.Map;

import javax.servlet.ServletContext;


public interface MockServletContext extends ServletContext {
	
    /**
     * <code>mimeType</code>を追加します。
     * 
     * @param file
     * @param type
     */
    void addMimeType(String file, String type);

    /**
     * 初期化パラメータを設定します。
     * 
     * @param name
     * @param value
     */
    void setInitParameter(String name, String value);

    /**
     * リクエストを作成します。
     * 
     * @param path
     * @return {@link MockHttpServletRequest}
     */
    MockHttpServletRequest createRequest(String path);

    /**
     * サーブレットコンテキスト名を設定します。
     * 
     * @param servletContextName
     */
    void setServletContextName(String servletContextName);

    /**
     * 初期化パラメータの{@link Map}を返します。
     * 
     * @return
     */
    Map getInitParameterMap();	

}
