package org.seasar.cubby.cubbitter.action;

/**
 *  メンバー表示用Actionクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.MemberPagerConditionDto;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.util.QueryStringBuilder;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.dao.pager.PagerViewHelper;

public class FollowingAction extends DefaultMenuAction {

	// ----------------------------------------------[DI Filed]

	public MemberDao memberDao;
	public MemberPagerConditionDto memberPagerConditionDto;
	public Integer memberPagerLimit;

	// ----------------------------------------------[Attribute]
	
	public PagerViewHelper pagerViewHelper;

	public List<Map<String, Object>> followerList;
	public List<Map<String, Object>> searchResultList;

	public String keyword; // 検索キーワード
	public int memberId; // 削除・追加するメンバーID
	public String encodedParam; // エンコードされたパラメータ
	public int pageNo = 1;

	// ----------------------------------------------[Override]

	@Override
	public void prerender() {
		setMainMenuData();
	}

	// ----------------------------------------------[Action]

	/** 登録している人 */
	public ActionResult index() {
		return new Forward("index.jsp");
	}

	/** 登録されている人 */
	public ActionResult follower() {
		followerList = followingMembersDao.getFollowerMemberList(userMemberId);
		return new Forward("follower.jsp");
	}

	/** 検索画面表示 */
	public ActionResult presearch() {
		return new Forward("search.jsp");
	}

	/** 検索ボタンクリック */
	@Validation(rules = "searchValidation", errorPage = "search.jsp")
	public ActionResult search() throws Exception {
		if (keyword != null) {
			keyword = keyword.trim();
			if (!keyword.equals("")) {
				// リダイレクトする。
				pageNo = 1;
				return new Redirect("doSearch?" + getSearchParam());
			}
		}
		return new Forward("search.jsp");
	}

	/** リダイレクト先・ページング先 */
	public ActionResult doSearch() throws Exception {
		if (pageNo < 1) {
			pageNo = 1;
		}
		if (keyword != null && !keyword.equals("")) {
			memberPagerConditionDto.setLimit(memberPagerLimit);
			memberPagerConditionDto.setPageNo(pageNo);
			memberPagerConditionDto.setUserMemberId(userMemberId);
			memberPagerConditionDto.setKeyword(keyword + "%");
			searchResultList = memberDao
					.searchMemberList(memberPagerConditionDto);
			if (searchResultList.size() < 1) {
				errors.add(Messages.getText("search.msg.noResult"));
			} else {
				encodedParam = "search?" + getSearchParam();
			}
			pagerViewHelper = new PagerViewHelper(memberPagerConditionDto);
		}
		return new Forward("search.jsp");
	}

	/**「登録している人」からの削除 */
	@Path("del/{memberId,[0-9]+}")
	public ActionResult del1() {
		followingMembersDao.deleteFollowing(userMemberId, memberId);
		return new Redirect("./");
	}

	/** 「登録されている人」から追加 */
	@Path("add/{memberId,[0-9]+}/follower")
	public ActionResult add1() {
		insertFollowing();
		return new Redirect("follower");
	}

	/** 「検索」から追加 */
	@Path("add/{memberId,[0-9]+}/search")
	public ActionResult add2() throws Exception {
		insertFollowing();
		return new Redirect("doSearch?" + getSearchParam());
	}

	/** 個人ごとの画面から追加 */
	@Path("add/{memberId,[0-9]+}/member")
	public ActionResult add3() {
		Member member = insertFollowing();
		if (member == null) {
			return new Redirect("/home");
		} else {
			return new Redirect("/member/" + member.getMemberName());
		}
	}

	/** 個人ごとの画面から削除 */
	@Path("del/{memberId,[0-9]+}/member")
	public ActionResult del2() {
		Member member = memberDao.getMemberById(memberId);
		if (member == null) {
			return new Redirect("/home");
		} else {
			followingMembersDao.deleteFollowing(userMemberId, memberId);
			return new Redirect("/member/" + member.getMemberName());
		}
	}

	// ----------------------------------------------[Private Method]

	/** follow処理 */
	private Member insertFollowing() {
		Member member = memberDao.getMemberById(memberId);
		// memberIdが正しくて、既にFollowされていなければinsert
		if (member != null
				&& followingMembersDao.isFollowingRequest(userMemberId,
						memberId) == null) {
			boolean isRequest;
			// 公開の場合
			if (member.isOpen()) {
				isRequest = false;
			}
			// 非公開の場合
			else {
				// 相手に登録されていなければRequest
				Boolean isFollowerRequest = followingMembersDao
						.isFollowingRequest(memberId, userMemberId);
				if (isFollowerRequest == null || isFollowerRequest) {
					isRequest = true;
				} else {
					isRequest = false;
				}
			}

			followingMembersDao.insertFollowing(userMemberId, memberId,
					isRequest);

		}
		return member;
	}

	/** 検索パラメータ */
	private String getSearchParam() {
		QueryStringBuilder query = new QueryStringBuilder();
		query.addParam("keyword", keyword);
		query.addParam("pageNo", pageNo);
		return query.toString();
	}

	// ----------------------------------------------[Validation]
	/** 検索実行時バリデータ */
	public ValidationRules searchValidation = new DefaultValidationRules(
			"search.") {
		@Override
		public void initialize() {
			add("keyword", new RequiredValidator());
		}
	};
}
