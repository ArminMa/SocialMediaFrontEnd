package org.kth.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.kth.util.gsonX.GsonX;


@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@JsonInclude(JsonInclude.Include.NON_NULL )
public class UserPojo  implements Serializable,Comparable<UserPojo>{
    private Long id;
    private String username;
    private String email;
    private String password;
    private TokenPojo token;
    private Byte[] picture; // Todo implement this Armin. low prio
    private Collection<FriendRequestPojo> friendRequests = new ArrayList<>();
    private Collection<MailMessagePojo> mailMessages = new ArrayList<>();
    private Collection<UserPojo> friends = new ArrayList<>();
    private Collection<PostPojo> log = new ArrayList<>();
    private Collection<ChatMessagePojo> chatMessages = new ArrayList<>();
    private Collection<UserRolePojo> authorities = new TreeSet<>();
    private Collection<FriendRequestPojo> sentFriendRequests = new ArrayList<>();

    private Collection<FriendRequestPojo> receivedFriendRequests = new ArrayList<>();

    private Collection<UserFriendPojo> acceptedFriends = new ArrayList<>();

    private Collection<UserFriendPojo> requestedFriends = new ArrayList<>();


    public UserPojo() {}

    public UserPojo(String username, String email, String password) {
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

    public TokenPojo getToken() {
        return token;
    }

    public void setToken(TokenPojo token) {
        this.token = token;
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

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public Collection<FriendRequestPojo> getFriendRequests() {
        return friendRequests;
    }
    public void setFriendRequests(Collection<FriendRequestPojo> friendRequests) {
        this.friendRequests = friendRequests;
    }

	public Collection<FriendRequestPojo> getSentFriendRequests() {
		return sentFriendRequests;
	}
	public void setSentFriendRequests(Collection<FriendRequestPojo> sentFriendRequests) {
		this.sentFriendRequests = sentFriendRequests;
	}

	public Collection<FriendRequestPojo> getReceivedFriendRequests() {
		return receivedFriendRequests;
	}
	public void setReceivedFriendRequests(Collection<FriendRequestPojo> receivedFriendRequests) {
		this.receivedFriendRequests = receivedFriendRequests;
	}

	public Collection<UserFriendPojo> getAcceptedFriends() {
		return acceptedFriends;
	}
	public void setAcceptedFriends(Collection<UserFriendPojo> acceptedFriends) {
		this.acceptedFriends = acceptedFriends;
	}

	public Collection<UserFriendPojo> getRequestedFriends() {
		return requestedFriends;
	}
	public void setRequestedFriends(Collection<UserFriendPojo> requestedFriends) {
		this.requestedFriends = requestedFriends;
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

	public Collection<UserRolePojo> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<UserRolePojo> authorities) {
		this.authorities = authorities;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPojo userPojo = (UserPojo) o;

        if (id != null ? !id.equals(userPojo.id) : userPojo.id != null) return false;
        if (username != null ? !username.equals(userPojo.username) : userPojo.username != null) return false;
        if (email != null ? !email.equals(userPojo.email) : userPojo.email != null) return false;
        if (password != null ? !password.equals(userPojo.password) : userPojo.password != null) return false;
        return Arrays.equals(picture, userPojo.picture);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public int compareTo(UserPojo o) {
        int thisObject= this.hashCode();
        long anotherObject = o.hashCode();
        return (thisObject<anotherObject ? -1 : (thisObject==anotherObject ? 0 : 1));
    }

    @Override
    public String toString() {
        //to ensure that json wont send empty lists/arrays when using tostring set empty arrays to null
        if(this.friendRequests != null && this.friendRequests.isEmpty()){
            friendRequests = null;
        }
        if(this.mailMessages != null && this.mailMessages.isEmpty()){
            this.mailMessages = null;
        }
        if(this.friends != null && this.friends.isEmpty()){
            this.friends = null;
        }
        if(this.log != null && this.log.isEmpty()){
            this.log = null;
        }
        if(this.chatMessages != null && this.chatMessages.isEmpty()){
            this.chatMessages = null;
        }

        if(this.authorities != null && this.authorities.isEmpty()){
            this.authorities = null;
        }

        String thisJsonString = GsonX.gson.toJson(this);

        //reconstruct empty arrays
        if(this.friendRequests == null){
            friendRequests = new ArrayList<>();
        }
        if(this.mailMessages == null){
            this.mailMessages = new ArrayList<>();
        }
        if(this.friends == null){
            this.friends = new ArrayList<>();
        }
        if(this.log == null){
            this.log = new ArrayList<>();
        }
        if(this.chatMessages == null){
            this.chatMessages = new ArrayList<>();
        }

        if(this.authorities == null){
            this.authorities = new ArrayList<>();
        }

        return thisJsonString;
    }
}
