package org.kth.beans;


import org.kth.handlers.UserHandler;

import java.util.ArrayList;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.bind.annotation.XmlRootElement;
import org.kth.pojos.User.UserPojo;
import org.kth.pojos.chatMessage.ChatMessagePojo;
import org.kth.pojos.friendRequest.FriendRequestPojo;
import org.kth.pojos.mailMessage.MailMessagePojo;
import org.kth.pojos.post.PostPojo;

@ManagedBean
@SessionScoped
@XmlRootElement
public class UserBean {

    private Long id;
    private String username;
    private String email;
    private String password;
    private byte[] picture; // Todo implement this Armin. low prio
    private Collection<FriendRequestPojo> friendRequests = new ArrayList<>();
    private Collection<MailMessagePojo> mailMessages = new ArrayList<>();
    private Collection<UserPojo> friends = new ArrayList<>();
    private Collection<PostPojo> log = new ArrayList<>();
    private Collection<ChatMessagePojo> chatMessages = new ArrayList<>();

    public UserBean(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        username ="enter username";
    }

    //controller methods
    public String doLogin() {
        System.out.println("found doLogin");
        if(UserHandler.login(this))
            return "search";

        return "index";
    }

    public String getUserByEmail(String email){
//        if("Hello  World".equals(UserHandler.testConnection())){
//            return "search";
//        }
//        return "index";
        UserBean userBean = UserHandler.getUserByEmail(email);
        if(userBean.getEmail().equals(email))
            return "search";

        return "index";
    }
}