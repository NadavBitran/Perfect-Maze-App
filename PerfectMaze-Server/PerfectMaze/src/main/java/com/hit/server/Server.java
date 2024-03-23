package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private boolean running;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("Server started on port " + port);

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                    // Create a new HandleRequest instance for each connection and start it in a new thread.
                    HandleRequest requestHandler = new HandleRequest(clientSocket);

                    new Thread(requestHandler).start();
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server on port " + port + ": " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
        System.out.println("Server stopped.");
    }
}
