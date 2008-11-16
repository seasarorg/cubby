package org.seasar.cubby.wiki.action;

import static org.seasar.cubby.action.RequestMethod.GET;
import static org.seasar.cubby.action.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.cubby.wiki.entity.Page;
import org.seasar.cubby.wiki.service.PageService;
import org.seasar.cubby.wiki.service.WikiService;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;


@RequestScoped
@Path("/")
public class PageAction extends Action {

	@Inject
	private PageService pageService;
	@Inject
	private WikiService wikiService;
	
	public HttpServletRequest request; 
	
	public final ValidationRules saveValidation = new DefaultValidationRules() {
		public void initialize() {
			add("name", new RequiredValidator());
			add(DATA_CONSTRAINT, new ExistPageNameValidator());
		}
		@Override
		public ActionResult fail(String errorPage) {
			page = pageService.getPageById(id);
			return super.fail(errorPage);
		}
	};
	
	class ExistPageNameValidator implements ValidationRule {
		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) throws ValidationException {
			if (isExistPageName()) {
				errors.add(Messages.getText("err.existPageName"), "name");
			}
		}
		private boolean isExistPageName() {
			Page p = pageService.getPageByName(name);
			return p != null && (id == null || !id.equals(p.getId()));
		}
	}
	
	private Page page;
	private Integer id;
	private String name;
	private String content;

	@Accept(GET)
	public ActionResult index() {
		return new Forward(PageAction.class, "show")
			.param("page", "FrontPage");
	}
	
	@Accept(GET)
	@Path("/pages/{page,.+}")
	public ActionResult show() throws Exception {
		if (page == null) {
			name = request.getParameter("page");
			return new Forward("edit.jsp");
		}
		this.id = page.getId();
		this.name = page.getName();
		this.content = page.getContent();
		return new Forward("index.jsp");
	}
	
	@Accept(GET)
	@Path(value="/pages/edit/", priority=0)
	public ActionResult newEdit() throws Exception {
		name = "New Page";
		return new Forward("edit.jsp");
	}
	
	@Accept(GET)
	@Path(value="/pages/edit/{page,.+}", priority=0)
	public ActionResult edit() throws Exception {
		this.id = page.getId();
		this.name = page.getName();
		this.content = page.getContent();
		return new Forward("edit.jsp");
	}

	@Accept(POST)
	@Path("/pages/")
	@Validation(rules="saveValidation", errorPage="edit.jsp")
	public ActionResult save() throws Exception {
		Page page = new Page();
		page.setId(this.id);
		page.setName(this.name);
		page.setContent(this.content);
		pageService.save(page);
		flash.put("notice", Messages.getText("msg.updated"));
		return new Redirect(PageAction.class, "show")
			.param("page", page.getName());
	}

	public List<Page> getPages() {
		return pageService.getPages();
	}
	
	public String getPageContent() {
		return wikiService.render(page.getContent());
	}

	public Page getPage() {
		return page;
	}

	@RequestParameter
	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getId() {
		return id;
	}

	@RequestParameter
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@RequestParameter
	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	@RequestParameter
	public void setContent(String content) {
		this.content = content;
	}
}