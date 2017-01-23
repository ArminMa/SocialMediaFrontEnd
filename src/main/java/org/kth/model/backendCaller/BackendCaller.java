package org.kth.model.backendCaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.kth.model.pojo.MailMessagePojo;
import org.kth.model.pojo.PostPojo;
import org.kth.model.pojo.TokenPojo;
import org.kth.model.pojo.UserPojo;
import org.kth.util.gsonX.GsonX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Backend method calls
 */
public interface BackendCaller {
    static final String baseUrlAddress = "http://104.199.70.136:8081";
    static final Logger logger1 = LoggerFactory.getLogger( UserPojo.class );
    static final String nLin = System.lineSeparator();

    /**
     * Makes a restless call and Attempts to register the user.
     * @param userPojo Desired user info
     * @return The created user if successful or null if unsuccessful
     */
    public static UserPojo register(UserPojo userPojo) {
        logger1.info("in register backend call");
        MediaType JsonUtf8Media = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
        String url = baseUrlAddress + "/social/register";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(JsonUtf8Media);
        HttpEntity<UserPojo> entity = new HttpEntity<UserPojo>(userPojo,headers);
        ResponseEntity<UserPojo> response = restTemplate.postForEntity(url, entity, UserPojo.class);
        logger1.info("after response:" + response.getStatusCode().toString());
        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            userPojo = response.getBody();
            return userPojo;
        }
        return null;
    }

    /**
     * Queries backend with a rest call. Returns true if successful and false if unsuccessful.
     * @param user Object containing the user credentials.
     * @return true on success and false on failure.
     */
    public static TokenPojo login(UserPojo user) {
        String url = baseUrlAddress + "/api/auth/login";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("Content-Type", "application/json");
        headers.set("Cache-Control", "no-cache");
        HttpEntity<UserPojo> entity = new HttpEntity<UserPojo>(user, headers);
        logger1.info("Entity : " + entity + nLin);
        ResponseEntity<TokenPojo> response = restTemplate.postForEntity(url, entity, TokenPojo.class);
        logger1.info("BEFORE IF " + nLin);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger1.info("after httpstatus ok!" + nLin);
            TokenPojo token = (TokenPojo) GsonX.gson.fromJson(response.getBody().toString(), TokenPojo.class);
            logger1.info("Token = " + token.getToken());
            logger1.info("successfully added cookie");
            return token;
        } else {
            logger1.error("Login failed");
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
        System.out.println("the url " + url);
        try {
            List<UserPojo> users = getUserPojosFromBackend(token, url);
            logger1.info("response successful");
            return users;
        } catch (Exception e) {
            logger1.error("call unsuccessful : " + e.toString());
            return null;
        }
    }

    static UserPojo searchUsersByEmail(String email) {
        final String url = baseUrlAddress + "/social/getUserByEmail/" + email;
        try {
            UserPojo user = getUserPojoFromBackend(url);
            logger1.info("response successful");
            return user;
        } catch (Exception e) {
            logger1.error("call unsuccessful : " + e.toString());
            return null;
        }
    }

    static List<UserPojo> getUserPojosFromBackend(String token, String url) {
        Client c = Client.create();
        WebResource resource = c.resource(url);
        UserPojo[] userArray = resource.header("x-authorization", "Bearer " + token).header("cache-control", "no-cache").get(UserPojo[].class);
        return Arrays.asList(userArray);
    }

    static UserPojo getUserPojoFromBackend(String url) {
        Client c = Client.create();
        WebResource resource = c.resource(url);
        UserPojo user = resource.get(UserPojo.class);
        return user;
    }

    static boolean sendPost(String token, PostPojo postPojo) {
        String url = baseUrlAddress + "/api/sendPost";
        Client c = Client.create();
        WebResource resource = c.resource(url);
        ClientResponse response = resource.type("application/json")
                .header("x-authorization", "Bearer " + token)
                .header("cache-control", "no-cache")
                .post(ClientResponse.class, postPojo.toString());

        if(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode()){
            logger1.info("ClientResponse.Status.CREATED.getStatusCode() == 201");
        }
        if (response.getStatus() != 201) {
            logger1.error("sendPost.Failed : HTTP error code : " + response.getStatus());
            return false;
        }
        return true;
    }

    static boolean sendMail(String token, MailMessagePojo mailMessagePojo) {
        String url = baseUrlAddress + "/api/sendMail";
        Client c = Client.create();
        WebResource resource = c.resource(url);
        ClientResponse response = null;
        try {
            response = resource.type("application/json")
                    .header("x-authorization", "Bearer " + token)
                    .header("cache-control", "no-cache")
                    .post(ClientResponse.class, mailMessagePojo.toString());
            if (response.getStatus() != ClientResponse.Status.CREATED.getStatusCode()) {
                logger1.error("sendMail.Failed : HTTP error code : " + response.getStatus());
                return false;
            }
            return true;
        } catch (UniformInterfaceException e){
            logger1.error("Failed : HTTP error code : " + response.getStatus());
            return false;
        }
    }

    static List<MailMessagePojo> getPersonalMessages(String token) {
        logger1.info("in getPersonalMessages");
        String url = baseUrlAddress + "/api/getMyMails";
        Client c = Client.create();
        WebResource resource = c.resource(url);
        try {
            MailMessagePojo[] messages = resource
                    .header("x-authorization", "Bearer " + token)
                    .header("cache-control", "no-cache")
                    .get(MailMessagePojo[].class);
            return Arrays.asList(messages);
        }  catch (UniformInterfaceException e){
            logger1.error("getPersonalMessages.Failed: " + e.toString());
            return null;
        }
    }

    static List<PostPojo> getLog(String token, String userName) {
        String url = baseUrlAddress + "/api/getPostsByUserName/" + userName;
        logger1.info("username = " + userName);
        Client c = Client.create();
        WebResource resource = c.resource(url);
        try {
            PostPojo[] postPojos = resource
                    .header("x-authorization", "Bearer " + token)
                    .header("cache-control", "no-cache")
                    .get(PostPojo[].class);
            return Arrays.asList(postPojos);
        }  catch (UniformInterfaceException e){
            logger1.error("getLog.Failed: " + e.toString());
            return null;
        }
    }

    static void deletePost(String token, PostPojo post) {
        logger1.info(nLin + nLin +" ----------------------- BackendCaller.deletePost ----------------------- " + nLin);
		logger1.info("BackendCaller.deletePost: post.getPostContent() = "  + post.getPostContent());
		logger1.info("Backendcaller.deletepost: id" + post.getId());
		logger1.info("Backendcaller.deletepost: date" + post.getPostedDate());
		logger1.info("Backendcaller.deletepost: tostring " + post.toString());
		logger1.info("Backendcaller.deletepost: sender tostring " + post.getSender().toString());
		logger1.info("Backendcaller.deletepost: receiver tostring" + post.getReceiver().toString());

        String url = baseUrlAddress + "/api/deleteLogMessage";
        Client c = Client.create();
        WebResource resource = c.resource(url);
        ClientResponse response = null;
        try {
            response = resource.type("application/json")
                    .header("x-authorization", "Bearer " + token)
                    .header("cache-control", "no-cache")
                    .post(ClientResponse.class, post);
            logger1.info("successfully posted with statuscode : " + response.getStatus());
        }  catch (UniformInterfaceException e){
            logger1.error("Failed deleting message: " + e.toString());
            if(response != null ) {
				logger1.info(nLin + "BackendCaller.deletePost: UniformInterfaceException response.getStatus()= "  + response.getStatus() + nLin);

			}else{
				logger1.info(nLin + "BackendCaller.deletePost: UniformInterfaceException response= null" + nLin);
			}
        }
    }

    static UserPojo getUserFromToken(String token) {
        Client c = Client.create();
        String url = baseUrlAddress + "/api/getUserFromToken";
        WebResource resource = c.resource( url );
        UserPojo userPojo= null;
        try{
            userPojo = resource.header("x-authorization", "Bearer " + token).header("cache-control", "no-cache").get(UserPojo.class);
            return userPojo;
        }catch (UniformInterfaceException ue){
            return null;
        }

    }

    static List<UserPojo> getAllUsers() {

        final String url = baseUrlAddress + "/social/getAllUsers";
        Client c = Client.create();
        WebResource resource = c.resource(url);
        try {
            UserPojo[] messages = resource.get(UserPojo[].class);
            return Arrays.asList(messages);
        }  catch (UniformInterfaceException e){
            logger1.error("getAllUsers.Failed: " + e.toString());
            return null;
        }
    }
}
