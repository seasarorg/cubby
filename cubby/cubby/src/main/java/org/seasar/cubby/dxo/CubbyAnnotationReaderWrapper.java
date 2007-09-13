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

import java.lang.reflect.Method;
import java.util.Map;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.framework.util.StringUtil;

public class CubbyAnnotationReaderWrapper implements AnnotationReader {

	private final AnnotationReader annotationReader;

	private final FormatPattern formatPattern;

	public CubbyAnnotationReaderWrapper(AnnotationReader annotationReader, FormatPattern formatPattern) {
		this.annotationReader = annotationReader;
		this.formatPattern = formatPattern;
	}

	public String getDatePattern(Class dxoClass, Method method) {
		String datePattern = annotationReader.getDatePattern(dxoClass, method);
		if (StringUtil.isEmpty(datePattern) && formatPattern != null) {
			datePattern = formatPattern.getDatePattern();
		}
		return datePattern;
	}

	public String getTimePattern(Class dxoClass, Method method) {
		String timePattern = annotationReader.getTimePattern(dxoClass, method);
		if (StringUtil.isEmpty(timePattern) && formatPattern != null) {
			timePattern = formatPattern.getTimePattern();
		}
		return timePattern;
	}

	public String getTimestampPattern(Class dxoClass, Method method) {
		String timestampPattern = annotationReader.getTimestampPattern(dxoClass, method);
		if (StringUtil.isEmpty(timestampPattern) && formatPattern != null) {
			timestampPattern = formatPattern.getTimestampPattern();
		}
		return timestampPattern;
	}

	public String getConversionRule(Class dxoClass, Method method) {
		return annotationReader.getConversionRule(dxoClass, method);
	}

	public boolean isExcludeNull(Class dxoClass, Method method) {
		return annotationReader.isExcludeNull(dxoClass, method);
	}

	public Map getConverters(Class destClass) {
		return annotationReader.getConverters(destClass);
	}

	public String getSourcePrefix(Class dxoClass, Method method) {
		return annotationReader.getSourcePrefix(dxoClass, method);
	}

}
