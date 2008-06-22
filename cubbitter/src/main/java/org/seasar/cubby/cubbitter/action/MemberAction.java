package org.seasar.cubby.cubbitter.action;

/** 
 * 個人ごとのページ表示用Action 
 */

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dao.FollowingMembersDao;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.util.Messages;
import org.seasar.dao.pager.PagerViewHelper;

public class MemberAction extends Action {

	// ----------------------------------------------[DI Filed]
	
	public HttpServletResponse response;
	public Map<String, Object> sessionScope;

	public MemberDao memberDao;
	public CommentDao commentDao;
	public FollowingMembersDao followingMembersDao;
	public MyPagerConditionDto myPagerConditionDto;
	public Integer commentPagerLimit;

	// ----------------------------------------------[Attribute]
	
	public PagerViewHelper pagerViewHelper;
	public int memberId;
	public String memberName;
	public Member member;
	public List<Map<String, Object>> commentList;
	public List<Map<String, Object>> followingList;
	public int button = 0;
	public boolean visibleCommentList = false;
	public int pageNo;

	// ----------------------------------------------[Private Field]
	
	private int userMemberId;
	
	// ----------------------------------------------[Override]

	@Override
	public void initialize() {
		if (sessionScope.containsKey("user")) {
			userMemberId = ((Member) sessionScope.get("user")).getMemberId();
		} else {
			userMemberId = -1;
		}
	}

	@Override
	public void prerender() {
		pagerViewHelper = new PagerViewHelper(myPagerConditionDto);
	}
	
	// ----------------------------------------------[Action Method]

	/** デフォルト表示 */
	@Path("{memberName,[0-9a-zA-Z_]+}")
	public ActionResult index() throws Exception{
		return page();
		
	}

	/** ページング用表示 */
	@Path("{memberName,[0-9a-zA-Z_]+}/{pageNo,[0-9]+}")
	public ActionResult page() throws Exception{
		// メンバー情報取得
		member = memberDao.getMemberByName(memberName);
		if (member == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return new Direct();
		}
		
		if (pageNo < 1) {
			pageNo = 1;
		}
		
		// ボタンの種類を取得
		setButtonType();

		// コメントを表示
		setCommentList();

		// メンバーリスト
		followingList = followingMembersDao.getFollowingMemberList(member
				.getMemberId());

		return new Forward("index.jsp");
	}

	// ----------------------------------------------[Private Method]

	/** 上部ボタンタイプを設定 */
	private void setButtonType() {
		if (userMemberId > 0) {

			if (userMemberId == member.getMemberId()) {
				button = 0;
			} else {
				Boolean isFollowingRequest = followingMembersDao
						.isFollowingRequest(userMemberId, member.getMemberId());
				// 存在しない
				if (isFollowingRequest == null) {
					if (member.isOpen()) {
						// 追加
						button = 1;
					} else {
						// 追加リクエスト
						button = 2;
					}
				}
				// 追加リクエスト中
				else if (isFollowingRequest) {
					button = 3;
				}
				// メンバーに存在
				else {
					button = 0;
				}

			}
		}
	}

	/** コメント一覧を設定 */
	private void setCommentList() {
		if (member.isOpen()) {
			// 公開の場合・表示
			visibleCommentList = true;
		} else {
			// 非公開の場合
			if (userMemberId <= 0) {
				// 未ログインの場合、非表示
				visibleCommentList = false;
				putMessage("member.msg.noOpen");
			} else {
				// ログインの場合、自分またはFollowリストなら表示
				if (button == 0) {
					visibleCommentList = true;
				} else {
					visibleCommentList = false;
					putMessage("member.msg.noFollowing");
				}
			}
		}
		if (visibleCommentList) {
			myPagerConditionDto.setLimit(commentPagerLimit);
			myPagerConditionDto.setPageNo(pageNo);

			commentList = commentDao.getMemberCommentList(myPagerConditionDto,
					member.getMemberId(), userMemberId);

			pagerViewHelper = new PagerViewHelper(myPagerConditionDto);
			
			if (commentList.isEmpty()) {
				putMessage("member.msg.noComment");
			}
		}
	}

	/** メッセージを出力 */
	private void putMessage(String messageKey) {
		flash.put("notice", Messages.getText(messageKey));
	}

}
