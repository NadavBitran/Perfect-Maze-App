package com.hit.server;

import com.google.gson.JsonElement;
import com.hit.server.util.Header;

public class Request {
    private Header headers;
    private JsonElement body;

    public Request() {}
    public Request(Header header, JsonElement body) {
        this.headers = header;
        this.body = body;
    }

    public Header getHeaders() {
        return headers;
    }

    public void setHeader(Header headers) {
        this.headers = headers;
    }

    public JsonElement getBody() {
        return body;
    }

    public void setBody(JsonElement body) {
        this.body = body;
    }
}
