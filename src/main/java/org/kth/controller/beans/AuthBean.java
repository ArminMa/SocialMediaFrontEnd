package org.kth.controller.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.kth.model.pojo.TokenPojo;
import org.kth.model.pojo.UserPojo;

@ManagedBean
@SessionScoped
public class AuthBean {
	private UserPojo userPojo;

	public AuthBean() {}

	public AuthBean(UserPojo user) {
		this.userPojo= user;
	}


	public UserPojo getUserPojo() {
		return userPojo;
	}

	public void setUserPojo(UserPojo userPojo) {
		this.userPojo = userPojo;
	}

	public boolean authUser() {
		return userPojo != null &&
				userPojo.getToken() != null &&
				userPojo.getToken().getToken() != null &&
				userPojo.getUsername() != null;
	}

}
