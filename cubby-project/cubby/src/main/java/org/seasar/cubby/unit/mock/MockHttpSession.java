package org.seasar.cubby.unit.mock;

import javax.servlet.http.HttpSession;

public interface MockHttpSession extends HttpSession {
    /**
     * {@link MockHttpSession#invalidate()}が呼ばれたか確認します。
     * 
     * @return 呼ばれた場合：true、呼ばれていない場合：false
     */
    boolean isValid();

    /**
     * {@link MockHttpSession#invalidate()}が呼ばれたときに、内部的に、このメソッドを呼びます。
     * 
     * @param valid
     *            {@link MockHttpSession#invalidate()}が呼ばれたときに、true
     */
    void setValid(boolean valid);

    /**
     * セッションにアクセスします。
     */
    void access();
}
