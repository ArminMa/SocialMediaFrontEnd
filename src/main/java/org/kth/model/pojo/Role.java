package org.kth.model.pojo;


import org.springframework.security.core.GrantedAuthority;

/**
 * Enumerated {@link UserPojo} roles.
 * @author vladimir.stankovic
 *
 * Aug 16, 2016
 */
public enum Role implements GrantedAuthority {
    MEMBER, PREMIUM_MEMBER, ADMIN, SUPER_ADMIN , ANONYMOUS, REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
    public String getAuthority() {
        return "ROLE_" + this.name();
    }

}
