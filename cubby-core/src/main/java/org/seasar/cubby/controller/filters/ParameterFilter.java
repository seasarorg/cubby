package org.seasar.cubby.controller.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

	@SuppressWarnings("unchecked")
	private void setupForm(ActionContext action) {
		HttpServletRequest request = action.getRequest();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Map<String, Object> params = null;
		if (isMultipart) {
			params = action.getController().getParams();
		} else {
			params = request.getParameterMap();
		}

		Object formBean = action.getFormBean();
		if (formBean != null) {
			populater.populate(formBean, params);
		}
	}
}
