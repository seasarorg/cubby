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

	@QueryName("findPublic")
	List<Entry> findPublic(
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("getPublicCount")
	long getPublicCount();

	@QueryName("findByAccount")
	List<Entry> findByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("getCountByAccount")
	long getCountByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount);

	@QueryName("findFriendsByAccount")
	List<Entry> findFriendsByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("getFriendsCountByAccount")
	long getFriendsCountByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount);

	@QueryName("findFavoritesByAccount")
	List<Entry> findFavoritesByAccount(
			@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("getFavoritesCountByAccount")
	long getFavoritesCountByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount);

	@QueryName("findRepliesByAccount")
	List<Entry> findRepliesByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount,
			@FirstResult @ParameterName("firstResult") int firstResult,
			@MaxResults @ParameterName("maxResults") int maxResults);

	@QueryName("getRepliesCountByAccount")
	long getRepliesCountByAccount(@ParameterName("account") Account account,
			@ParameterName("loginAccount") Account loginAccount);

}