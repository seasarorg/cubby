package org.seasar.cubby.cubbitter.action;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.cubby.util.Messages;

public class PublicFeedAction extends FeedAction {
	
	// ----------------------------------------------[DI Field]
	
	public CommentDao commentDao;
	
	// ----------------------------------------------[Action Method]
	
	public ActionResult rss1() throws Exception {
		initCommon();
		writeRss1Feed();
		return new Direct();
	}

	public ActionResult rss2() throws Exception {
		initCommon();
		writeRss2Feed();
		return new Direct();
	}

	public ActionResult atom() throws Exception {
		initCommon();
		writeAtomFeed();
		return new Direct();
	}

	// ----------------------------------------------[Private Method]
	
	/** 共通初期化 */
	private void initCommon() {
		title = Messages.getText("feed.public.title");
		description = Messages.getText("feed.public.description");
		// データ取得
		MyPagerConditionDto dto = new MyPagerConditionDto();
		dto.setLimit(commentPagerLimit);
		dto.setPageNo(1);
		commentList = commentDao.getPublicCommentList(dto, -1);
	}
}
