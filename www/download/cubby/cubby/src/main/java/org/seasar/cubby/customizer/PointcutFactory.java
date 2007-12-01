package org.seasar.cubby.customizer;

import java.lang.reflect.Method;

import org.seasar.cubby.aop.ActionMethodPointcutImpl;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.util.StringUtil;

/**
 * {@link org.seasar.framework.aop.Pointcut ポイントカット}を構築するためのファクトリクラスです。
 * 
 * @author baba
 */
class PointcutFactory {

    /**
     * 指定されたポイントカットを表す文字列から、 {@link org.seasar.framework.aop.Pointcut ポイントカット}を構築して返します。
     * 
     * @param pointcutStr
     *            ポイントカットを表す文字列
     * @return ポイントカット
     */
    public static Pointcut createPointcut(final String pointcutStr) {
        if (!StringUtil.isEmpty(pointcutStr)) {
            String[] methodNames = StringUtil.split(pointcutStr, ", \n");
            return new ActionMethodPointcutImpl(methodNames);
        }
        return null;
    }

    /**
     * 指定された{@link Class クラス}から、
     * {@link org.seasar.framework.aop.Pointcut ポイントカット}を構築して返します。
     * 
     * @param clazz
     *            クラス
     * @return ポイントカット
     */
    public static Pointcut createPointcut(final Class<?> clazz) {
        return new ActionMethodPointcutImpl(clazz);
    }

    /**
     * 指定された{@link java.lang.reflect.Method メソッド}から、
     * {@link org.seasar.framework.aop.Pointcut ポイントカット}を構築して返します。
     * 
     * @param method
     *            メソッド
     * @return ポイントカット
     */
    public static Pointcut createPointcut(final Method method) {
        if (method != null) {
            return new ActionMethodPointcutImpl(method);
        }
        return null;
    }

}
