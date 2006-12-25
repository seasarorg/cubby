package org.seasar.cubby.seasar.convert;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.seasar.extension.dxo.annotation.DxoConverter;

@Retention(RetentionPolicy.RUNTIME)
@DxoConverter("removeEmptyValueConverter")
public @interface RemoveEmptyValueConverter {
}
