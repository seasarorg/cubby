/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.plugins.oval.validation;

public class OvalValidationContext {

	private static final ThreadLocal<OvalValidationContext> context = new ThreadLocal<OvalValidationContext>() {

		@Override
		protected OvalValidationContext initialValue() {
			return new OvalValidationContext();
		}

	};

	private String resourceKeyPrefix;

	private OvalValidationContext() {
	}

	public static OvalValidationContext get() {
		return context.get();
	}

	public static void remove() {
		context.remove();
	}

	public String getResourceKeyPrefix() {
		return resourceKeyPrefix;
	}

	public void setResourceKeyPrefix(String resourceKeyPrefix) {
		this.resourceKeyPrefix = resourceKeyPrefix;
	}

}
