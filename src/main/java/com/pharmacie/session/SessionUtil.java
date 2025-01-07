package com.pharmacie.session;

import com.pharmacie.models.User;
import com.pharmacie.models.Login;

public class SessionUtil {
    
    private static User currentUser;
    private static Login currentLogin;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static Login getCurrenLogin() {
        return currentLogin;
    }

    public static void setCurrentLogin(Login login) {
        currentLogin = login;
    }
}
