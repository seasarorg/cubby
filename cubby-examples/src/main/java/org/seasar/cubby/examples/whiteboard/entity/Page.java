package org.seasar.cubby.examples.whiteboard.entity;

import java.io.Serializable;

import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

public class Page implements Serializable {

	private static final long serialVersionUID = 8110985412551946878L;

	private Integer id;

	private byte[] image;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id(IdType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImage() {
		return this.image;
	}

}
