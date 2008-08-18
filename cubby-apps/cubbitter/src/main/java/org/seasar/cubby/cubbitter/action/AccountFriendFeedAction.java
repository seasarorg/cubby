package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.REQUEST_BASIC_AUTHENTICATION;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.AuthenticationException;
import org.seasar.cubby.cubbitter.service.BasicAuthenticationService;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.FeedUtils;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

import com.sun.syndication.io.FeedException;

@Path("{account,[0-9a-zA-Z_]+}/friend")
public class AccountFriendFeedAction extends AbstractAccountAction {

	public BasicAuthenticationService basicAuthenticationService;

	public EntryService entryService;

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new BasicAuthenticationValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}

	};

	class BasicAuthenticationValidationRule implements ValidationRule {

		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) throws ValidationException {
			if (loginAccount == null) {
				try {
					loginAccount = basicAuthenticationService
							.authenticate(request);
				} catch (AuthenticationException e) {
					throw new ValidationException(REQUEST_BASIC_AUTHENTICATION);
				}
			}
		}

	}

	@Validation(rules = "validationRules")
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

	@Validation(rules = "validationRules")
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
			String title = Messages.getText("feed.friend.title");
			String description = Messages.getText("feed.friend.description",
					account.getFullName());
			List<Entry> entries = entryService.getFollowingEntries(account);
			if (entries.size() > Constants.FEEDS_MAX_RESULT) {
				entries = entries.subList(0, Constants.FEEDS_MAX_RESULT);
			}
			writeEntries(title, description, entries);
		}

		abstract void writeEntries(String title, String description,
				List<Entry> entries) throws IOException, FeedException;
	}

}
