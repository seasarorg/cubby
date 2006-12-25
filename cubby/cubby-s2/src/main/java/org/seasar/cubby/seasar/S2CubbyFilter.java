package org.seasar.cubby.seasar;

import org.seasar.cubby.controller.AbstractCubbyFilter;
import org.seasar.cubby.convert.ValueConverter;
import org.seasar.cubby.util.CubbyHelperFunctions;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class S2CubbyFilter extends AbstractCubbyFilter {

	@Override
    protected void initialize() {
        synchronized (this) {
            if (!initiallize) {
                S2Container container = SingletonS2ContainerFactory.getContainer();
                container.injectDependency(this);
                actionFactory.initialize(config);
                ValueConverter valueConverter = (ValueConverter) container
					.getComponent(ValueConverter.class);
                CubbyHelperFunctions.setValueConverter(valueConverter);
            }
        }
    }
}
