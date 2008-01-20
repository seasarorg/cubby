/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.cubby.dxo;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.action.impl.FormatPatternImpl;
import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.annotation.AnnotationReaderFactory;
import org.seasar.framework.container.S2Container;

public class CubbyAnnotationReaderFactoryImpl implements
		AnnotationReaderFactory {

	private AnnotationReaderFactory annotationReaderFactory;

	private final FormatPattern formatPattern;

	public CubbyAnnotationReaderFactoryImpl(final S2Container container) {
		if (container.getRoot().hasComponentDef(FormatPattern.class)) {
			this.formatPattern = (FormatPattern) container.getRoot().getComponent(FormatPattern.class);
		} else {
			this.formatPattern = new FormatPatternImpl();
		}
	}

	public void setAnnotationReaderFactory(final AnnotationReaderFactory annotationReaderFactory) {
		this.annotationReaderFactory = annotationReaderFactory;
	}

	public AnnotationReader getAnnotationReader() {
		final AnnotationReader annotationReader = annotationReaderFactory
				.getAnnotationReader();
		return new CubbyAnnotationReaderWrapper(annotationReader, formatPattern);
	}

}
