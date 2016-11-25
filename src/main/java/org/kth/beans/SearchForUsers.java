package org.kth.beans;

import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.kth.handlers.UserHandler;

@ManagedBean
@SessionScoped
public class SearchForUsers {
	private String searchString = "";

	public SearchForUsers() {
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Collection<UserBean> searchForUsersByName(){
		return UserHandler.getUserNamesByName(searchString);
	}

	public String testSearchForUsersByName(){
		Collection<UserBean> users = UserHandler.getUserNamesByName(searchString);
		if(users == null)
			return "test failed: null";
		if(users.isEmpty())
			return "found no occurences";
		return "success";
	}
}
