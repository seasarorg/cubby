package org.seasar.cubby.examples.smartdeploy.dxo;

import org.seasar.cubby.examples.smartdeploy.action.TodoAction;
import org.seasar.cubby.examples.smartdeploy.entity.Todo;
import org.seasar.extension.dxo.annotation.DatePattern;

@DatePattern("yyyy-MM-dd")
public interface TodoDxo {

	Todo convert(TodoAction src);

	void convert(Todo src, TodoAction dest);

}
