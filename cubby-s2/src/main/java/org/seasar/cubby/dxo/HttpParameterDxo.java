package org.seasar.cubby.dxo;

import java.util.Map;

import org.seasar.extension.dxo.annotation.DatePattern;

@DatePattern("yyyy-MM-dd")
public interface HttpParameterDxo {
    void convert(Map<String, ?> src, Object dest);
}
