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
package org.seasar.cubby.internal.routing.impl;

import static org.seasar.cubby.internal.util.LogMessages.format;

import org.seasar.cubby.internal.routing.PathTemplateException;
import org.seasar.cubby.internal.routing.PathTemplateParser;

/**
 * パステンプレートのパーサーの実装です。
 * 
 * @author baba
 * @since 1.1.1
 */
public class PathTemplateParserImpl implements PathTemplateParser {

	/** プレースホルダの開始文字。 */
	private static final char OPEN_PLACE_HOLDER = '{';

	/** プレースホルダの終了文字。 */
	private static final char CLOSE_PLACE_HOLDER = '}';

	/** プレースホルダ中のパラメータ名と正規表現の区切り文字。 */
	private static final char PLACE_HOLDER_SEPARATOR = ',';

	/** 正規表現のエスケープ文字。 */
	private static final char REGEXP_ESCAPE = '\\';

	/**
	 * パース中の状態。
	 * 
	 * @author baba
	 * @since 1.1.1
	 */
	private enum State {
		NORMAL, PARAM_NAME, PARAM_REGEX, PARAM_REGEX_ESCAPE;
	}

	/**
	 * {@inheritDoc}
	 */
	public String parse(final String template, final Handler handler) {
		final StringBuilder pathRegex = new StringBuilder(100);
		final StringBuilder paramName = new StringBuilder(10);
		final StringBuilder paramRegex = new StringBuilder(10);

		State state = State.NORMAL;
		int braceDepth = 0;

		for (int i = 0; i < template.length(); i++) {
			final char c = template.charAt(i);
			switch (state) {
			case NORMAL: {
				if (c == OPEN_PLACE_HOLDER) {
					state = State.PARAM_NAME;
				} else {
					pathRegex.append(c);
				}
				break;
			}
			case PARAM_NAME: {
				if (c == CLOSE_PLACE_HOLDER) {
					if (paramName.length() == 0) {
						throw new PathTemplateException(format("ECUB0108",
								template, i));
					}
					final String replacement = handler.handle(paramName
							.toString(), DEFAULT_URI_PARAMETER_REGEX);
					pathRegex.append(replacement);

					paramName.setLength(0);
					state = State.NORMAL;
				} else if (c == PLACE_HOLDER_SEPARATOR) {
					state = State.PARAM_REGEX;
				} else {
					paramName.append(c);
				}
				break;
			}
			case PARAM_REGEX: {
				if (c == REGEXP_ESCAPE) {
					paramRegex.append(c);
					state = State.PARAM_REGEX_ESCAPE;
				} else if (c == CLOSE_PLACE_HOLDER && braceDepth == 0) {
					if (paramName.length() == 0) {
						throw new PathTemplateException(format("ECUB0108",
								template, i));
					}
					if (paramRegex.length() == 0) {
						throw new PathTemplateException(format("ECUB0109",
								template, i));
					}
					final String replacement = handler.handle(paramName
							.toString(), paramRegex.toString());
					pathRegex.append(replacement);

					paramName.setLength(0);
					paramRegex.setLength(0);
					braceDepth = 0;
					state = State.NORMAL;
				} else {
					if (c == OPEN_PLACE_HOLDER) {
						braceDepth++;
					} else if (c == CLOSE_PLACE_HOLDER) {
						braceDepth--;
					}
					paramRegex.append(c);
				}
				break;
			}
			case PARAM_REGEX_ESCAPE: {
				paramRegex.append(c);
				state = State.PARAM_REGEX;
				break;
			}
			default:
				throw new IllegalStateException();
			}
		}
		if (state != State.NORMAL) {
			throw new PathTemplateException(format("ECUB0107", template));
		}
		return pathRegex.toString();
	}

}
