package org.seasar.cubby.wiki.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.cubby.plugins.guice.InjectorFactory;

import com.google.inject.Injector;

public class TransactionFilter implements Filter {

	private Injector injector;

	public void init(FilterConfig config) throws ServletException {
		injector = InjectorFactory.getInjector();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		boolean commit = false;
		try {
			entityManager = injector.getInstance(EntityManager.class);
			transaction = entityManager.getTransaction();
			transaction.begin();
			chain.doFilter(request, response);
			transaction.commit();
			commit = true;
		} finally {
			if (transaction != null && transaction.isActive() && !commit) {
				transaction.rollback();
			}
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}
}
