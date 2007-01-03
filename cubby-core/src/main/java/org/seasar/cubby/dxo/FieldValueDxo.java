package org.seasar.cubby.dxo;

import org.seasar.cubby.convert.ValueConverter;
import org.seasar.extension.dxo.annotation.DatePattern;

@DatePattern("yyyy-MM-dd")
public interface FieldValueDxo extends ValueConverter {
	String convert(Object src);
}
