package org.seasar.cubby.dxo;

import java.util.Map;

import org.seasar.extension.dxo.annotation.DatePattern;

@DatePattern("yyyy-MM-dd")
public interface HttpRequestDxo {

	void convert(Map<String, ?> src, Object dest);

	void convert(Object src, Map<String, String> dest);

}
