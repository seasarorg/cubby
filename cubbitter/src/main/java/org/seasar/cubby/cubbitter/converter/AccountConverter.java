package org.seasar.cubby.cubbitter.converter;

import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.impl.AbstractConverter;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.service.AccountService;

public class AccountConverter extends AbstractConverter {

	public AccountService accountService;

	public Class<?> getObjectType() {
		return Account.class;
	}

	public Object convertToObject(Object value, Class<?> objectType,
			ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		String name = String.class.cast(value);
		Account account = accountService.findByName(name);
		return account;
	}

	public String convertToString(Object value, ConversionHelper helper) {
		return null;
	}

}
