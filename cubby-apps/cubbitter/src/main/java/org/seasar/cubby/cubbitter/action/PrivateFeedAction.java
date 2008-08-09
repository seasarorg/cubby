package org.seasar.cubby.cubbitter.action;

/** 個人用フィード表示用アクションクラス */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletResponse;


import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.cubbitter.dao.CommentDao;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.MyPagerConditionDto;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.util.Messages;

public class PrivateFeedAction extends FeedAction {
	
	// ----------------------------------------------[DI Field]
	
	public Map<String, Object> sessionScope;
	public MemberDao memberDao;
	public CommentDao commentDao;

	// ----------------------------------------------[Private Field]
	
	private Member user;

	// ----------------------------------------------[Action Method]
	
	public ActionResult rss1() throws Exception {
		if (!checkAuth()) {
			writeAuthError();
		} else {
			initCommon();
			writeRss1Feed();
		}
		return new Direct();
	}

	public ActionResult rss2() throws Exception {
		if (!checkAuth()) {
			writeAuthError();
		} else {
			initCommon();
			writeRss2Feed();
		}
		return new Direct();
	}

	public ActionResult atom() throws Exception {
		if (!checkAuth()) {
			writeAuthError();
		} else {
			initCommon();
			writeAtomFeed();
		}
		return new Direct();
	}

	// ----------------------------------------------[Private Method]
	
	/** 認証処理 */
	private boolean checkAuth() throws Exception {
		// セッションから取得
		if (sessionScope.containsKey("user")) {
			user = (Member) sessionScope.get("user");
			return true;
		}

		// basic認証する
		String basicAuthData = request.getHeader("authorization");
		if (basicAuthData == null
				|| !basicAuthData.toUpperCase().startsWith("BASIC")) {
			return false;
		}
		String basicAuthBody = basicAuthData.substring(6);
		ByteArrayInputStream bin = new ByteArrayInputStream(basicAuthBody
				.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				MimeUtility.decode(bin, "base64"), "UTF-8"));

		StringBuilder buf = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			buf.append(line);
		}
		int index = buf.indexOf(":");
		if (index <= 0 && buf.length() <= index) {
			return false;
		}

		// データベースをチェック
		String memberName = buf.substring(0, index);
		String password = buf.substring(index + 1);

		user = memberDao.getMemberByName(memberName);
		if (user == null || !user.getPassword().equals(password)) {
			return false;
		}

		return true;
	}

	/** Basic認証エラーを出力 */
	private void writeAuthError() throws Exception {
		response.setHeader("WWW-Authenticate", "BASIC realm=\"users\"");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/** 共通初期化 */
	private void initCommon() {
		title = Messages.getText("feed.private.title");
		description = Messages.getText("feed.private.description", user.getFullName());
		MyPagerConditionDto dto = new MyPagerConditionDto();
		dto.setLimit(commentPagerLimit);
		dto.setPageNo(1);
		commentList = commentDao.getFollowingMemberCommentList(dto, user.getMemberId());
	}

}
