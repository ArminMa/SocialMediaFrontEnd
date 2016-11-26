package org.kth.controller.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class SearchForUsers {
	public static final Logger logger1 = LoggerFactory.getLogger( UserHandler.class );
	private String searchstring = "";
	private List<UserPojo> users =  new ArrayList<>();
	private String searchOption = "";
	private List<UserPojo> userBeansList =  new ArrayList<>();

	public SearchForUsers() {
	}

	public String getSearchstring() {
		return searchstring;
	}
	public void setSearchstring(String searchstring) {
		this.searchstring = searchstring;
	}

	public List<UserPojo> getUsers() {
		return users;
	}

	public void setUsers(List<UserPojo> users) {
		this.users = users;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public List<UserPojo> getUserBeansList() {
		return userBeansList;
	}

	public void setUserBeansList(List<UserPojo> userBeansList) {
		this.userBeansList = userBeansList;
	}

	public void searchForUsers(){
		logger1.info("inside searchForUsers");
		userBeansList.clear();
		logger1.info("after clear");
		if(getSearchOption().equals("1UserName")){
			List<UserPojo> newUsers = UserHandler.getUserNamesByName(searchstring);
			if(newUsers != null) {
				userBeansList.addAll(newUsers);
			}
		}else if(getSearchOption().equals("2Email")){
			UserPojo newUsers = UserHandler.getUserByEmail(searchstring);
			if(newUsers != null) {
				userBeansList.add(newUsers);
			}
		}
	}

}