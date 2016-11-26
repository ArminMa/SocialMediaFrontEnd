package org.kth.controller.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlRootElement;
import org.kth.controller.handlers.UserHandler;


@ManagedBean
@ViewScoped
@XmlRootElement
public class registerBean {

    private UserBean userBean;
    private String userName;
    private String email;
    private String password;

    public registerBean() {
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String registerUser(String userName , String email, String password) {
        userBean = new UserBean(userName, email, password);

        return UserHandler.registerNewUser(userBean);
    }
}
