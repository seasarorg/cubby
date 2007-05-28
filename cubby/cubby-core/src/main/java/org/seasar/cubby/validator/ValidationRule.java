package org.seasar.cubby.validator;

import java.util.Collection;

public interface ValidationRule {
	Collection<Validator> getValidators();
}
