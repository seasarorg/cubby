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

	private EntityManager entityManager;

	public void init(FilterConfig config) throws ServletException {
		final Injector injector = InjectorFactory.getInjector();
		this.entityManager = injector.getInstance(EntityManager.class);
	}

	public void destroy() {
		if (entityManager != null) {
			entityManager.close();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			chain.doFilter(request, response);
			transaction.commit();
		} catch (Throwable e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			if (e instanceof Error) {
				throw (Error) e;
			}
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
		}
	}
}
