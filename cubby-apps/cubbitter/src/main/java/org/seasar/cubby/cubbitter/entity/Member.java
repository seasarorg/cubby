package org.seasar.cubby.cubbitter.entity;

/**
 * 個人情報Entityクラス
 */

import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

public class Member implements java.io.Serializable {

	private static final long serialVersionUID = 4901987744625643911L;

	private int memberId;
	private String memberName;
	private String fullName;
	private String password;
	private boolean open;
	private String email;
	private String locale;
	private String web;
	private String biography;
	private String location;

	@Id(value = IdType.SEQUENCE, sequenceName = "member_member_id_seq")
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return memberId + "," + memberName + "," + fullName + "," + open + ","
				+ email + "," + locale + "," + web + "," + biography + ","
				+ location;
	}
}
