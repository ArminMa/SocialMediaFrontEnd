package org.kth.controller.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
public class MenuBean implements Serializable {

    private static final long serialVersionUID = -5602018325682326508L;

    public String logout(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) context.getRequest();
        HttpSession session = req.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "logout";
    }
}
