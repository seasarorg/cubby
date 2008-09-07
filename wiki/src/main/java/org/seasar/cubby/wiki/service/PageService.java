package org.seasar.cubby.wiki.service;

import java.util.List;

import org.seasar.cubby.wiki.entity.Page;
import org.seasar.extension.jdbc.JdbcManager;


public class PageService {

	public JdbcManager jdbcManager;
	
	public Page getPageById(Integer id) {
		return jdbcManager.from(Page.class).id(id).getSingleResult();
	}

	public Page getPageByName(String name) {
		return jdbcManager.from(Page.class).where("name=?", name).getSingleResult();
	}

	public void save(Page page) {
		if (page.getId() == null) {
			jdbcManager.insert(page).excludesNull().execute();
		} else {
			jdbcManager.update(page).excludesNull().execute();
		}
	}

	public List<Page> getPages() {
		return jdbcManager.from(Page.class).orderBy("name").getResultList();
	}
}
