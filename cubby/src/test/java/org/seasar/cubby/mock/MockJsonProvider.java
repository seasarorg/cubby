package org.seasar.cubby.mock;

import org.seasar.cubby.internal.spi.JsonProvider;

public class MockJsonProvider implements JsonProvider {

	public static final String JSON_STRING = "**JSON**";

	public String toJson(Object o) {
		return JSON_STRING;
	}

}
