package org.seasar.cubby.cubbitter.action;

/** 
 * デフォルトメニューを表示する親クラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.action.Action;
import org.seasar.cubby.cubbitter.dao.FavoriteCommentsDao;
import org.seasar.cubby.cubbitter.dao.FollowingMembersDao;
import org.seasar.cubby.cubbitter.dao.ReplyCommentsDao;
import org.seasar.cubby.cubbitter.entity.Member;

public class DefaultMenuAction extends Action {

	// ----------------------------------------------[DI Filed]

	public Map<String, Object> sessionScope;

	public FollowingMembersDao followingMembersDao;
	public FavoriteCommentsDao favoriteCommentDao;
	public ReplyCommentsDao replyCommentDao;

	// ----------------------------------------------[Attribute]
	
	public List<Map<String, Object>> followingList;
	public int followRequestedCount;
	
	// ----------------------------------------------[Protected Filed]

	protected int userMemberId;

	// ----------------------------------------------[Override]

	@Override
	public void initialize() {
		if (sessionScope.containsKey("user")) {
			userMemberId = ((Member) sessionScope.get("user")).getMemberId();
		}else{
			userMemberId = -1;
		}
	}

	// ----------------------------------------------[Method]
	
	protected void setMainMenuData() {
		followingList = followingMembersDao
				.getFollowingMemberList(userMemberId);
		followRequestedCount = followingMembersDao
				.getRequestedCount(userMemberId);
	}



}
