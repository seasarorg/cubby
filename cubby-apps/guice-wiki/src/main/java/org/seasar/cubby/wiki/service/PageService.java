package org.seasar.cubby.wiki.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.seasar.cubby.wiki.entity.Page;

import com.google.inject.Inject;


public class PageService {

	@Inject
	private EntityManager entityManager;
	
	public Page getPageById(Integer id) {
		return entityManager.find(Page.class, id);
	}

	public Page getPageByName(String name) {
		try {
			return (Page) entityManager
			.createQuery("SELECT p FROM Page p where p.name = :name")
			.setParameter("name", name)
			.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public void save(Page page) {
		if (page.getId() == null) {
			entityManager.persist(page);
		} else {
			entityManager.merge(page);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Page> getPages() {
		return (List<Page>) entityManager
		.createQuery("SELECT p FROM Page p order by p.name")
		.getResultList();
	}
}
