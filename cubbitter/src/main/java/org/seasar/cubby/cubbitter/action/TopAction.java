package org.seasar.cubby.cubbitter.action;

/** 
 * トップ画面表示用Actionクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.dao.pager.PagerViewHelper;

public class TopAction extends DefaultMenuAction {

	// ----------------------------------------------[DI Filed]

	public CommentDao commentDao;
	public MyPagerConditionDto myPagerConditionDto;
	public Integer commentPagerLimit;

	// ----------------------------------------------[Attribute]
	
	public List<Map<String, Object>> commentList;
	public PagerViewHelper pagerViewHelper;
	public int pageNo;

	// ----------------------------------------------[Action Method]

	/** トップ画面 */
	@Path("{pageNo,[0-9]*}")
	public ActionResult index() {
		if (pageNo < 1) {
			pageNo = 1;
		}
		myPagerConditionDto.setLimit(commentPagerLimit);
		myPagerConditionDto.setPageNo(pageNo);
		
		pagerViewHelper = new PagerViewHelper(myPagerConditionDto);

		if (sessionScope.containsKey("user")) {
			setMainMenuData();
			commentList = commentDao.getPublicCommentList(myPagerConditionDto, userMemberId);
		}else{
			commentList = commentDao.getPublicCommentList(myPagerConditionDto, -1);
		}
		
		return new Forward("index.jsp");
	}

}
