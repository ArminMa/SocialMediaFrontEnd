package org.kth.handlers;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.util.Arrays;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.kth.pojos.User.UserPojo;
import org.kth.util.gsonX.GsonX;
import org.kth.beans.UserBean;
import org.kth.pojos.TokenPojo;
import org.kth.service.JpaServerCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

public class UserHandler {
	public static final Logger logger1 = LoggerFactory.getLogger( UserHandler.class );
    private static final String baseUrlAddress = "http://localhost:8081";
	public static final String nLin = System.lineSeparator();

    public static boolean login(UserBean user) {
	    logger1.error(nLin+nLin+" ---------- UserHandler.authenticate debug start ----------\n" );
	    System.out.println("wtf why cant i see this");
	    logger1.error("wtf why cant i see this");
	    String url = baseUrlAddress + "/api/auth/login";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(JsonUtf8Media);
	    headers.set("X-Requested-With", "XMLHttpRequest");
	    headers.set("Content-Type" ,"application/json");
	    headers.set("Cache-Control", "no-cache");
        HttpEntity<UserBean> entity = new HttpEntity<UserBean>(user,headers);
	    logger1.error("Entity : " + entity + nLin);
	    System.out.println("before response");
	    ResponseEntity<TokenPojo> response = restTemplate.postForEntity(url, entity, TokenPojo.class);
	    System.out.println("after response");
	    logger1.error("BEFORE IF " + nLin);
	    System.out.println("statuscode = " + response.getStatusCode());
        if(response.getStatusCode().equals(HttpStatus.OK)){
        	logger1.error("after httpstatus ok!" + nLin);
	        System.out.println("before extracting token");
	        TokenPojo token =  (TokenPojo)GsonX.gson.fromJson(response.getBody().toString(), TokenPojo.class);
	        logger1.error("Token = " + token.getToken());
	        System.out.println("Login Token = " + token.getToken());
	        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("token", token.getToken(), null);
	        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("refreshToken", token.getRefreshToken(), null);
	        logger1.error("successfully added cookie");
	        return true;
        } else {
            return false;
        }
    }

    public static Collection getUserNamesByName(String name) {
	    if(name == null) return null;

	    logger1.error("in getUsersByName");
	    String url = baseUrlAddress + "/api/userSearch/" + name;
	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

	    String token = null;
//	    TokenPojo tokenPojo = null;
	    Cookie[] userCookies = request.getCookies();
	    Cookie cookie = null;
		logger1.error(nLin+"before cookie finder");
	    for(int i = 0; i < userCookies.length; i++){
	    	if(userCookies[i].getName().equals("token")){
	    		cookie = userCookies[i];
		    }
	    }
	    logger1.error(nLin+"after cookie finder");

	    if(cookie != null){
		    System.out.println("found cookie!!!");
		    token = cookie.getValue();
		    logger1.error("-----------------------------Cookie entry = " + token);
	    }

	    if(token == null || token.isEmpty()){
		    System.out.println("tokenPojo could not be extracted from cookie!!!");
	    	return null;
	    }

	    OkHttpClient client = new OkHttpClient();

	    Request request3 = new Request.Builder()
			    .url("http://localhost:8081/api/userSearch/e")
			    .get()
			    .addHeader("x-authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Iiwic2NvcGVzIjpbIlJPTEVfTUVNQkVSIl0sImlzcyI6Imh0dHA6Ly9zb2NpYWxXYXIua3RoIiwiaWF0IjoxNDgwMDE5MDk3LCJleHAiOjE0ODEzMTUwOTd9.8FtX1HOhF5Bn-wLXZhuJo_CoDQBAhw9hEgeiuMF6Y2OJrNcvQhM6JgKs1MH-t3qE1Ukw8ebQ0Ze-Vne57CVoaA")
			    .addHeader("cache-control", "no-cache")
			    .addHeader("postman-token", "b26a4ef7-4a7e-57d6-1bed-63f7ad990e15")
			    .build();

	    try {
		    Response response = client.newCall(request3).execute();
		    if(response.isSuccessful()){
			    System.out.println("in http ok");
			    logger1.error("response successfull");
			    return (Collection<UserBean>)GsonX.gson.fromJson(response.body().toString(), Collection.class);
		    }
	    } catch (Exception ex){

	    }

	    return null;
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

        userBean = JpaServerCaller.register(userBean);
        // handle the user hear
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