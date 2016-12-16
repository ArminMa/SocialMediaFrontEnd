package org.kth.controller.beans;

import java.io.Serializable;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.PostPojo;

import javax.faces.bean.ViewScoped;

@javax.faces.bean.ManagedBean
@ViewScoped
public class WriteLogBean implements Serializable {
    private String content = "";
    private String successMessage = "";

    public WriteLogBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public void writePost(){
        if (content == ""){
            successMessage = "you need to enter a message to post";
            return;
        }
       successMessage = UserHandler.sendMailMessage(new PostPojo(content));
    }
}
