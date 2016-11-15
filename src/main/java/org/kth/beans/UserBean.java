package org.kth.beans;


import org.kth.handlers.UserHandler;
import org.kth.pojos.*;

import java.util.ArrayList;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.bind.annotation.XmlRootElement;

@ManagedBean
@SessionScoped
@XmlRootElement
public class UserBean {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private byte[] picture; // Todo implement this Armin. low prio
    private Collection<FriendRequestPojo> friendRequests = new ArrayList<>();
    private Collection<MailMessagePojo> mailMessages = new ArrayList<>();
    private Collection<UserPojo> friends = new ArrayList<>();
    private Collection<PostPojo> log = new ArrayList<>();
    private Collection<ChatMessagePojo> chatMessages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Collection<FriendRequestPojo> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Collection<FriendRequestPojo> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public Collection<MailMessagePojo> getMailMessages() {
        return mailMessages;
    }

    public void setMailMessages(Collection<MailMessagePojo> mailMessages) {
        this.mailMessages = mailMessages;
    }

    public Collection<UserPojo> getFriends() {
        return friends;
    }

    public void setFriends(Collection<UserPojo> friends) {
        this.friends = friends;
    }

    public Collection<PostPojo> getLog() {
        return log;
    }

    public void setLog(Collection<PostPojo> log) {
        this.log = log;
    }

    public Collection<ChatMessagePojo> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(Collection<ChatMessagePojo> chatMessages) {
        this.chatMessages = chatMessages;
    }

    //constructor
    public UserBean() {
        userName="enter username";
    }

    //controller methods
    public String doLogin() {
        System.out.println("found doLogin");
        if(UserHandler.login(userName, password))
            return "search";

        return "index";
    }
}