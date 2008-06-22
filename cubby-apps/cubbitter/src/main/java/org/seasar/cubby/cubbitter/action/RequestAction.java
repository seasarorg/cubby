package org.seasar.cubby.cubbitter.action;

/** 
 * 承認リクエスト用Actionクラス
 */

import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;

public class RequestAction extends DefaultMenuAction {

	// ----------------------------------------------[Attribute]
	
	public List<Map<String, Object>> requestedMemberList;
	public int memberId;


	// ----------------------------------------------[Override]

	@Override
	public void prerender() {
		setMainMenuData();
	}

	// ----------------------------------------------[Action Method]

	/** 初期表示 */
	public ActionResult index() {
		requestedMemberList = followingMembersDao
				.getRequestedMemberList(userMemberId);
		return new Forward("index.jsp");
	}

	/** 承諾 */
	@Path("accept/{memberId,[0-9]+}")
	public ActionResult accept() {
		followingMembersDao.updateRequestAccept(memberId, userMemberId);
		return acceptDenyRedirect();
	}

	/** 拒否 */
	@Path("deny/{memberId,[0-9]+}")
	public ActionResult deny() {
		followingMembersDao.deleteFollowing(memberId, userMemberId);
		return acceptDenyRedirect();
	}
	
	// ----------------------------------------------[Private Method]
	
	/** 承諾・拒否時リダイレクト */
	private ActionResult acceptDenyRedirect(){
		int count = followingMembersDao.getRequestedCount(userMemberId);
		if(count > 0){
			return new Redirect("./");
		}else{
			return new Redirect("../home/");
		}
	}
	
}
