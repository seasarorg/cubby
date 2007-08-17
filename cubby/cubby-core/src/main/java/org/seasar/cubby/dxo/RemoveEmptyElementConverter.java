package org.seasar.cubby.dxo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.seasar.extension.dxo.annotation.DxoConverter;

@Retention(RetentionPolicy.RUNTIME)
@DxoConverter("removeEmptyElementConverter")
public @interface RemoveEmptyElementConverter {
}
