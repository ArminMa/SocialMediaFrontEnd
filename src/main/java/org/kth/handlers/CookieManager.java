package org.kth.handlers;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sebastian on 2016-11-26.
 */
public class CookieManager {
    private final static String token = "token";
    private final static String refreshToken = "refreshToken";

    public static void addToken(String body){
        addCookie(token, body);
    }

    public static void addRefreshToken(String body){
        addCookie(refreshToken, body);
    }

    public static String getToken() throws CookieNotFoundException{
        return getCookie(token);
    }

    public static String getRefreshToken() throws CookieNotFoundException{
        return getCookie(refreshToken);
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
