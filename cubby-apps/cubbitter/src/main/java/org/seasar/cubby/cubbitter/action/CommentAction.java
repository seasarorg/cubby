package org.seasar.cubby.cubbitter.action;

/**
 * コメント個別表示用Actionクラス
 */

import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.entity.Member;

public class CommentAction extends Action {

	// ----------------------------------------------[DI Filed]
	
	public HttpServletResponse response;
	public Map<String, Object> sessionScope;
	public CommentDao commentDao;

	// ----------------------------------------------[Attribute]
	
	public int commentId;
	public Map<String, Object> comment;

	// ----------------------------------------------[Action Method]
	
	/** デフォルト表示 */
	@Path("{commentId,[0-9]+}")
	public ActionResult index() throws Exception{
		int userMemberId;
		if (sessionScope.containsKey("user")) {
			userMemberId = ((Member) sessionScope.get("user")).getMemberId();
		} else {
			userMemberId = -1;
		}
		comment = commentDao.getComment(commentId, userMemberId);
		if (comment == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return new Direct();
		} else {
			return new Forward("index.jsp");
		}
	}
}
