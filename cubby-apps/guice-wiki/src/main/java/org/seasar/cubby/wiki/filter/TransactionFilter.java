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

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;

public class TransactionFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		EntityManager entityManager = container.lookup(EntityManager.class);
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
