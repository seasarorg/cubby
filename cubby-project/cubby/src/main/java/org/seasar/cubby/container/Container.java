package org.seasar.cubby.container;

public interface Container {

	<T> T lookup(Class<T> type) throws LookupException;

}
