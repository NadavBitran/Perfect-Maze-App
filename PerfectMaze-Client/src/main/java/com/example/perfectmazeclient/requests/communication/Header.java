package com.example.perfectmazeclient.requests.communication;

public class Header {
    private String action;

    public Header() {}

    public Header(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
