package org.seasar.cubby.plugins.guice.spi;

import org.seasar.cubby.spi.JsonProvider;

import com.google.gson.Gson;

public class GsonJsonProvider implements JsonProvider {

	public String toJson(Object o) {
		final Gson gson = new Gson();
		return gson.toJson(o);
	}

}
