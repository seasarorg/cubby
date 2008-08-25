package org.seasar.cubby.cubbitter.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.TxBehavior;
import org.seasar.framework.unit.annotation.TxBehaviorType;

@RunWith(Seasar2.class)
@TxBehavior(TxBehaviorType.ROLLBACK)
public class AccountDaoTest {

	AccountDao accountDao;

	EntryDao entryDao;

	EntityManager entityManager;

	@Test
	public void find() {
		Account account = accountDao.findByName("account2");

		Account p = account.getFollowings().iterator().next();
		System.out.println(p);
		List<Account> aa = new ArrayList<Account>();
		aa.add(account);
		aa.addAll(account.getFollowings());
		List<Entry> com = entryDao.findByAccounts(aa, 0, 999);
		System.out.println(com);
		for (Entry c : com) {
			Set<Entry> fav = c.getAccount().getFavorites();
			System.out.println(fav);
		}
	}

	@Test
	public void relation() {
		Account account1 = new Account();
		account1.setName("account1");
		account1.setFullName("account 1");
		account1.setPassword("password");
		accountDao.persist(account1);

		Entry entry1 = new Entry();
		entry1.setText("comment1");
		entry1.setAccount(account1);
		entry1.setPost(new Date());
		entryDao.persist(entry1);

		Account account2 = new Account();
		account2.setName("account2");
		account2.setFullName("account 2");
		account2.setPassword("password");
		Set<Account> followingAccounts = new LinkedHashSet<Account>();
		followingAccounts.add(account1);
		account2.setFollowings(followingAccounts);
		accountDao.persist(account2);

		Set<Entry> favoriteEntries = new LinkedHashSet<Entry>();
		favoriteEntries.add(entry1);
		account2.setFavorites(favoriteEntries);

		Entry entry2 = new Entry();
		entry2.setText("comment2");
		entry2.setAccount(account2);
		entry2.setPost(new Date());
		entryDao.persist(entry2);

		Account selectedAccount1 = accountDao.findByName("account1");
		System.out.println(selectedAccount1);

		try {
			Account selectedAccount2 = accountDao.findByName("none");
			System.out.println(selectedAccount2);
		} catch (NoResultException e) {
			// 
		}

	}

}
