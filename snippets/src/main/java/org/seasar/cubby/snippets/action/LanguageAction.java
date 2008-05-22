package org.seasar.cubby.snippets.action;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.snippets.entity.Language;
import org.seasar.cubby.snippets.entity.Snippet;
import org.seasar.framework.util.JSONSerializer;

public class LanguageAction extends BaseAction {

	public String langPath;
	public String query;
	public Language language;
	public List<Snippet> snippets;
	
	@Path(value="/{langPath}", priority=0)
	public ActionResult index() {
		this.language = db.from(Language.class).where("path=?", langPath).getSingleResult();
		this.snippets = db.from(Snippet.class).where(
				"language_id=?", language.getId()).orderBy("updated desc").limit(5).getResultList();
		return new Forward("/lang.jsp");
	}

	@Path(value="/{langPath}/search/{query}")
	public ActionResult search() {
		this.language = db.from(Language.class).where("path=?", langPath).getSingleResult();
		this.snippets = db.from(Snippet.class).where(
				"language_id=? and (title like ? or content like ?)", 
				language.getId(), "%" + query + "%", "%" + query + "%")
				.limit(20)
				.orderBy("updated desc").getResultList();
		//System.out.println(JSONSerializer.serialize(snippets));
		return new Json(this.snippets);
	}
}