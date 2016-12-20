package org.kth.controller.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlRootElement;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;


@ManagedBean
@ViewScoped
@XmlRootElement
public class RegisterBean implements Serializable {

    private UserPojo userPojo;
    private String userName;
    private String email;
    private String password;

    public RegisterBean() {
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

    public String registerUser() {
        userPojo = new UserPojo(userName, email, password);
        return UserHandler.registerNewUser(userPojo);
    }
}
