package org.seasar.cubby.cubbitter.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.Base64Util;

public class BasicAuthenticationService {

	public AccountService accountService;

	public Account authenticate(HttpServletRequest request) throws AuthenticationException {
		String basicAuthData = request.getHeader("authorization");
		if (basicAuthData == null
				|| !basicAuthData.toUpperCase().startsWith("BASIC")) {
			throw new AuthenticationException();
		}
		String basicAuthBody = basicAuthData.substring(6);
		byte[] bytes = Base64Util.decode(basicAuthBody);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(bytes), "UTF-8"));

			StringBuilder buf = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				buf.append(line);
			}
			int index = buf.indexOf(":");
			if (index <= 0 && buf.length() <= index) {
				throw new AuthenticationException();
			}
			String user = buf.substring(0, index);
			String password = buf.substring(index + 1);
			Account loginAccount = accountService.findByNameAndPassword(user, password);
			if (loginAccount == null) {
				throw new AuthenticationException();
			}
			accountService.login(loginAccount);
			return loginAccount;
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

}
