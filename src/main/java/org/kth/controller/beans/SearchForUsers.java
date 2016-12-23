package org.kth.controller.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.kth.controller.beans.chat.ChatBean;
import org.kth.controller.handlers.UserHandler;
import org.kth.model.pojo.MailMessagePojo;
import org.kth.model.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class SearchForUsers implements Serializable {
	public static final Logger logger1 = LoggerFactory.getLogger( UserHandler.class );
	private String searchString = "";
	private String searchOption = "";
	private List<UserPojo> users =  new ArrayList<>();
	private List<UserPojo> userBeansList =  new ArrayList<>();
	private MailMessagePojo messagePojo;
	private String message = "";
	private String topic = "";
	private String resultPost = "";



	public SearchForUsers() {
	}

	@ManagedProperty("#{chatBean}")
	private ChatBean chatBean;
	public ChatBean getChatBean() {
		return chatBean;
	}
	public void setChatBean(ChatBean chatBean) {
		this.chatBean = chatBean;
	}

	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<UserPojo> getUsers() {
		return users;
	}

	public void setUsers(List<UserPojo> users) {
		this.users = users;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public List<UserPojo> getUserBeansList() {
		return userBeansList;
	}

	public void setUserBeansList(List<UserPojo> userBeansList) {
		this.userBeansList = userBeansList;
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

	public void searchForUsers(){
		logger1.info("inside searchForUsers");
		userBeansList.clear();
		logger1.info("after clear");
		if(getSearchOption().equals("1UserName")){
			List<UserPojo> newUsers = UserHandler.getUserNamesByName(searchString);
			if(newUsers != null) {
				userBeansList.addAll(newUsers);
			}
		}else if(getSearchOption().equals("2Email")){
			UserPojo newUsers = UserHandler.getUserByEmail(searchString);
			if(newUsers != null) {
				userBeansList.add(newUsers);
			}
		}
	}

	public void sendMessage(UserPojo user){
		if(user == null || message == "" || topic == ""){
			resultPost = "Message cannot be sent. Require a receiver a message and a topic";

		}

		if(user != null ){
			if(user.getUsername() != null ){
				logger1.info("receiver name = " + user.getUsername());
			}else{
				logger1.info("receiver name = null" );
			}
			if(user.getId() != null ){
				logger1.info("receiver id = " + user.getId());
			}else{
				logger1.info("receiver id = null" );
			}
		}


		logger1.info("message value = " + message);

		logger1.info("topic = " + topic);
		resultPost = UserHandler.sendMailMessage(new MailMessagePojo(message, topic, user));
	}
}
