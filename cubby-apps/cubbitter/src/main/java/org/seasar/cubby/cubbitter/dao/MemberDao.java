package org.seasar.cubby.cubbitter.dao;

/**
 * 個人情報用Daoクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.cubbitter.dto.MemberPagerConditionDto;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.NoPersistentProperty;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

@S2Dao(bean = Member.class)
public interface MemberDao {
	/**
	 * @param memberId
	 * @return 個人情報
	 */
	@Query("member_id = ?")
	public Member getMemberById(int memberId);

	/**
	 * @param memberName
	 * @return 個人情報
	 */
	@Query("member_name = ?")
	public Member getMemberByName(String memberName);

	/**
	 * @param dto 検索条件
	 * @return 検索結果メンバーのリスト
	 */
	@Arguments("dto")
	public List<Map<String, Object>> searchMemberList(
			MemberPagerConditionDto dto);

	/**
	 * 個人の追加
	 * @param member 個人情報
	 * @return 更新レコード数
	 */
	public int insertMember(Member member);

	/**
	 * 個人基本情報の更新（パスワード以外）
	 * @param member
	 * @return
	 */
	@NoPersistentProperty("password")
	public int updateMember(Member member);

	/**
	 * パスワードの更新
	 * @param password パスワード
	 * @param memberId 個人ID
	 * @return 更新レコード数
	 */
	@Sql("UPDATE member SET password = ? WHERE member_id = ?")
	public int updatePassword(String password, int memberId);

	/**
	 * 個人の削除
	 * @param memberId 個人ID
	 * @return 削除レコード数
	 */
	@Arguments("memberId")
	public int deleteMemberById(int memberId);

	/**
	 * アイコンの更新（JPEG）
	 * @param smallPicture 24x24
	 * @param mediumPicture 48x48
	 * @param largePicture 73x73
	 * @param memberId 個人ID
	 * @return 更新レコード数
	 */
	@Sql("UPDATE member SET small_picture = ?, medium_picture = ?, large_picture = ? WHERE member_id = ?")
	public int updatePictures(byte smallPicture[], byte mediumPicture[],
			byte largePicture[], int memberId);

	/**
	 * @param memberId 個人ID
	 * @return 小サイズアイコンデータ
	 */
	@Sql("SELECT small_picture FROM member WHERE member_id = ?")
	public byte[] getSmallPicture(int memberId);

	/**
	 * @param memberId 個人ID
	 * @return 中サイズアイコンデータ
	 */
	@Sql("SELECT medium_picture FROM member WHERE member_id = ?")
	public byte[] getMediumPicture(int memberId);

	/**
	 * @param memberId 個人ID
	 * @return 大サイズアイコンデータ
	 */
	@Sql("SELECT large_picture FROM member WHERE member_id = ?")
	public byte[] getLargePicture(int memberId);
}
