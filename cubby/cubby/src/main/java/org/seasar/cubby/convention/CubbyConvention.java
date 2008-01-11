package org.seasar.cubby.convention;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.ActionDef;

public interface CubbyConvention {

	ActionDef fromPathToActionDef(HttpServletRequest request);

}