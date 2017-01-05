package org.kth.controller.beans.chat;

import javax.annotation.PostConstruct;

/**
 * Created by Sys on 2017-01-02.
 */
public class message {

	private String message;
	private String username;
	private String user2Name;

	public message() {
	}

	public message(String username, String message ) {
		this.message = message;
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUser2Name() {
		return user2Name;
	}

	public void setUser2Name(String user2Name) {
		this.user2Name = user2Name;
	}
}
