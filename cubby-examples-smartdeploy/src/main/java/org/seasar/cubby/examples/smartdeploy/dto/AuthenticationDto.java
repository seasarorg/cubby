package org.seasar.cubby.examples.smartdeploy.dto;

import java.io.Serializable;

import org.seasar.cubby.examples.smartdeploy.entity.User;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class AuthenticationDto implements Serializable {

	private static final long serialVersionUID = -1814425408935759640L;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
