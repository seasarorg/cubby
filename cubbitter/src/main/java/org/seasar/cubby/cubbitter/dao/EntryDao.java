package org.seasar.cubby.cubbitter.dao;

import java.util.List;

import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.framework.beans.annotation.ParameterName;
import org.seasar.kuina.dao.GenericDao;
import org.seasar.kuina.dao.QueryName;

public interface EntryDao extends GenericDao<Entry, Long> {

	@QueryName("Entry.findByAccounts")
	List<Entry> findByAccounts(@ParameterName("accounts") List<Account> accounts);

	List<Entry> findByOpen(@ParameterName("account$open") boolean open);

}
