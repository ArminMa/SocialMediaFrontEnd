package org.kth.controller.beans;

import org.kth.controller.handlers.CookieManager;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    UserPojo userPojo;

    public LoginBean() {
        userPojo = new UserPojo();
    }

    public UserPojo getUserPojo() {
        return userPojo;
    }

    public void setUserPojo(UserPojo userPojo) {
        this.userPojo = userPojo;
    }

    //controller methods
    public String doLogin() {
        System.out.println("found doLogin");
        boolean loginOK = false;
        if (userPojo != null){
            if(userPojo.getUsername() != null && userPojo.getPassword() != null ){
                if(loginOK = UserHandler.login(userPojo))
                    CookieManager.addUserName(userPojo.getUsername());
                return "viewPersonalMessages?faces-redirect=true";
            }
        }

        return "index?faces-redirect=true";
    }
}
