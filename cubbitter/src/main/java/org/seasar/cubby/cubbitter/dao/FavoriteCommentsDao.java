package org.seasar.cubby.cubbitter.dao;

/**
 * お気に入りコメント用Daoクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Sql;

public interface FavoriteCommentsDao {

	/**
	 * @param dto ページング条件
	 * @param memberId 利用者個人ID
	 * @return お気に入りコメントリスト
	 */
	@Arguments({"dto", "memberId"})
	public List<Map<String, Object>> getFavoriteComments(MyPagerConditionDto dto, int memberId);
	
	/**
	 * @param memberId 個人ID
	 * @param commentId コメントID
	 * @return 引数の条件でお気に入りが存在するかどうか
	 */
	@Sql("SELECT COUNT(*) > 0 FROM favorite_comments WHERE member_id = ? AND comment_id = ?")
	public boolean isExist(int memberId, int commentId);

	/**
	 * お気に入りに登録
	 * @param memberId 個人ID
	 * @param commentId コメントID
	 * @return 追加レコード数
	 */
	@Sql("INSERT INTO favorite_comments(member_id, comment_id) VALUES(?, ?)")
	public int insert(int memberId, int commentId);
	
	/**
	 * お気に入りから削除
	 * @param memberId 個人ID
	 * @param commentId コメントID
	 * @return 削除レコード数
	 */
	@Sql("DELETE FROM favorite_comments WHERE member_id = ? AND comment_id = ?")
	public int delete(int memberId, int commentId);

}
