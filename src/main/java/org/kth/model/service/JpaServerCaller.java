package org.kth.model.service;

import java.nio.charset.Charset;
import org.kth.beans.UserBean;
import org.springframework.http.MediaType;


public interface JpaServerCaller {
  static final MediaType JsonUtf8Media = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
  public static final String baseUrlAddress = "http://localhost:8081";


  static final RegisterUserCaller registerUserCaller = new RegisterUserCaller(baseUrlAddress);


  static UserBean register(UserBean userBean) {
    return RegisterUserCaller.register(userBean);
  }

}
