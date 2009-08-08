package org.seasar.cubby.cubbitter.service;

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
		return entryDao.findPublic(firstResult, maxResults);
	}

	public long getPublicCount() {
		return entryDao.getPublicCount();
	}

	public List<Entry> findByAccount(Account account, Account loginAccount,
			int firstResult, int maxResults) {
		return entryDao.findByAccount(account, loginAccount, firstResult,
				maxResults);
	}

	public long getCountByAccount(Account account, Account loginAccount) {
		return entryDao.getCountByAccount(account, loginAccount);
	}

	public List<Entry> findFriendsByAccount(Account loginAccount,
			Account account, int firstResult, int maxResults) {
		if (account == null) {
			return Collections.emptyList();
		}
		return entryDao.findFriendsByAccount(account, loginAccount,
				firstResult, maxResults);
	}

	public long getFriendsCountByAccount(Account loginAccount, Account account) {
		if (account == null) {
			return 0L;
		}
		return entryDao.getFriendsCountByAccount(account, loginAccount);
	}

	public List<Entry> findFavoritesByAccount(Account account,
			Account loginAccount, int firstResult, int maxResults) {
		return entryDao.findFavoritesByAccount(account, loginAccount,
				firstResult, maxResults);
	}

	public long getFavoritesCountByAccount(Account account, Account loginAccount) {
		return entryDao.getFavoritesCountByAccount(account, loginAccount);
	}

	public List<Entry> findRepliesByAccount(Account account,
			Account loginAccount, int firstResult, int maxResults) {
		return entryDao.findRepliesByAccount(account, loginAccount,
				firstResult, maxResults);
	}

	public long getRepliesCountByAccount(Account account, Account loginAccount) {
		return entryDao.getRepliesCountByAccount(account, loginAccount);
	}

}
