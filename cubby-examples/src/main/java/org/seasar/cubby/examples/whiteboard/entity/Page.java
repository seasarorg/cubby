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
