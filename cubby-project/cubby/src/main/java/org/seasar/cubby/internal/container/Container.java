package org.seasar.cubby.internal.container;

public interface Container {

	<T> T lookup(Class<T> type) throws LookupException;

}
