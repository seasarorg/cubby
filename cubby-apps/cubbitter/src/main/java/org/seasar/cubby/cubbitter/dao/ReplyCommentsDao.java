package org.seasar.cubby.cubbitter.dao;

/**
 * 返信コメント用Daoクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.dao.annotation.tiger.Arguments;

public interface ReplyCommentsDao {

	/**
	 * @param dto ページング条件
	 * @param memberId 利用者ID
	 * @return memberIdに返信されたコメントリスト
	 */	@Arguments({"dto", "memberId"})
	public List<Map<String, Object>> getReplyComments(MyPagerConditionDto dto, int memberId);

}
