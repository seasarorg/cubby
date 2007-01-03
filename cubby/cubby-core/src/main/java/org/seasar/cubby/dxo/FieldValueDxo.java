package org.seasar.cubby.dxo;

import org.seasar.cubby.convert.ValueConverter;
import org.seasar.extension.dxo.annotation.DatePattern;

/**
 * フォームオブジェクト->文字列の変換を行います。
 * @author agata
 * @since 1.0
 */
@DatePattern("yyyy-MM-dd")
public interface FieldValueDxo extends ValueConverter {
	String convert(Object src);
}
