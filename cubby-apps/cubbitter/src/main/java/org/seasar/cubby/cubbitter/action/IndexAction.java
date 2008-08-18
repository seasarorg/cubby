package org.seasar.cubby.cubbitter.action;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.Pager;

@Path("/")
public class IndexAction extends AbstractAction {

	public EntryService entryService;

	@RequestParameter
	public int pageNo = 1;

	public List<Entry> entries;

	public Pager<Entry> pager;

	public ActionResult index() {
		List<Entry> entries = entryService.getPublicEntries();
		pager = new Pager<Entry>(entries, pageNo, Constants.ENTRIES_MAX_RESULT);
		this.entries = pager.subList();
		return new Forward("index.jsp");
	}

}
