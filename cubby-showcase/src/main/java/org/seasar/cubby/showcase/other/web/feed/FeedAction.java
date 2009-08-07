package org.seasar.cubby.showcase.other.web.feed;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Forward;

/**
 * Feed配信を行うAction
 * @author agata
 */
public class FeedAction extends Action {
	
	// ----------------------------------------------[DI Filed]

	public HttpServletResponse response;

	public ActionResult index() {
		return new Forward("index.jsp");
	}
	
	// ----------------------------------------------[Action Method]

	public ActionResult atom() {
		Feed feed = getFeed();
		response.setContentType("application/atom+xml");
		Document<Feed> doc = feed.getDocument();
		try {
			doc.writeTo(response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			response.setStatus(500);
		}
		return new Direct();
	}

	private Feed getFeed() {
		Feed feed = Abdera.getNewFactory().newFeed();
		feed.setTitle("RSS Sample");
		feed.setText("RSS Sample text");
		for (int i = 0; i < 10; i++) {
			Entry entry = feed.addEntry();
			entry.setId(String.valueOf(i));
			entry.setTitle("entry title" + i);	
			entry.setContent("entry content" + i);
			entry.addAuthor("user" + i);
			entry.setUpdated(new Date());
		}
		return feed;
	}
}
