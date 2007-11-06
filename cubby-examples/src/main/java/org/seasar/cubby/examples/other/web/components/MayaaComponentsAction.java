package org.seasar.cubby.examples.other.web.components;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;

@Url("mayaa")
public class MayaaComponentsAction extends Action {

	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]

	public UserForm form = new UserForm();

	public List<Hobby> hobbies;

	public List<Color> colors;

	// ----------------------------------------------[Action Method]

	@Override
	public void prerender() {
		super.prerender();
		hobbies = getHobbies();
		colors = getColors();
	}
	
	@Form("form")
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