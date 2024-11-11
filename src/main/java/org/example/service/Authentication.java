package org.example.service;

import lombok.Getter;
import org.example.entity.User;

public class Authentication {

    @Getter
    private static User loggedInUser;

    public static void setLoggedUser(User user){
        loggedInUser = user;
    }

    public static void logout(){
        loggedInUser = null;
    }

}
