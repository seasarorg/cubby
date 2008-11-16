package org.seasar.cubby.wiki.service;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.wiki.entity.Page;


public class PageService {

//	public JdbcManager jdbcManager;
	
	public Page getPageById(Integer id) {
//		return jdbcManager.from(Page.class).id(id).getSingleResult();
		return new Page();
	}

	public Page getPageByName(String name) {
//		return jdbcManager.from(Page.class).where("name=?", name).getSingleResult();
		return new Page();
	}

	public void save(Page page) {
//		if (page.getId() == null) {
//			jdbcManager.insert(page).excludesNull().execute();
//		} else {
//			jdbcManager.update(page).excludesNull().execute();
//		}
	}

	public List<Page> getPages() {
//		return jdbcManager.from(Page.class).orderBy("name").getResultList();
		return new ArrayList<Page>();
	}
}
