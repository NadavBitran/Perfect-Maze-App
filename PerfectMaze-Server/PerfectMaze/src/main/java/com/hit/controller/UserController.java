package com.hit.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.hit.dm.User;
import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.server.Request;
import com.hit.server.Response;
import com.hit.server.util.ResponseUtils;
import com.hit.service.UserService;

public class UserController implements IController {

    private UserService userService;
    private Gson gson = new Gson();
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response executeAction(Request request) throws ControllerRoutingFailed {

        String methodAction = request.getHeaders().getAction().split("/")[1];
        String currentController = request.getHeaders().getAction().split("/")[0];

        switch(methodAction) {
            case "login":
                return login(request.getBody());
            case "register":
                return register(request.getBody());
            case "delete":
                return deleteUser(request.getBody());
            default:
                throw new ControllerRoutingFailed("Failed to route to method action: " + methodAction + " in controller: " + currentController);
        }
    }

    public Response login(JsonElement body) throws JsonSyntaxException
    {
        User userLoginDetails = gson.fromJson(body, User.class);

        Response response = null;

        try
        {
            String userId = userService.login(userLoginDetails.getEmail(), userLoginDetails.getPassword());
            response = ResponseUtils.buildResponse("Login successful", "success", "userId", userId);
        }
        catch(Exception e)
        {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

    public Response register(JsonElement body) throws JsonSyntaxException
    {
        User userRegistrationDetails = gson.fromJson(body, User.class);

        Response response = null;

        try
        {
            String userId = userService.register(userRegistrationDetails.getEmail(), userRegistrationDetails.getPassword(), userRegistrationDetails.getUsername());
            response = ResponseUtils.buildResponse("Registration successful", "success", "userId", userId);
        }
        catch(Exception e)
        {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

    public Response deleteUser(JsonElement body) throws JsonSyntaxException
    {
        User user = gson.fromJson(body, User.class);

        Response response = null;

        try
        {
            userService.deleteUser(user.getEmail(), user.getUserId());
            response = ResponseUtils.buildResponse("User deleted successfully", "success");
        }
        catch(Exception e)
        {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }
}
