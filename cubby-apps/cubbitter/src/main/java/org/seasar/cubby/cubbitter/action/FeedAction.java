package org.seasar.cubby.cubbitter.action;

/** フィード表示用親クラス */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class FeedAction extends Action {
	
	// ----------------------------------------------[DI Filed]
	
	public HttpServletRequest request;
	public HttpServletResponse response;
	public Integer commentPagerLimit;

	// ----------------------------------------------[Protected]
	
	protected List<Map<String, Object>> commentList;
	protected String title;
	protected String description;

	protected String url;
	
	protected void writeRss1Feed() throws Exception {
		response.setContentType("application/rss+xml;charset=UTF-8");
		writeFeed("rss_1.0");
	}

	protected void writeRss2Feed() throws Exception {
		response.setContentType("application/rss+xml;charset=UTF-8");
		writeFeed("rss_2.0");
	}

	protected void writeAtomFeed() throws Exception {
		response.setContentType("application/atom+xml;charset=UTF-8");
		writeFeed("atom_1.0");
	}

	// ----------------------------------------------[Private Method]
	
	private void writeFeed(String feedType) throws Exception {
		// URL
		setRequestUrl();

		// フィードの作成
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType(feedType);
		feed.setTitle(title);
		feed.setLink(url);
		feed.setDescription(description);

		if (commentList.size() > 0) {
			feed.setPublishedDate((Date) (commentList.get(0).get("postTime")));
		}

		// 記事
		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		for (Map<String, Object> map : commentList) {
			SyndEntry entry = createEntryFromMap(map);
			entries.add(entry);
		}

		feed.setEntries(entries);

		SyndFeedOutput output = new SyndFeedOutput();
		output.output(feed, response.getWriter());
	}

	private void setRequestUrl() {
		StringBuffer sb = new StringBuffer(request.getRequestURL());
		int fromIndex = sb.length() + 1;
		for (int i = 0; i < 3; i++) {
			fromIndex = sb.lastIndexOf("/", fromIndex - 1);
		}
		sb.delete(fromIndex + 1, sb.length());
		url = sb.toString();
	}

	private SyndEntry createEntryFromMap(Map<String, Object> map) {

		String content = map.get("memberName") + " : " + map.get("comment");

		SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(content);
		entry.setAuthor(map.get("memberName") + " / " + map.get("fullName"));
		entry.setPublishedDate((Date) (map.get("postTime")));

		SyndContent description = new SyndContentImpl();
		description.setValue(content);
		entry.setDescription(description);
		entry.setLink(url + "comment/" + map.get("commentId"));
		return entry;
	}
}
