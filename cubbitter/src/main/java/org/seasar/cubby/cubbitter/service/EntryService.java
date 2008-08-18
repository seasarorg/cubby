package org.seasar.cubby.cubbitter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.seasar.cubby.cubbitter.dao.EntryDao;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.entity.Entry;

public class EntryService {

	public EntryDao entryDao;

	public List<Entry> getFollowingEntries(Account account) {
		if (account == null) {
			return Collections.emptyList();
		}
		List<Account> followings = mergeFollowingAccounts(account);
		List<Entry> entries = entryDao.findByAccounts(followings);
		return entries;
	}

	private List<Account> mergeFollowingAccounts(Account account) {
		List<Account> entryAccounts = new ArrayList<Account>();
		entryAccounts.add(account);
		entryAccounts.addAll(account.getFollowings());
		return entryAccounts;
	}

	public Entry find(Long id) {
		try {
			return entryDao.find(id);
		} catch (NoResultException e) {
			return null;
		}
	}

	public void persist(Entry entry) {
		entryDao.persist(entry);
	}

	public void remove(Entry entry) {
		entryDao.remove(entry);
	}

	public List<Entry> getPublicEntries() {
		return entryDao.findByOpen(true);
	}

}
