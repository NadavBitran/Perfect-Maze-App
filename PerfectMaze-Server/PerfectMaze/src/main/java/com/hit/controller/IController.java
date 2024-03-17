package com.hit.controller;

import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.server.Request;
import com.hit.server.Response;

public interface IController {
    Response executeAction(Request request) throws ControllerRoutingFailed;
}
