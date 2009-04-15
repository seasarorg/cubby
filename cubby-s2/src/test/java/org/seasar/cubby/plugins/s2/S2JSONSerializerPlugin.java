package org.seasar.cubby.plugins.s2;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.framework.util.JSONSerializer;

/**
 * {@link JSONSerializer} にって {@link JsonProvider} を提供するプラグインのサンプルです。
 * 
 * @author baba
 */
@SuppressWarnings("deprecation")
public class S2JSONSerializerPlugin extends AbstractPlugin {

	private JsonProvider jsonProvider = new JsonProvider() {

		public String toJson(Object o) {
			return JSONSerializer.serialize(o);
		}

	};

	public <S extends Provider> S getProvider(Class<S> service) {
		if (JsonProvider.class.equals(service)) {
			return service.cast(jsonProvider);
		}
		return null;
	}

}
