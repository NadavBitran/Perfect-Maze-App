package com.example.perfectmazeclient.containers;

import com.example.perfectmazeclient.dm.User;

public final class CurrentLoggedUserContainer {
    private static User loggedUser;

    private CurrentLoggedUserContainer() {}

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }
}
