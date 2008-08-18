package org.seasar.cubby.cubbitter.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.entity.Entry;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class FeedUtils {

	public static void writeRss2Feed(String title, String description,
			List<Entry> entries, HttpServletRequest request,
			HttpServletResponse response) throws IOException, FeedException {
		response.setContentType("application/rss+xml;charset=UTF-8");
		writeFeed("rss_2.0", title, description, entries, request, response);
	}

	public static void writeAtomFeed(String title, String description,
			List<Entry> entries, HttpServletRequest request,
			HttpServletResponse response) throws IOException, FeedException {
		response.setContentType("application/atom+xml;charset=UTF-8");
		writeFeed("atom_1.0", title, description, entries, request, response);
	}

	private static void writeFeed(String feedType, String title,
			String description, List<Entry> entries,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, FeedException {
		SyndFeed syndFeed = new SyndFeedImpl();
		syndFeed.setFeedType(feedType);
		syndFeed.setTitle(title);
		syndFeed.setLink("/");
		syndFeed.setDescription(description);

		if (entries.size() > 0) {
			syndFeed.setPublishedDate(entries.get(0).getPost());
		}

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();
		for (Entry entry : entries) {
			SyndEntry syndEntry = toSyndEntry(entry, request);
			syndEntries.add(syndEntry);
		}
		syndFeed.setEntries(syndEntries);

		SyndFeedOutput output = new SyndFeedOutput();
		output.output(syndFeed, response.getWriter());
	}

	private static SyndEntry toSyndEntry(Entry entry, HttpServletRequest request) {
		Account account = entry.getAccount();
		String content = account.getName() + " : " + entry.getText();

		SyndEntry syndEntry = new SyndEntryImpl();
		syndEntry.setTitle(content);
		syndEntry.setAuthor(account.getName() + " / " + account.getFullName());
		syndEntry.setPublishedDate(entry.getPost());

		SyndContent description = new SyndContentImpl();
		description.setValue(content);
		syndEntry.setDescription(description);
		syndEntry.setLink(request.getContextPath() + "/entry/" + entry.getId());
		return syndEntry;
	}

}
