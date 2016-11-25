package org.kth.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlPanelGroup;

import org.kth.handlers.UserHandler;

@ManagedBean
@SessionScoped
public class SearchForUsers {
	private String searchString = "";
	private List<UserBean> users =  new ArrayList<>();

	public SearchForUsers() {
	}

	public String getSearchString() {
		return searchString;
	}

	public List<UserBean> getUsers() {
		return users;
	}

	public void setUsers(List<UserBean> users) {
		this.users = users;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void searchForUsersByName(){
		users.clear();
		List<UserBean> newUsers = UserHandler.getUserNamesByName(searchString);
		if(newUsers != null) {
			users.addAll(newUsers);
		}
	}

}
