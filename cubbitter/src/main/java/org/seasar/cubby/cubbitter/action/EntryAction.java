package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.FORBIDDEN;
import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;

public class EntryAction extends AbstractAction {

	public EntryService entryService;

	@RequestParameter
	public Long entryId;

	public Entry entry;

	@Path("{entryId,[0-9]+}")
	public ActionResult index() throws Exception {
		entry = entryService.find(entryId);
		if (entry == null) {
			return NOT_FOUND;
		}
		if (!entry.getAccount().isOpen()) {
			if (!loginAccount.getFollowings().contains(entry.getAccount())) {
				return FORBIDDEN;
			}
		}
		return new Forward("index.jsp");
	}

}
