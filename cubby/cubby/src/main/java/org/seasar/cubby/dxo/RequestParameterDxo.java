package org.seasar.cubby.dxo;

import java.util.Map;

public interface RequestParameterDxo {

	void convert(Map<String, Object> src, Object dest);

	void convert(Object src, Map<String, String[]> dest);

}
