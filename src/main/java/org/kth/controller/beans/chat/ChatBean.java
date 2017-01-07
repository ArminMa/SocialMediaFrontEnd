package org.kth.controller.beans.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.kth.controller.handlers.CookieManager;
import org.kth.controller.handlers.CookieNotFoundException;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.UserPojo;

@ManagedBean
@SessionScoped
public class ChatBean implements Serializable {

	private UserPojo user1;
	private String user1Name;
	private String user1AsJson;
	private UserPojo user2;
	private String user2AsJson;
	private String user2Name;
	private String token;
	private String message;

	private List<message> messages = new ArrayList<>();

	public ChatBean() {
	}

	@PostConstruct
	public void init() {
		this.user1 = UserHandler.getUserFromToken();
		if(this.user1 != null) {
			this.user1AsJson = this.user1.toString();
			this.user1Name = user1.getUsername();
		}
		try {
			token = CookieManager.getToken();
		} catch (CookieNotFoundException e){
			token = null;
		}

		messages.add(new message("Armin", "hi")) ;
		messages.add(new message("Elisa", "Whats up??")) ;
		messages.add(new message("Armin", "nice AngularJS Directive")) ;
		messages.add(new message("Elisa", "Looks Great!!!")) ;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<message> getMessages() {
		return messages;
	}

	public void setMessages(List<message> messages) {
		this.messages = messages;
	}

	public String startChat(UserPojo userPojo2){
		System.out.println("called startChat");
		if(userPojo2 != null){
			this.user2 = userPojo2;
			this.user2AsJson = user2.toString();
			this.user2Name = user2.getUsername();
			return user2.getUsername();
		} else {
			return "anonymus";
		}
	}

	public String getUser1Name() {
		this.user1 = UserHandler.getUserFromToken();
		if(this.user1 != null) {
			this.user1AsJson = this.user1.toString();
			this.user1Name = this.user1.getUsername();
		}
		if(user1Name == null){
			return "nullWhat";
		}
		return user1Name;
	}

	public void setUser1Name(String user1Name) {
		this.user1Name = user1Name;
	}

	public String getUser2Name() {
		if (user2Name == null || user2Name.isEmpty())
			return "WTF is this?";
		return user2Name;
	}

	public void setUser2Name(String user2Name) {
		this.user2Name = user2Name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUser1AsJson(String user1AsJson) {
		this.user1AsJson = user1AsJson;
	}

	public void setUser2AsJson(String user2AsJson) {
		this.user2AsJson = user2AsJson;
	}

	public UserPojo getUser1() {
		return user1;
	}

	public void setUser1(UserPojo user1) {
		this.user1 = user1;
		this.user1AsJson = this.user1.toString();
	}

	public UserPojo getUser2() {
		return user2;
	}

	public void setUser2(UserPojo user2) {
		this.user2 = user2;
	}

	public String getUser1AsJson(){
		if(user1== null){
			init();
		}
		if(this.user1AsJson != null){
			return this.user1AsJson;
		}else{
			return "userNull";
		}
	}

	public String getUser2AsJson(){
		return user2.toString();
	}
}
