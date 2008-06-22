package org.seasar.cubby.cubbitter.dao;

/**
 * 登録メンバー用Daoクラス
 */

import java.util.List;
import java.util.Map;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Sql;

public interface FollowingMembersDao {

	/**
	 * @param memberId 利用者ID
	 * @param followingMemberId memberIdの登録メンバー
	 * @return 登録リクエスト中：true 登録中：false 登録なし：null
	 */
	@Arguments( { "memberId", "followingMemberId" })
	public Boolean isFollowingRequest(int memberId, int followingMemberId);

	/**
	 * @param memberId 個人ID
	 * @return memberIdの登録メンバーリスト
	 */
	@Arguments("memberId")
	public List<Map<String, Object>> getFollowingMemberList(int memberId);

	/**
	 * @param memberId 利用者ID
	 * @return memberIdが登録されているメンバーリスト
	 */
	@Arguments("memberId")
	public List<Map<String, Object>> getFollowerMemberList(int memberId);

	/**
	 * @param memberId 利用者ID
	 * @return memberIdへの登録リクエスト数
	 */
	@Sql("SELECT COUNT(*) FROM following_members WHERE following_member_id = ? AND request = true")
	public int getRequestedCount(int memberId);
	
	/**
	 * @param memberId 利用者ID
	 * @return memberIdへ登録リクエスト中のメンバーリスト
	 */
	@Arguments("memberId")
	public List<Map<String, Object>> getRequestedMemberList(int memberId);
	
	/**
	 * 登録メンバーに追加
	 * @param memberId 利用者ID
	 * @param followingMemberId 登録する個人ID
	 * @param isRequest リクエストかどうか
	 * @return 追加レコード数
	 */
	@Sql("INSERT INTO following_members(member_id, following_member_id, request) "
			+ "values (?, ?, ?)")
	public int insertFollowing(int memberId, int followingMemberId,
			boolean isRequest);

	/**
	 * 登録リクエストを承認
	 * @param memberId 利用者ID
	 * @param followingMemberId 承認する個人ID
	 * @return 更新レコード数
	 */
	@Sql("UPDATE following_members SET request = false "
			+ "WHERE member_id = ? AND following_member_id = ?")
	public int updateRequestAccept(int memberId, int followingMemberId);

	/**
	 * メンバー登録を削除
	 * @param memberId 利用者ID
	 * @param followingMemberId 削除する個人ID
	 * @return 削除レコード数
	 */
	@Sql("DELETE FROM following_members WHERE member_id = ? AND following_member_id = ?")
	public int deleteFollowing(int memberId, int followingMemberId);
}
