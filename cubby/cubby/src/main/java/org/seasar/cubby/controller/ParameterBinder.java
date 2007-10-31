package org.seasar.cubby.controller;

import java.util.Map;

public interface ParameterBinder {

	void bindParamsToForm(Map<String, Object[]> src, Object dest);

	Map<String, String[]> bindFormToOutputValues(Object src);

}
