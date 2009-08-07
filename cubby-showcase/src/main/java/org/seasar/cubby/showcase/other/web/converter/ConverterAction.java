/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.cubby.showcase.other.web.converter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.SendError;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class ConverterAction extends Action {

	public BookDao bookDao;

	@RequestParameter
	public Book book;

	public List<Book> books;

	@Override
	public void prerender() {
		this.books = bookDao.findAll();
	}

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	public ValidationRules detailValidationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			add(RESOURCE, new ValidationRule() {

				public void apply(Map<String, Object[]> params, Object form,
						ActionErrors errors) throws ValidationException {
					// バリデーション実行前に
					// ・コンバータによる String から Book への変換
					// ・アクションのプロパティへのバインド
					// の実行は終わっているので、
					// この時点でプロパティを検査して null の場合は
					// クライアントへ 404 NOT FOUND を返す。
					ConverterAction action = ConverterAction.class.cast(form);
					if (action.book == null) {
						throw new ValidationException(new SendError(
								HttpServletResponse.SC_NOT_FOUND));
					}
				}

			});
		}

	};

	@Path("{book,...-..........}")
	@Validation(rules = "detailValidationRules")
	public ActionResult detail() {
		return new Forward("index.jsp");
	}

	public ValidationRules inputValidationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			add("book", new RequiredValidator());
			add(DATA_CONSTRAINT, new ValidationRule() {

				public void apply(Map<String, Object[]> params, Object form,
						ActionErrors errors) throws ValidationException {
					ConverterAction action = ConverterAction.class.cast(form);
					if (action.book == null) {
						throw new ValidationException("no such book", "book");
					}
				}

			});
		}

	};

	@Validation(rules = "inputValidationRules", errorPage = "index.jsp")
	public ActionResult input() {
		return new Forward("index.jsp");
	}

}
