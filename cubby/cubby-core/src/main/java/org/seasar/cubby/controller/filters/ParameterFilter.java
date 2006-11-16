package org.seasar.cubby.controller.filters;

import java.util.Map;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.convert.Populater;

public class ParameterFilter extends AroundFilter {

	private final Populater populater;
	
	public ParameterFilter(Populater populater) {
		this.populater = populater;
	}
	
	@Override
	protected void doBeforeFilter(ActionContext action) {
		setupForm(action);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private void setupForm(ActionContext action) {
		Map<String, Object> params = action.getController().getParams().getOriginalParameter();
		Object formBean = action.getFormBean();
		if (formBean != null) {
			populater.populate(formBean, params);
		}
	}
}
