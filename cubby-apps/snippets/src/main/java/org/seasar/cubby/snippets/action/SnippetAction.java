package org.seasar.cubby.snippets.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.snippets.entity.Comment;
import org.seasar.cubby.snippets.entity.Language;
import org.seasar.cubby.snippets.entity.Snippet;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.framework.beans.util.Beans;

public class SnippetAction extends BaseAction {

	@SuppressWarnings("unused")
	public ValidationRules rules = new DefaultValidationRules("") {
		protected void initialize() {
			add("title", "snippet.title", new RequiredValidator());
			add("content", "snippet.content", new RequiredValidator());
		}
	};
	
	public String id;
	public String langPath;
	public String title;
	public String content;
	public String updated;
	public List<Comment> comments;
	public String text;
	public String name;
	
	@Path("/{langPath}/{id,[0-9]+}")
	public ActionResult index() {
		Snippet snippet = db.from(Snippet.class).id(id).getSingleResult();
		comments = db.from(Comment.class).where("snippet_id=?", id).getResultList();
		Beans.copy(snippet, this).dateConverter("yyyy-MM-dd HH:mm:dd", "updated").execute();
		return new Forward("/snippet/show.jsp");
	}

	@Path("/{langPath}/{id,[0-9]+}/json")
	public ActionResult json() {
		Snippet snippet = db.from(Snippet.class).id(id).getSingleResult();
		return new Json(snippet);
	}

	@Path("/{langPath}/create")
	public ActionResult create() {
		return new Forward("/snippet/edit.jsp");
	}

	@Path("/{langPath}/{id,[0-9]+}/edit")
	public ActionResult edit() {
		Snippet snippet = db.from(Snippet.class).id(id).getSingleResult();
		Beans.copy(snippet, this).dateConverter("yyyy-MM-dd HH:mm:dd", "updated").execute();
		return new Forward("/snippet/edit.jsp");
	}

	@Validation(rules="rules", errorPage="/snippet/edit.jsp")
	@Path("/{langPath}/save")
	public ActionResult save() {
		Language language = db.from(Language.class).where("path=?", langPath).getSingleResult();
		Snippet snippet = Beans.createAndCopy(Snippet.class, this).execute();
		snippet.setLanguageId(language.getId());
		db.insert(snippet).excludesNull().execute();
		flash.put("notice", "registed...");
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("langPath", new String[] { language.getPath() });
		params.put("id", new String[] { String.valueOf(snippet.getId()) });
		return new Redirect(SnippetAction.class, "index", params);
	}

	@Path("/{langPath}/{id,[0-9]+}/addcomment")
	public ActionResult addcomment() {
		Language language = db.from(Language.class).where("path=?", langPath).getSingleResult();
		Comment comment = new Comment();
		comment.setText(this.text);
		comment.setSnippetId(Integer.parseInt(id));
		comment.setName(this.name);
		db.insert(comment).excludesNull().execute();
		flash.put("notice", "registed...");
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("langPath", new String[] { language.getPath() });
		params.put("id", new String[] { String.valueOf(id) });
		return new Redirect(SnippetAction.class, "index", params);
	}
}
