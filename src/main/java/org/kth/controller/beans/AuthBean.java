package org.kth.controller.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.TokenPojo;
import org.kth.model.pojo.UserPojo;

@ManagedBean
@SessionScoped
public class AuthBean {
	private UserPojo userPojo;

	public AuthBean() {
	}



	public UserPojo getUserPojo() {
		return userPojo;
	}

	public void setUserPojo(UserPojo userPojo) {
		this.userPojo = userPojo;
	}

	@PostConstruct
	public void authUser() {
		if( userPojo == null ){
			this.userPojo = UserHandler.getUserFromToken();
		}
	}



}
