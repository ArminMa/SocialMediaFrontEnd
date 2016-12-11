package org.kth.controller.handlers;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieManager {
    private final static String token = "token";
    private final static String refreshToken = "refreshToken";
    private final static String userName = "userName";

    public static void addToken(String body){
        addCookie(token, body);
    }

    public static void addRefreshToken(String body){
        addCookie(refreshToken, body);
    }

    public static void addUserName(String body){
        addCookie(userName, body);
    }

    public static String getToken() throws CookieNotFoundException{
        return getCookie(token);
    }

    public static String getRefreshToken() throws CookieNotFoundException{
        return getCookie(refreshToken);
    }

    public static String getUserName() throws CookieNotFoundException{
        return getCookie(userName);
    }

    public static void addCookie(String name, String content) {
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(name, content, null);
    }

    public static String getCookie(String name) throws CookieNotFoundException{
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] userCookies = request.getCookies();
        for(int i = 0; i < userCookies.length; i++){
            if(userCookies[i].getName().equals(name)){
                return userCookies[i].getValue();
            }
        }
        throw new CookieNotFoundException("parameter not found in cookie");
    }

}
