package org.kth.controller.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlRootElement;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;


@ManagedBean
@ViewScoped
@XmlRootElement
public class registerBean {

    private UserPojo userBean;
    private String username;
    private String email;
    private String password;

    public registerBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        userBean = new UserPojo(userName, email, password);

        return UserHandler.registerNewUser(userBean);
    }
}
