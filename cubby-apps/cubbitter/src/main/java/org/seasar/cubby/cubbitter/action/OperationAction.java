package org.seasar.cubby.cubbitter.action;

/** コメントに対する操作用Actionクラス */

import java.util.Map;


import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dao.FavoriteCommentsDao;
import org.seasar.cubby.cubbitter.entity.Member;

public class OperationAction extends Action {
	
	// ----------------------------------------------[DI Filed]
	
	public Map<String, Object> sessionScope;
	public CommentDao commentDao;
	public FavoriteCommentsDao favoriteCommentsDao;

	// ----------------------------------------------[Attribute]
	
	public String kind;
	public String op;
	public int commentId;
	public String returnAction;
	public int pageNo;
	public String memberName;

	// ----------------------------------------------[Private Filed]
	
	private int userMemberId;

	private static final String PARAM1 = "/{commentId,[0-9]+}/{returnAction,comment}";
	private static final String PARAM2 = "/{commentId,[0-9]+}/{returnAction,top|home|favorite|reply}/{pageNo,[0-9]+}";
	private static final String PARAM3 = "/{commentId,[0-9]+}/{returnAction,member}/{memberName,[0-9a-zA-Z_]+}/{pageNo,[0-9]+}";

	// ----------------------------------------------[Override]
	
	@Override
	public void initialize() {
		userMemberId = ((Member) sessionScope.get("user")).getMemberId();
	}

	// ----------------------------------------------[Action Method]
	
	@Path("{kind,fav|comment}/del" + PARAM1)
	public ActionResult del1() {
		delete();
		if(kind.equals("comment")){
			return new Redirect("/home/");
		}else{
			return createReirect(1);
		}
	}

	@Path("{kind,fav|comment}/del" + PARAM2)
	public ActionResult del2() {
		delete();
		return createReirect(2);
	}

	@Path("{kind,fav|comment}/del" + PARAM3)
	public ActionResult del3() {
		delete();
		return createReirect(3);
	}

	@Path("fav/add" + PARAM1)
	public ActionResult add1() {
		addFavorite();
		return createReirect(1);
	}

	@Path("fav/add" + PARAM2)
	public ActionResult add2() {
		addFavorite();
		return createReirect(2);
	}

	@Path("fav/add" + PARAM3)
	public ActionResult add3() {
		addFavorite();
		return createReirect(3);
	}

	// ----------------------------------------------[Private Method]
	
	/** 削除共通処理 */
	private void delete() {
		if (kind.equals("fav")) {
			deleteFavorite();
		} else {
			deleteComment();
		}
	}

	/** お気に入りの追加 */
	private void addFavorite() {
		// commentIdが存在し、既にお気に入りに存在しないこと
		if (commentDao.isExist(commentId)
				&& !favoriteCommentsDao.isExist(userMemberId, commentId)) {
			favoriteCommentsDao.insert(userMemberId, commentId);
		}
	}

	/** お気に入りの削除 */
	private void deleteFavorite() {
		favoriteCommentsDao.delete(userMemberId, commentId);
	}

	/** コメントの削除 */
	private void deleteComment() {
		// 自分が投稿したものであれば削除
		Map<String, Object> comment = commentDao.getComment(commentId, -1);
		if (comment.get("memberId").equals(userMemberId)) {
			commentDao.deleteComment(commentId);
		}
	}

	/** リダイレクト処理 */
	private ActionResult createReirect(int returnType) {
		StringBuilder str = new StringBuilder();
		str.append("/").append(returnAction).append("/");
		switch (returnType) {
		case 1:
			str.append(commentId);
			break;
		case 2:
			str.append(pageNo);
			break;
		default:
			str.append(memberName).append("/").append(pageNo);
			break;
		}
		return new Redirect(str.toString());
	}
}
