package org.kth.controller.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.kth.controller.handlers.CookieManager;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @Size(min=1, max=50)
    @NotEmpty
    private String username;

    @Size(min=1, max=50)
    @NotEmpty
    private String password;


    public void setUsername(String name) {
        this.username = name;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public LoginBean() {

    }

    //controller methods
    public String doLogin() {


        boolean loginOK = false;
        if(loginOK = UserHandler.login(new UserPojo(username, null, password))) {
            CookieManager.addUserName(username);
            return "viewPersonalMessages?faces-redirect=true";
        }


        return "index?faces-redirect=true";
    }

    public void forgotPassword() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "don't forget your UserName");
        FacesContext.getCurrentInstance().addMessage("loginForm:username", msg);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "don't forget your password");
        FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
    }

    public void login() {
        if ("BootsFaces".equalsIgnoreCase(username) && "rocks!".equalsIgnoreCase(password)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Congratulations! You've successfully logged in.");
            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "That's the wrong password.");
            FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
        }
    }
}
