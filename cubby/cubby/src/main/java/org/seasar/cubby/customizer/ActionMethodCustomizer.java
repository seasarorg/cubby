/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.customizer;

import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.container.customizer.AspectCustomizer;
import org.seasar.framework.util.StringUtil;

/**
 * {@link org.seasar.framework.container.ComponentDef コンポーネント定義}に
 * {@link org.seasar.framework.container.AspectDef アスペクト定義}を
 * 登録するコンポーネントカスタマイザです。
 * <p>
 * カスタマイザには、ポイントカットとインターセプタを設定します。 インターセプタはコンポーネント名で指定し、複数のインターセプタ名を設定することができます。
 * インターセプタ名が複数設定された場合は、設定された順にアスペクト定義をコンポーネント定義に登録します。
 * 最初に設定された名前を持つインターセプタが、後に設定された名前を持つインターセプタよりも先に呼び出されることになります。
 * </p>
 * <p>
 * コンポーネントに適用するインターセプタのインスタンス属性が<code>singleton</code>以外の場合は、
 * {@link #setUseLookupAdapter(boolean) useLookupAdapter}プロパティを<code>true</code>に設定します。
 * これにより、コンポーネントのメソッドが呼び出される度に、コンテナからインターセプタのインスタンスをルックアップするようになります。
 * </p>
 * 
 * @author higa
 */
public class ActionMethodCustomizer extends AspectCustomizer {

    private String pointcut;

    /**
     * コンポーネント定義に登録するアスペクト定義のポイントカットを設定します。
     * 
     * @param pointcut
     *            ポイントカット
     */
    public void setPointcut(final String pointcut) {
    	super.setPointcut(pointcut);
        this.pointcut = pointcut;
    }

    /**
     * ポイントカットを作成して返します。
     * <p>
     * <code>pointcut</code>プロパティが指定されている場合は、その文字列からポイントカットを作成します。
     * <code>targetInterface</code>プロパティが指定されている場合は、そのインターフェースからポイントカットを作成します。
     * それ以外の場合は<code>null</code>を返します。
     * </p>
     * 
     * @return ポイントカット
     */
    protected Pointcut createPointcut() {
        if (!StringUtil.isEmpty(pointcut)) {
            return PointcutFactory.createPointcut(pointcut);
        }
        if (targetInterface != null) {
            return PointcutFactory.createPointcut(targetInterface);
        }
        return null;
    }

}
