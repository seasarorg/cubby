package org.seasar.cubby.cubbitter.dao;

/**
 * コメント用Daoクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Sql;

public interface CommentDao {

	/**
	 * @param dto ページング条件
	 * @param userMemberId 利用者ID（未ログインの場合は-1）
	 * @return 公開コメントリスト
	 */
	@Arguments( { "dto", "userMemberId" })
	List<Map<String, Object>> getPublicCommentList(MyPagerConditionDto dto,
			int userMemberId);

	/**
	 * @param dto ページング条件
	 * @param memberId 取得する個人ID
	 * @param userMemberId 利用者ID（未ログインの場合は-1）
	 * @return 個人ごとのコメントリスト
	 */
	@Arguments( { "dto", "memberId", "userMemberId" })
	List<Map<String, Object>> getMemberCommentList(MyPagerConditionDto dto,
			int memberId, int userMemberId);

	/**
	 * @param dto ページング条件
	 * @param userMemberId 利用者ID
	 * @return Followメンバーのコメントリスト
	 */
	@Arguments( { "dto", "userMemberId" })
	List<Map<String, Object>> getFollowingMemberCommentList(
			MyPagerConditionDto dto, int userMemberId);

	/**
	 * @param commentId コメントID
	 * @param userMemberId 利用者ID（未ログインの場合は-1）
	 * @return コメント
	 */
	@Arguments( { "commentId", "userMemberId" })
	Map<String, Object> getComment(int commentId, int userMemberId);

	/**
	 * @param memberId 個人ID
	 * @return memberIdが投稿したコメントIDのリスト
	 */
	@Sql("SELECT comment_id FROM member_comments WHERE member_id = ?")
	List<Integer> getMemberCommentIdList(int memberId);

	/**
	 * @param commentId コメントID
	 * @return commentIdが存在するかどうか
	 */
	@Sql("SELECT COUNT(*) > 0 FROM comment WHERE comment_id = ?")
	public boolean isExist(int commentId);

	/**
	 * @param comment コメントの内容
	 * @param memberId 投稿者の個人ID
	 * @param replyMemberId リプライ先の個人ID（ない場合は-1）
	 * @return 更新レコード数
	 */
	@Arguments( { "comment", "memberId", "replyMemberId" })
	int insertComment(String comment, int memberId, int replyMemberId);

	/**
	 * @param commentId 削除するコメントID
	 * @return 削除レコード数
	 */
	@Arguments("commentId")
	int deleteComment(int commentId);

	/**
	 * @param memberId 削除する個人ID
	 * @param commentIdList 削除するコメントIDリスト
	 * @return 削除レコード数
	 */
	@Arguments( { "memberId", "commentIdList" })
	int deleteCommentList(int memberId, List<Integer> commentIdList);
}
