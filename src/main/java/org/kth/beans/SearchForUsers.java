package org.kth.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.kth.handlers.UserHandler;
import org.kth.model.pojos.User.UserPojo;

@ManagedBean
@SessionScoped
public class SearchForUsers {
	private String searchString = "";
	private List<UserPojo> users =  new ArrayList<>();

	public SearchForUsers() {
	}

	public String getSearchString() {
		return searchString;
	}

	public List<UserPojo> getUsers() {
		return users;
	}

	public void setUsers(List<UserPojo> users) {
		this.users = users;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void searchForUsersByName(){
		users.clear();
		List<UserPojo> newUsers = UserHandler.getUserNamesByName(searchString);
		if(newUsers != null) {
			users.addAll(newUsers);
		}
	}

}
