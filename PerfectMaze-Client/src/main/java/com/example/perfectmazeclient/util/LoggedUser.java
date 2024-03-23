package com.example.perfectmazeclient.util;

import com.example.perfectmazeclient.dm.User;

public final class LoggedUser {
    private static User loggedUser;

    private LoggedUser() {}

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        if (loggedUser == null) {
            loggedUser = user;
        }
    }
}
