package org.kth.service;

import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.Date;
import javax.crypto.SecretKey;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.kth.beans.UserBean;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.kth.service.JpaServerCaller.JsonUtf8Media;


class RegisterUserCaller {

    private static String baseUrlAddress;
    private static RegisterUserCaller registerUserCaller;

    RegisterUserCaller(String baseUrlAddressParam) {
        baseUrlAddress = baseUrlAddressParam;
    }

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
