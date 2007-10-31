package org.seasar.cubby.validator;

import java.util.List;

public interface ValidationRule {
	List<Validator> getValidators();
}
