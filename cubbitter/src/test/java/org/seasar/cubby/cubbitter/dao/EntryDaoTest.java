package org.seasar.cubby.cubbitter.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.TxBehavior;
import org.seasar.framework.unit.annotation.TxBehaviorType;

@RunWith(Seasar2.class)
@TxBehavior(TxBehaviorType.ROLLBACK)
public class EntryDaoTest extends S2TestCase {

	EntryDao EntryDao;

	@Test
	public void findAll() {
		List<Entry> list = EntryDao.findAll();
		System.out.println(list);
	}

}
