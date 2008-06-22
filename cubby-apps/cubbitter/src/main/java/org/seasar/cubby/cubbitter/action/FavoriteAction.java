package org.seasar.cubby.cubbitter.action;

/**
 * お気に入りコメント表示用Actionクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.dao.FavoriteCommentsDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.cubby.util.Messages;
import org.seasar.dao.pager.PagerViewHelper;

public class FavoriteAction extends DefaultMenuAction {
	// ----------------------------------------------[DI Filed]
	
	public FavoriteCommentsDao favoriteCommentsDao;

	public MyPagerConditionDto myPagerConditionDto;
	public Integer commentPagerLimit;

	// ----------------------------------------------[Attribute]
	
	public List<Map<String, Object>> commentList;
	public PagerViewHelper pagerViewHelper;

	public int pageNo;

	// ----------------------------------------------[Action Method]
	
	/** 一覧表示 */
	@Path("{pageNo,[0-9]*}")
	public ActionResult index() {
		if (pageNo < 1) {
			pageNo = 1;
		}

		myPagerConditionDto.setLimit(commentPagerLimit);
		myPagerConditionDto.setPageNo(pageNo);

		pagerViewHelper = new PagerViewHelper(myPagerConditionDto);

		commentList = favoriteCommentsDao.getFavoriteComments(
				myPagerConditionDto, userMemberId);

		if (commentList == null || commentList.size() < 1) {
			getFlash().put("notice", Messages.getText("favorite.msg.notFound"));
		}

		setMainMenuData();
		
		return new Forward("index.jsp");
	}
}
