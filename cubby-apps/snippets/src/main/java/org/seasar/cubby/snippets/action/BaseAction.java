package org.seasar.cubby.snippets.action;

import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.snippets.entity.Language;
import org.seasar.extension.jdbc.JdbcManager;

public class BaseAction extends Action {
	
	protected JdbcManager db;
	private List<Language> languages;

	public void setJdbcManager(JdbcManager jdbcManager) {
		this.db = jdbcManager;
	}
	
	
	public List<Language> getLanguages() {
		if (languages == null) {
			this.languages = db.from(Language.class).getResultList();
		}
		return languages;
	}
}
