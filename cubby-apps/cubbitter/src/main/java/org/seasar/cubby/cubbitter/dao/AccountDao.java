package org.seasar.cubby.cubbitter.dao;

import java.util.List;

import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.framework.beans.annotation.ParameterName;
import org.seasar.kuina.dao.FirstResult;
import org.seasar.kuina.dao.GenericDao;
import org.seasar.kuina.dao.MaxResults;
import org.seasar.kuina.dao.QueryName;

public interface AccountDao extends GenericDao<Account, Long> {

	Account findByName(@ParameterName("name") String name);

	Account findByNameAndPassword(@ParameterName("name") String name,
			@ParameterName("password") String password);

	@QueryName("Account.findByKeyword")
	List<Account> findByKeyword(@ParameterName("keyword") String keyword,
			@FirstResult @ParameterName("firstResult") Integer firstResult,
			@MaxResults @ParameterName("maxResults") Integer maxResults);

	@QueryName("Account.getCountByKeyword")
	long getCountByKeyword(@ParameterName("keyword") String string);

}
