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
package org.seasar.cubby.examples.other.web.components;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.DateFormatValidator;
import org.seasar.cubby.validator.validators.NumberValidator;
import org.seasar.cubby.validator.validators.RangeValidator;

@Path("mayaa")
public class MayaaComponentsAction extends Action {

	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]

	public ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("date", new DateFormatValidator("yyyy-MM-dd"));
			add("intValue", new NumberValidator(), new RangeValidator(1, 10));
		}
	};

	public FormDto formDto;

	public List<Hobby> hobbies;

	public List<Color> colors;

	// ----------------------------------------------[Action Method]

	@Override
	public void prerender() {
		super.prerender();
		hobbies = getHobbies();
		colors = getColors();
	}

	@Form(value = "formDto")
	@Validation(rules = "validation", errorPage = "components.html")
	public ActionResult components() {
		return new Forward("components.html");
	}

	// ----------------------------------------------[Helper Method]

	private java.util.List<Hobby> getHobbies() {
		Hobby h1 = new Hobby();
		h1.name = "料理";
		h1.value = "1";
		Hobby h2 = new Hobby();
		h2.name = "スポーツ";
		h2.value = "2";
		Hobby h3 = new Hobby();
		h3.name = "パソコン";
		h3.value = "3";
		List<Hobby> hobbies = new ArrayList<Hobby>();
		hobbies.add(h1);
		hobbies.add(h2);
		hobbies.add(h3);
		return hobbies;
	}

	private java.util.List<Color> getColors() {
		Color c1 = new Color();
		c1.name = "赤";
		c1.value = "red";
		Color c2 = new Color();
		c2.name = "青";
		c2.value = "blue";
		Color c3 = new Color();
		c3.name = "緑";
		c3.value = "green";
		List<Color> colors = new ArrayList<Color>();
		colors.add(c1);
		colors.add(c2);
		colors.add(c3);
		return colors;
	}
}
