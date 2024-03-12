package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.hit.controller.ControllerFactory;
import com.hit.controller.IController;
import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.server.util.RequestUtils;
import com.hit.server.util.ResponseUtils;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class HandleRequest implements Runnable {

    private Gson gson = new GsonBuilder().create();
    private Socket cilentSocket;

    public HandleRequest(Socket clientSocket) {
        this.cilentSocket = clientSocket;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(cilentSocket.getInputStream())));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cilentSocket.getOutputStream())))) {

            try {
                String jsonRequest = scanner.nextLine();
                Request request = gson.fromJson(jsonRequest, Request.class);

                if (RequestUtils.isRequestValid(request))
                {
                    String controllerName = request.getHeaders().getAction().split("/")[0];
                    IController controller = ControllerFactory.createController(controllerName);
                    Response response = controller.executeAction(request);

                    String jsonResponse = gson.toJson(response);

                    writer.println(jsonResponse);
                }
                else
                {
                    Response response = ResponseUtils.buildResponse("request missing header or body", "failed");

                    writer.println(gson.toJson(response));
                }

            } catch (ControllerRoutingFailed | JsonSyntaxException e) {
                Response response = ResponseUtils.buildResponse(e.getMessage(), "failed");

                writer.println(gson.toJson(response));
            } finally {
                closeSocket();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void closeSocket() throws IOException
    {
        cilentSocket.close();
    }
}
