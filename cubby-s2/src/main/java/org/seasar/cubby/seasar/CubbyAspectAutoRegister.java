package org.seasar.cubby.seasar;

import org.aopalliance.intercept.MethodInterceptor;
import org.seasar.framework.container.AspectDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.autoregister.AbstractComponentTargetAutoRegister;
import org.seasar.framework.container.factory.AspectDefFactory;

/**
 * 
 */
public class CubbyAspectAutoRegister extends AbstractComponentTargetAutoRegister {

    private MethodInterceptor interceptor;

    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    protected void register(ComponentDef componentDef) {
        AspectDef aspectDef = AspectDefFactory.createAspectDef(interceptor,
                new ActionPointCut());
        componentDef.addAspectDef(aspectDef);
    }
}