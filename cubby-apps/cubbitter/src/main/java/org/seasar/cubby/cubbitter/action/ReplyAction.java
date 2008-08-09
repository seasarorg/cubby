package org.seasar.cubby.cubbitter.action;

/** 
 * 返信コメント表示用Actionクラス
 */

import java.util.List;
import java.util.Map;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.dao.ReplyCommentsDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.cubby.util.Messages;
import org.seasar.dao.pager.PagerViewHelper;

public class ReplyAction extends DefaultMenuAction {

	// ----------------------------------------------[DI Filed]
	
	public ReplyCommentsDao replyCommentsDao;

	public MyPagerConditionDto myPagerConditionDto;
	public Integer commentPagerLimit;

	// ----------------------------------------------[Attribute]
	
	public List<Map<String, Object>> commentList;
	public PagerViewHelper pagerViewHelper;

	public int pageNo;

	// ----------------------------------------------[Action Method]
	
	// 一覧表示
	@Path("{pageNo,[0-9]*}")
	public ActionResult index() {
		if (pageNo < 1) {
			pageNo = 1;
		}
		
		myPagerConditionDto.setLimit(commentPagerLimit);
		myPagerConditionDto.setPageNo(pageNo);
		commentList = replyCommentsDao
				.getReplyComments(myPagerConditionDto, userMemberId);
		pagerViewHelper = new PagerViewHelper(myPagerConditionDto);
		
		
		if(commentList == null || commentList.size() < 1){
			getFlash().put("notice", Messages.getText("reply.msg.notFound"));
		}
		
		setMainMenuData();
		
		return new Forward("index.jsp");
	}
}
