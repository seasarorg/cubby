package org.seasar.cubby.exmaple.gae.action;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.exmaple.gae.dto.SearchDto;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class SearchAction extends Action {

	private SearchDto searchDto;

	public SearchDto getSearchDto() {
		return this.searchDto;
	}

	@Inject
	@RequestParameter
	public void setSearchDto(SearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public ActionResult index() throws Exception {
		return new Forward("index.jsp");
	}

	@Form("searchDto")
	@Accept(RequestMethod.POST)
	public ActionResult search() throws Exception {
		return new Forward("search.jsp");
	}

}
