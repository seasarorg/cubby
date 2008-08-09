package org.seasar.cubby.cubbitter.dto;

/**
 * コメント一覧のページング条件 
 */

import org.seasar.dao.pager.DefaultPagerCondition;

public class MyPagerConditionDto extends DefaultPagerCondition {
	private static final long serialVersionUID = 4525082362721834940L;

	public void setPageNo(int pageNo) {
		setOffset((pageNo - 1) * getLimit());
	}
}
