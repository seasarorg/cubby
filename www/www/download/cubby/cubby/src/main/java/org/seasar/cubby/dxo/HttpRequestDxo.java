package org.seasar.cubby.dxo;

import java.util.Map;

public interface HttpRequestDxo {

	void convert(Map<String, ?> src, Object dest);

	void convert(Object src, Map<String, String> dest);

}
