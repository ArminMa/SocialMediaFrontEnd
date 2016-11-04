package org.kth.beans;


import org.kth.handlers.UserHandler;

import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean
@SessionScoped
public class UserBean {
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    private String userName;
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    private String passWord;
    public UserBean() {
        userName="enter username";
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }


    //controller methods
    public String doLogin() {
        System.out.println("found doLogin");
        if(UserHandler.login(userName, passWord))
            return "search";

        return "index";
    }
    public Collection getNames() {
        return UserHandler.getUserNamesByName(userName);
    }

    public String getUserByEmail(String email){
        UserBean userBean = UserHandler.getUserByEmail(email);
        if(userBean.getEmail().equals(email))
            return "search";

        return "index";
    }
}