package org.seasar.cubby.examples.todo.dxo;

import org.seasar.cubby.examples.todo.action.TodoAction;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.extension.dxo.annotation.DatePattern;

@DatePattern("yyyy-MM-dd")
public interface TodoDxo {

	Todo convert(TodoAction src);

	void convert(Todo src, TodoAction dest);

}
