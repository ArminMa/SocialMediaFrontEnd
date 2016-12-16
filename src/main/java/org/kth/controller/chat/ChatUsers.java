package org.kth.controller.chat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;

@ManagedBean
@SessionScoped
public class ChatUsers implements Serializable {

	private List<String> users;
	private List<UserPojo> allUsers;

	@PostConstruct
	public void init() {
		users = new ArrayList<String>();
		//this shuld be changed to get this users friend
		allUsers = UserHandler.getAllUsers();
		for(UserPojo U : allUsers){
			users.add(U.getUsername());
		}
	}

	public List<String> getUsers() {

		return users;
	}

	public void remove(String user) {
		this.users.remove(user);
	}

	public void add(String user) {
		this.users.add(user);
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<UserPojo> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<UserPojo> allUsers) {
		this.allUsers = allUsers;
	}

	public boolean contains(String user) {
		return this.users.contains(user);
	}
}