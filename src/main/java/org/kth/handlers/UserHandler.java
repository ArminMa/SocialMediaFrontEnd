package org.kth.handlers;

import org.apache.log4j.Level;
import org.kth.GsonX;
import org.kth.beans.UserBean;
import org.kth.pojos.UserPojo;
import org.kth.service.JpaServerCaller;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserHandler {

    private static final String baseUrlAddress = "http://localhost:8081";

    public static boolean login(String user, String pass) {
        if (user.compareToIgnoreCase("reine") == 0 &&
                pass.compareTo("tjo") == 0)
            return true;
        else
            return false;
    }

    public static Collection getUserNamesByName(String name) {
        ArrayList<String> names = new ArrayList<>();
        for(int i= 1; i<11; i++){
            names.add("name =" +i);
        }
        return names;
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
        if(userBean == null || userBean.getUserName() == null || userBean.getEmail() == null || userBean.getPassword() == null) return null;

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