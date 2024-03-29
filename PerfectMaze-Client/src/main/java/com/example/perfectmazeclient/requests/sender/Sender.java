package com.example.perfectmazeclient.requests.sender;

import com.example.perfectmazeclient.requests.communication.Request;
import com.example.perfectmazeclient.requests.communication.Response;
import com.example.perfectmazeclient.requests.util.RequestUtils;
import com.example.perfectmazeclient.requests.util.ResponseUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Sender {
    private static final Gson gson = new GsonBuilder().create();
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 34567;

    public static <T> Response sendRequest(String action, T body) {

        Request request = RequestUtils.buildRequest(action, body);
        Response response = null;

        try
        {
            try (Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
                 Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
                 PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))))
            {
                writer.println(gson.toJson(request));
                writer.flush();

                String jsonResponse = scanner.nextLine();

                response = gson.fromJson(jsonResponse, Response.class);
            }
        }
        catch (IOException e)
        {
            response = ResponseUtils.buildResponse("Error: failed to send request", "failed");
        }

        return response;
    }
}
