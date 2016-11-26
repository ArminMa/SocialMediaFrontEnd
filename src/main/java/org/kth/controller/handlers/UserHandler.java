package org.kth.controller.handlers;

import java.util.*;

import org.kth.model.TokenPojo;
import org.kth.model.UserPojo;
import org.kth.util.gsonX.GsonX;
import org.kth.controller.beans.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserHandler {
	public static final Logger logger1 = LoggerFactory.getLogger( UserHandler.class );
    private static final String baseUrlAddress = "http://localhost:8081";
	public static final String nLin = System.lineSeparator();

	/**
	 * Tries to login the user with the given user credentials. Returns true if successful and false if unsuccessful.
	 */
    public static boolean login(UserBean user) {
	    logger1.info(nLin+nLin+" ---------- UserHandler.authenticate debug start ----------\n" );
        TokenPojo token = UserPojo.login(user);
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

		List<UserPojo> userPojos = UserPojo.searchUsersByName(token, name);
		return userPojos;
    }

	public static UserBean getUserByEmail(String email){
        if(email == null) return null;

        String url = baseUrlAddress + "/getEmail/" + email;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserBean> response = restTemplate.getForEntity(url, UserBean.class);
        UserBean user = null;
        if(response.getStatusCode().equals(HttpStatus.OK)){
            user = GsonX.gson.fromJson(response.getBody().toString(), UserBean.class);
        }
        return user;
    }

    // logic should be in this class and not in the bean classes
    public static String registerNewUser(UserBean userBean){
        if(userBean == null || userBean.getUsername() == null || userBean.getEmail() == null || userBean.getPassword() == null) return null;

        userBean = UserPojo.register(userBean);
        // handle the user here
        if(userBean == null)
            return "index";
        return "search";
    }

    public static String testConnection(){
        String url = baseUrlAddress + "/ping1";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}