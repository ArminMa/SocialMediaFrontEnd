package org.kth.controller.beans;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.MailMessagePojo;
import org.kth.model.pojo.UserPojo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ViewPersonalMessages implements Serializable {
    private MailMessagePojo messagePojo;
    private String message = "";
    private String topic = "";
    private String resultPost = "";

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

    public MailMessagePojo getMessagePojo() {
        return messagePojo;
    }

    public void setMessagePojo(MailMessagePojo messagePojo) {
        this.messagePojo = messagePojo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getResultPost() {
        return resultPost;
    }

    public void setResultPost(String resultPost) {
        this.resultPost = resultPost;
    }

    @PostConstruct
    public void init(){
       getPersonalMessages();
    }

    public void sendMessage(UserPojo user){
        if(user == null || message == "" || topic == ""){
            resultPost = "Message cannot be sent. Require a receiver a message and a topic";
        }
        resultPost = UserHandler.sendMailMessage(new MailMessagePojo(message, topic, user));
    }
}
