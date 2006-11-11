package org.seasar.cubby.examples.components;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.controller.Controller;

@Url("")
public class ComponentsController extends Controller {

	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]

	public UserForm form = new UserForm();
	public List<Hobby> hobbies;
	public List<Color> colors;

	// ----------------------------------------------[Action Method]

	@Override
	public void initalize() {
		hobbies = getHobbies();
		colors = getColors();
	}
	
	@Url("components")
	@Form("form")
	public String show() {
		return "/components.jsp";
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
