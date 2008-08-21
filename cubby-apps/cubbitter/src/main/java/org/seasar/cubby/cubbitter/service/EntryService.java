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

	public List<Entry> findPublic(int firstResult, int maxResults) {
		return entryDao.findByOpen(true, firstResult, maxResults);
	}

	public long getPublicCount() {
		return entryDao.getCountByOpen(true);
	}

	public List<Entry> findByAccount(Account account, int firstResult,
			int maxResults) {
		return entryDao.findByAccount(account, firstResult, maxResults);
	}

	public long getCountByAccount(Account account) {
		return entryDao.getCountByAccount(account);
	}

	public List<Entry> findFriendsByAccount(Account account, int firstResult,
			int maxResults) {
		if (account == null) {
			return Collections.emptyList();
		}
		List<Account> friends = getFriends(account);
		List<Entry> entries = entryDao.findByAccounts(friends, firstResult,
				maxResults);
		return entries;
	}

	public long getFriendsCountByAccount(Account account) {
		if (account == null) {
			return 0L;
		}
		List<Account> friends = getFriends(account);
		return entryDao.getCountByAccounts(friends);
	}

	private List<Account> getFriends(Account account) {
		List<Account> entryAccounts = new ArrayList<Account>();
		entryAccounts.add(account);
		entryAccounts.addAll(account.getFollowings());
		return entryAccounts;
	}

	public List<Entry> findFavoritesByAccount(Account account, int firstResult,
			int maxResults) {
		return entryDao
				.findFavoritesByAccount(account, firstResult, maxResults);
	}

	public long getFavoritesCountByAccount(Account account) {
		return entryDao.getFavoritesCountByAccount(account);
	}

	public List<Entry> findRepliesByAccount(Account account, int firstResult,
			int maxResults) {
		return entryDao.findRepliesByAccount(account, firstResult, maxResults);
	}

	public long getRepliesCountByAccount(Account account) {
		return entryDao.getRepliesCountByAccount(account);
	}

}
