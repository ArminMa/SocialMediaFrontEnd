package org.kth.pojos.role;


public enum Role {
    MEMBER, PREMIUM_MEMBER, ADMIN, SUPER_ADMIN , ANONYMOUS;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
