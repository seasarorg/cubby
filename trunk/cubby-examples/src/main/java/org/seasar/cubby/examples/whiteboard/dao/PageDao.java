package org.seasar.cubby.examples.whiteboard.dao;

import org.seasar.cubby.examples.whiteboard.entity.Page;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = Page.class)
public interface PageDao {

	int insert(Page page);

	@Arguments("id")
	Page selectById(Integer id);

}
