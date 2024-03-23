package com.hit.server.util;

import com.hit.server.Request;

public class RequestUtils {
    public static boolean isRequestValid(Request request) {
        return  request != null &&
                request.getHeaders() != null &&
                request.getHeaders().getAction() != null;
    }
}
