package org.seasar.cubby.examples.other.web.components;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.ArrayValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Url("components")
public class ArrayAction extends Action {

	// ----------------------------------------------[DI Filed]

	public final ValidationRules validation = new DefaultValidationRules("color.") {
		public void initialize() {
			add("name", new ArrayValidator(new RequiredValidator()));
			add("value", new ArrayValidator(new RequiredValidator()));
		}
	};
	
	// ----------------------------------------------[Attribute]

	public String name[];

	public String value[];

	public String[] check;
	
	// ----------------------------------------------[Action Method]

	@Override
	public void prerender() {
		super.prerender();
	}
	
	@Form()
	@Url("array")
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
	
	@Form()
	@Url("array_save")
	@Validation(rulesField="validation", errorPage="array.jsp")
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
