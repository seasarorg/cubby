package org.seasar.cubby.wiki;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class JpaModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {
			public EntityManager get() {
				EntityManagerFactory factory = Persistence
						.createEntityManagerFactory("guice-wiki");
				EntityManager manager = factory.createEntityManager();
				manager.setFlushMode(FlushModeType.COMMIT);
				return manager;
			}

		}).in(Singleton.class);
	}

}
