package org.seasar.cubby.plugins.s2;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.framework.util.JSONSerializer;

public class S2JsonPlugin extends AbstractPlugin {

	private JsonProvider jsonProvider = new S2JsonProvider();

	public <S extends Provider> S getProvider(Class<S> service) {
		if (JsonProvider.class.equals(service)) {
			return service.cast(jsonProvider);
		}
		return null;
	}

	static class S2JsonProvider implements JsonProvider {

		public String toJson(Object o) {
			return JSONSerializer.serialize(o);
		}

	}

}