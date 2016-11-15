package org.kth.handlers;

import org.apache.log4j.Level;
import org.kth.GsonX;
import org.kth.beans.UserBean;
import org.kth.pojos.UserPojo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserHandler {

    private static final String baseUrlAddress = "http://localhost:8081";

    public static boolean login(String userName, String password) {
        String url = baseUrlAddress + "/login/" + userName + "/" + password;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return true;
        } else {
            return false;
        }
    }

    public static Collection<UserBean> searchForUsersByUserName(String name) {
        if(name == null) return null;

        String url = baseUrlAddress + "/userSearch/" + name;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserBean[]> response = restTemplate.getForEntity(url, UserBean[].class);
        Collection<UserBean> matches = null;
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return (Collection<UserBean>)GsonX.gson.fromJson(response.getBody().toString(), Collection.class);
        }
        return null;
    }

    public static UserBean getUserByEmail(String email){
        if(email == null) return null;

        String url = baseUrlAddress + "/getEmail/" + email;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url,  Object[].class);
        UserBean user = null;
        if(response.getStatusCode().equals(HttpStatus.OK)){
            user = GsonX.gson.fromJson(response.getBody().toString(), UserBean.class);
        }
        return user;
    }

    public static String testConnection(){
        String url = baseUrlAddress + "/ping1";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}