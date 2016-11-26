package org.kth.handlers;


import org.kth.beans.UserBean;
import org.kth.model.pojos.User.UserPojo;
import org.kth.util.gsonX.GsonX;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Am on 11/4/2016.
 */
public class ModelConverter {

    public ModelConverter() {
    }

    private static boolean ObjectNotNull(Object o){
        return (o == null ? false : true);
    }

    public static UserPojo convert(UserBean userEntity){
        if( userEntity == null ) return null;
        return GsonX.gson.fromJson(userEntity.toString(), UserPojo.class );
    }

    public static UserBean convert(UserPojo userPojo){
        if( userPojo == null ) return null;
        return GsonX.gson.fromJson(userPojo.toString(), UserBean.class );
    }

    @SuppressWarnings("unchecked")
    public static Iterable<?> convert (Iterable<?> genericList){

        if( genericList == null ) return null;

        if ( !genericList.iterator().hasNext() ) return null;

        if(genericList.iterator().next() instanceof UserPojo){
            Collection<UserPojo> userPojos = new ArrayList<>();
            genericList.forEach( S -> userPojos.add( convert( (UserBean) S) ) );
            return userPojos;
        }

        if(genericList.iterator().next() instanceof UserBean){
            Collection<UserBean> userPojos = new ArrayList<>();
            genericList.forEach( S -> userPojos.add( convert( (UserPojo) S) ) );
            return userPojos;
        }

        return null;
    }
}
