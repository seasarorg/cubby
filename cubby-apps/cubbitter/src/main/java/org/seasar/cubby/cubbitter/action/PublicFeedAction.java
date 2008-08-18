package org.seasar.cubby.cubbitter.action;

import java.io.IOException;
import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.FeedUtils;
import org.seasar.cubby.util.Messages;

import com.sun.syndication.io.FeedException;

@Path("/")
public class PublicFeedAction extends AbstractAction {

	public EntryService entryService;

	public ActionResult rss2() throws Exception {
		new FeedWriter() {

			@Override
			void writeEntries(String title, String description,
					List<Entry> entries) throws IOException, FeedException {
				FeedUtils.writeRss2Feed(title, description, entries, request,
						response);
			}

		}.write();
		return new Direct();
	}

	public ActionResult atom() throws Exception {
		new FeedWriter() {

			@Override
			void writeEntries(String title, String description,
					List<Entry> entries) throws IOException, FeedException {
				FeedUtils.writeAtomFeed(title, description, entries, request,
						response);
			}

		}.write();
		return new Direct();
	}

	private abstract class FeedWriter {

		void write() throws IOException, FeedException {
			String title = Messages.getText("feed.public.title");
			String description = Messages.getText("feed.public.description");
			List<Entry> entries = entryService.getPublicEntries();
			if (entries.size() > Constants.FEEDS_MAX_RESULT) {
				entries = entries.subList(0, Constants.FEEDS_MAX_RESULT);
			}
			writeEntries(title, description, entries);
		}

		abstract void writeEntries(String title, String description,
				List<Entry> entries) throws IOException, FeedException;
	}

}
