package org.seasar.cubby.cubbitter.dao;

import java.util.List;

import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.framework.beans.annotation.ParameterName;
import org.seasar.kuina.dao.FirstResult;
import org.seasar.kuina.dao.GenericDao;
import org.seasar.kuina.dao.MaxResults;
import org.seasar.kuina.dao.QueryName;

public interface EntryDao extends GenericDao<Entry, Long> {

	@QueryName("Entry.findByOpen")
	List<Entry> findByOpen(@ParameterName("open") boolean open,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("Entry.getCountByOpen")
	long getCountByOpen(@ParameterName("open") boolean open);

	@QueryName("Entry.findByAccounts")
	List<Entry> findByAccounts(
			@ParameterName("accounts") List<Account> accounts,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("Entry.getCountByAccounts")
	long getCountByAccounts(@ParameterName("accounts") List<Account> accounts);

	@QueryName("Entry.findByAccount")
	List<Entry> findByAccount(@ParameterName("account") Account account,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("Entry.getCountByAccount")
	long getCountByAccount(@ParameterName("account") Account account);

	@QueryName("Entry.findFavoritesByAccount")
	List<Entry> findFavoritesByAccount(
			@ParameterName("account") Account account,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("Entry.getFavoritesCountByAccount")
	long getFavoritesCountByAccount(@ParameterName("account") Account account);

	@QueryName("Entry.findRepliesByAccount")
	List<Entry> findRepliesByAccount(@ParameterName("account") Account account,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("Entry.getRepliesCountByAccount")
	long getRepliesCountByAccount(@ParameterName("account") Account account);

}
