package org.kth.model.backendCaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.kth.model.pojo.TokenPojo;
import org.kth.model.pojo.UserPojo;
import org.kth.util.gsonX.GsonX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Backend method calls
 */
public interface BackendCaller {
    static final String baseUrlAddress = "http://localhost:8081";
    static final Logger logger1 = LoggerFactory.getLogger( UserPojo.class );
    static final String nLin = System.lineSeparator();

    /**
     * Makes a restless call and Attempts to register the user.
     * @param userBean Desired user info
     * @return The created user if successful or null if unsuccessful
     */
    public static UserPojo register(UserPojo userBean) {
        MediaType JsonUtf8Media = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
        String url = baseUrlAddress + "/social/register";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(JsonUtf8Media);
        HttpEntity<UserPojo> entity = new HttpEntity<UserPojo>(userBean,headers);
        ResponseEntity<UserPojo> response = restTemplate.postForEntity(url, entity, UserPojo.class);
        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            userBean = response.getBody();
            return userBean;
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
}
