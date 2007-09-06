package org.seasar.cubby.convention;

import org.seasar.cubby.controller.ActionDef;

public interface CubbyConvention {

	ActionDef fromPathToActionDef(String path);

}