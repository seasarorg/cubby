package org.seasar.cubby.cubbitter.action;

/**
 * ホーム（ログイン後の初期表示）用Actionクラス
 */

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.dao.pager.PagerViewHelper;

public class HomeAction extends DefaultMenuAction {

	// ----------------------------------------------[DI Filed]

	public CommentDao commentDao;
	public MemberDao memberDao;
	public MyPagerConditionDto myPagerConditionDto;
	public Integer commentPagerLimit;

	// ----------------------------------------------[Attribute]
	
	public List<Map<String, Object>> commentList;
	public PagerViewHelper pagerViewHelper;
	public String comment;
	public int pageNo = 1;

	// ----------------------------------------------[Override]

	@Override
	public void prerender() {

		myPagerConditionDto.setLimit(commentPagerLimit);
		myPagerConditionDto.setPageNo(pageNo);
		commentList = commentDao
				.getFollowingMemberCommentList(myPagerConditionDto, userMemberId);
		pagerViewHelper = new PagerViewHelper(myPagerConditionDto);

		setMainMenuData();
	}

	// ----------------------------------------------[Action]

	/** ホーム画面表示 */
	@Path("{pageNo,[0-9]*}")
	public ActionResult index() {
		if (pageNo < 1) {
			pageNo = 1;
		}
	
		return new Forward("index.jsp");
	}

	/** コメントの登録 */
	@Validation(rules = "commentValidation", errorPage = "index.jsp")
	public ActionResult comment() {
		if (comment != null) {
			comment = comment.trim();
			if (!comment.equals("")) {
				
				Pattern pattern1 = Pattern.compile("^@([_0-9a-zA-Z]+)");
				Matcher matcher = pattern1.matcher(comment);
				int replyMemberId = -1;
				
				if(matcher.find()){
					Member replyMember = memberDao.getMemberByName(matcher.group(1));
					if(replyMember != null){
						replyMemberId = replyMember.getMemberId();
					}
				}
				commentDao.insertComment(comment, userMemberId, replyMemberId);
			}
		}
		return new Redirect("./");
	}

	// ----------------------------------------------[Validation]

	/** コメントのValidation */
	public ValidationRules commentValidation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("comment", new MaxLengthValidator(140));
		}
	};

}
