package org.seasar.cubby.examples.components;

import java.util.ArrayList;
import java.util.List;

public class UserForm {
	public String userName;
	public String color;
	private List<String> hobbies = new ArrayList<String>();
	private List<String> hobbies2 = new ArrayList<String>();
	private String hobbies3;
	private String hobbies4;
	private String confirm;
	private String memo;
	private Boolean check1;
	private Integer[] check2;
	public Boolean getCheck1() {
		return check1;
	}
	public void setCheck1(Boolean check1) {
		this.check1 = check1;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
	public void addHobbies(String[] hobbies) {
		if (hobbies != null) {
			for (String hobby : hobbies) {
				this.hobbies.add(hobby);
			}
		}
	}
	public List<String> getHobbies2() {
		return hobbies2;
	}
	public void setHobbies2(List<String> hobbies2) {
		this.hobbies2 = hobbies2;
	}
	public void addHobbies2(String[] hobbies2) {
		if (hobbies2 != null) {
			for (String hobby : hobbies2) {
				this.hobbies2.add(hobby);
			}
		}
	}
	public String getHobbies3() {
		return hobbies3;
	}
	public void setHobbies3(String hobbies3) {
		this.hobbies3 = hobbies3;
	}
	public String getHobbies4() {
		return hobbies4;
	}
	public void setHobbies4(String hobbies4) {
		this.hobbies4 = hobbies4;
	}
	public Integer[] getCheck2() {
		return check2;
	}
	public void setCheck2(Integer[] check2) {
		this.check2 = check2;
	}
	
}
