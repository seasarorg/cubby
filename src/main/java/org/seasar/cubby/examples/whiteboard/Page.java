package org.seasar.cubby.examples.whiteboard;

public class Page {

	private final String id;
	private byte[] image;

	public Page(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImage() {
		return this.image;
	}

}
