package org.kth.controller.handlers;

import java.util.*;

import org.kth.model.backendCaller.BackendCaller;
import org.kth.model.pojo.MailMessagePojo;
import org.kth.model.pojo.TokenPojo;
import org.kth.model.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserHandler {
	public static final Logger logger1 = LoggerFactory.getLogger( UserHandler.class );
	private static final String baseUrlAddress = "http://localhost:8081";
	public static final String nLin = System.lineSeparator();

	/**
	 * Tries to login the user with the given user credentials. Returns true if successful and false if unsuccessful.
	 */
	public static boolean login(UserPojo user) {
		logger1.info(nLin+nLin+" ---------- UserHandler.authenticate debug start ----------\n" );
		TokenPojo token = BackendCaller.login(user);
		if(token == null){
			return false;
		}
		CookieManager.addToken(token.getToken());
		CookieManager.addRefreshToken(token.getRefreshToken());
		return true;
	}

	/**
	 * Makes a rest call to the backend using given search string.
	 * Returns null if response is empty.
	 */
	public static List<UserPojo> getUserNamesByName(String name) {
		if(name == null) return null;
		logger1.info("search string = " + name);
		String token = null;
		//Get token from cookie
		try {
			token = CookieManager.getToken();
		} catch (CookieNotFoundException e){
			return null;
		}

		List<UserPojo> userPojos = BackendCaller.searchUsersByName(token, name);
		return userPojos;
	}

	// logic should be in this class and not in the bean classes
	public static String registerNewUser(UserPojo userBean){
		if(userBean == null || userBean.getUsername() == null || userBean.getEmail() == null || userBean.getPassword() == null) return null;

		userBean = BackendCaller.register(userBean);
		// handle the user here
		if(userBean == null)
			return "register";
		return "index";
	}

	public static UserPojo getUserByEmail(String email){
		if(email == null) return null;

		return BackendCaller.searchUsersByEmail(email);
	}

	public static String sendPostMessage(MailMessagePojo mailMessagePojo) {
		String token = null;
		//Get token from cookie
		try {
			token = CookieManager.getToken();
		} catch (CookieNotFoundException e){
			return "Message could not be sent";
		}

		if(BackendCaller.sendMail(token, mailMessagePojo)){
			return "successfully sent mail to " + mailMessagePojo.getReceiver().getUsername();
		}
		return "Message could not be sent. Try again later";
	}
}