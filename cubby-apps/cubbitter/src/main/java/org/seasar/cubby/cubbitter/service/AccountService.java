package org.seasar.cubby.cubbitter.service;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.seasar.cubby.cubbitter.dao.AccountDao;
import org.seasar.cubby.cubbitter.entity.Account;

public class AccountService {

	private static final int MAX_RESULTS = 20;

	private static final String LOGIN_ACCOUNT_ID = "loginAccountId";

	public AccountDao accountDao;

	public Map<String, Object> sessionScope;

	public Account login(String name, String password) throws AuthenticationException {
		try {
			Account account = accountDao.findByNameAndPassword(name, password);
			login(account);
			return account;
		} catch (NoResultException e) {
			throw new AuthenticationException(e);
		}
	}

	public void login(Account account) {
		sessionScope.put(LOGIN_ACCOUNT_ID, account.getId());
	}

	public Account getLoginAccount() {
		if (!sessionScope.containsKey(LOGIN_ACCOUNT_ID)) {
			return null;
		}
		Long accountId = (Long) sessionScope.get(LOGIN_ACCOUNT_ID);
		Account account = accountDao.find(accountId);
		if (account == null) {
			return null;
		}
		return accountDao.merge(account);
	}

	public boolean isLoginable(String name, String password) {
		if (name == null || password == null) {
			return false;
		}
		try {
			accountDao.findByNameAndPassword(name, password);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public boolean isDuplicate(String name) {
		if (name == null) {
			return false;
		}
		try {
			accountDao.findByName(name);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public Account find(final Long id) {
		try {
			return accountDao.find(id);
		} catch (NoResultException e) {
			return null;
		}
	}

	public Account findByName(final String name) {
		try {
			return accountDao.findByName(name);
		} catch (NoResultException e) {
			return null;
		}
	}

	public Account findByNameAndPassword(final String name, final String password) {
		try {
			return accountDao.findByNameAndPassword(name, password);
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Account> findByKeyword(final String keyword, int pageNo) {
		int firstResult = (pageNo - 1) * MAX_RESULTS;
		int maxResults = MAX_RESULTS;
		return accountDao.findByKeyword("%" + keyword + "%", firstResult,
				maxResults);
	}

	public void persist(Account account) {
		accountDao.persist(account);
	}

	public void remove(Account account) {
		accountDao.remove(account);
	}

}
