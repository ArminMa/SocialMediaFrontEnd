package org.kth.controller.beans;

import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.MailMessagePojo;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ViewPersonalMessages {
    private List<MailMessagePojo> messages;

    public ViewPersonalMessages() {
        messages = new ArrayList<>();
    }

    public List<MailMessagePojo> getMessages() {
        return messages;
    }

    public void setMessages(List<MailMessagePojo> messages) {
        this.messages = messages;
    }

    public void getPersonalMessages(){
        messages = UserHandler.getPersonalMessages();
    }

    @PostConstruct
    public void init(){
       getPersonalMessages();
    }
}
