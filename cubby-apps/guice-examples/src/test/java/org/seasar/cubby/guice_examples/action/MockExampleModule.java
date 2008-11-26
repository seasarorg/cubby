package org.seasar.cubby.guice_examples.action;

import org.seasar.cubby.guice_examples.ExampleModule;
import org.seasar.cubby.plugins.guice.unit.MockServletModule;

public class MockExampleModule extends ExampleModule {

	@Override
	protected void setUpServletModule() {
		install(new MockServletModule());
	}
}
