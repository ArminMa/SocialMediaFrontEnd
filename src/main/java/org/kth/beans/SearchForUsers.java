package org.kth.beans;

import org.kth.handlers.UserHandler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Collection;

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

    public Collection<UserBean> SearchForUsersByName(){
        return UserHandler.searchForUsersByUserName(searchString);
    }

    public String testSearchForUsersByName(){
        Collection<UserBean> users = UserHandler.searchForUsersByUserName(searchString);
        if(users == null)
            return "test failed: null";
        if(users.isEmpty())
            return "found no occurences";
        return "success";
    }
}
