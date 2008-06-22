package org.seasar.cubby.cubbitter.dto;

/**
 * メンバー検索のページングと検索条件
 */

public class MemberPagerConditionDto extends MyPagerConditionDto {
	private static final long serialVersionUID = -1784015360970261049L;

	private String keyword;
	private int userMemberId;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getUserMemberId() {
		return userMemberId;
	}

	public void setUserMemberId(int userMemberId) {
		this.userMemberId = userMemberId;
	}
}
