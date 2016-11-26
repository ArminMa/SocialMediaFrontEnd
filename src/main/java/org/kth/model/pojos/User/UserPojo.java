package org.kth.model.pojos.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.kth.beans.UserBean;
import org.kth.model.pojos.TokenPojo;
import org.kth.util.gsonX.GsonX;
import org.kth.model.pojos.chatMessage.ChatMessagePojo;
import org.kth.model.pojos.friendRequest.FriendRequestPojo;
import org.kth.model.pojos.mailMessage.MailMessagePojo;
import org.kth.model.pojos.post.PostPojo;
import org.kth.model.pojos.role.UserRolePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@JsonInclude(JsonInclude.Include.NON_NULL )

public class UserPojo  implements Serializable,Comparable<UserPojo>{
    private static final String baseUrlAddress = "http://localhost:8081";
    private static final Logger logger1 = LoggerFactory.getLogger( UserPojo.class );
    private static final String nLin = System.lineSeparator();

    private Long id;
    private String username;
    private String email;
    private String password;
    private byte[] picture; // Todo implement this Armin. low prio
    @JsonInclude(JsonInclude.Include.NON_EMPTY )
    private Collection<FriendRequestPojo> friendRequests = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY )
    private Collection<MailMessagePojo> mailMessages = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY )
    private Collection<UserPojo> friends = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY )
    private Collection<PostPojo> log = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY )
    private Collection<ChatMessagePojo> chatMessages = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY )
    private Collection<UserRolePojo> authorities = new TreeSet<>();

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
        //to ensure that json wont send empty lists/arrays when using toString set empty arrays to null
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

    //Backend method calls

    /**
     * Queries backend with a rest call. Returns true if successful and false if unsuccessful.
     * @param user Object containing the user credentials.
     * @return true on success and false on failure.
     */
    public static TokenPojo login(UserBean user) {
        String url = baseUrlAddress + "/api/auth/login";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("Content-Type", "application/json");
        headers.set("Cache-Control", "no-cache");
        HttpEntity<UserBean> entity = new HttpEntity<UserBean>(user, headers);
        logger1.error("Entity : " + entity + nLin);
        ResponseEntity<TokenPojo> response = restTemplate.postForEntity(url, entity, TokenPojo.class);
        logger1.error("BEFORE IF " + nLin);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger1.error("after httpstatus ok!" + nLin);
            TokenPojo token = (TokenPojo) GsonX.gson.fromJson(response.getBody().toString(), TokenPojo.class);
            logger1.error("Token = " + token.getToken());
            logger1.error("successfully added cookie");
            return token;
        } else {
            return null;
        }
    }

    /**
     * Queries backend with a rest call. Returns null if response is empty.
     * @param token the authorization token to be included in the header
     * @param name The name to search for
     * @return matching names
     */
    public static List<UserPojo> searchUsersByName(String token, String name) {
        final String url = baseUrlAddress + "/api/userSearch/" + name;
        try {
            Client c = Client.create();
            WebResource resource = c.resource(url);
            UserPojo[] userArray = resource.header("x-authorization", "Bearer " + token).header("cache-control", "no-cache").get(UserPojo[].class);
            List<UserPojo> users = Arrays.asList(userArray);
            logger1.info("response successful");
            return users;
        } catch (Exception e) {
            logger1.error("call unsuccessful : " + e.toString());
            return null;
        }
    }
}
