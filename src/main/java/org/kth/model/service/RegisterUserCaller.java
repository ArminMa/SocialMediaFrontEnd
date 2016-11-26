package org.kth.model.service;

import org.kth.beans.UserBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.kth.model.service.JpaServerCaller.JsonUtf8Media;


class RegisterUserCaller {

    private static String baseUrlAddress;
    private static RegisterUserCaller registerUserCaller;

    RegisterUserCaller(String baseUrlAddressParam) {
        baseUrlAddress = baseUrlAddressParam;
    }

    /**
     * Makes a restless call and Attempts to register the user.
     * @param userBean Desired user info
     * @return The created user if successful or null if unsuccessful
     */
    static UserBean register(UserBean userBean) {
        String url = baseUrlAddress + "/social/register";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(JsonUtf8Media);
        HttpEntity<UserBean> entity = new HttpEntity<UserBean>(userBean,headers);
        ResponseEntity<UserBean> response = restTemplate.postForEntity(url, entity, UserBean.class);
        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            userBean = response.getBody();
            return userBean;
        }

        return null;
    }
}
