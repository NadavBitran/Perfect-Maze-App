package com.example.perfectmazeclient.requests.communication;

import com.google.gson.JsonElement;

public class Response {

    private JsonElement body;


    public Response() {}
    public Response(JsonElement body) {
        this.body = body;
    }


    public JsonElement getBody() {
        return body;
    }

    public void setBody(JsonElement body) {
        this.body = body;
    }
}
