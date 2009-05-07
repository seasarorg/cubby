package org.seasar.cubby.exmaple.gae.dto;

import java.io.Serializable;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class SearchDto implements Serializable {

	private static final long serialVersionUID = -7454912072275212820L;

	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

}
