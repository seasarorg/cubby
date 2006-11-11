package org.seasar.cubby.examples.todo.dto;

import org.seasar.cubby.validator.LabelKey;
import org.seasar.cubby.validator.Validatable;
import org.seasar.cubby.validator.Validators;
import org.seasar.cubby.validator.validators.Required;


@LabelKey("login.")
public class LoginDto implements Validatable {
	
	public static Validators VALIDATORS = new Validators();
	static {
		VALIDATORS.add("userId", new Required());
		VALIDATORS.add("password", new Required());
	}	
	public Validators getValidators() {
		return VALIDATORS;
	}
	
	public String userId;
	public String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}