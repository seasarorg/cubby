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
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Path("components")
public class ArrayAction extends Action {

	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]

	public final ValidationRules validation = new DefaultValidationRules("color.") {
		public void initialize() {
			add("name", new RequiredValidator());
			add("value", new RequiredValidator());
		}
	};
	
	@RequestParameter
	public String name[];

	@RequestParameter
	public String value[];

	@RequestParameter
	public String[] check;

	@RequestParameter
	public int[] intValues;

	// ----------------------------------------------[Action Method]

	@Override
	public void prerender() {
		super.prerender();
	}
	
	@Path("array")
	public ActionResult array() {
		List<Color> colors = getColors();
		this.name = new String[colors.size()];
		this.value = new String[colors.size()];
		for (int i = 0; i < colors.size(); i++) {
			Color color = colors.get(i);
			this.name[i] = color.getName();
			this.value[i] = color.getValue();
		}
		return new Forward("array.jsp");
	}
	
	@Path("array_save")
	@Validation(rules="validation", errorPage="array.jsp")
	public ActionResult array_save() {
		System.out.println(check);
		return new Forward("array.jsp");
	}

	// ----------------------------------------------[Helper Method]
	
	private List<Color> getColors() {
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
		//return colors.toArray(new Color[0]);
	}

}
