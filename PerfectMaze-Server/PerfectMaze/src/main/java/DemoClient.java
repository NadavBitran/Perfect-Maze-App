import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hit.server.Request;
import com.hit.server.util.Header;

import java.io.*;
import java.net.Socket;

public class DemoClient {

    private final String host;
    private final int port;
    private final Gson gson;

    public DemoClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.gson = new Gson();
    }

    public void sendRequest(String action, JsonObject body) {
        try (Socket socket = new Socket(this.host, this.port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Create header with action
            Header header = new Header(action);

            // Create request with header and body
            Request request = new Request(header, body);

            // Convert request to JSON and send it
            String jsonRequest = gson.toJson(request);
            out.println(jsonRequest);

            // Wait for and read the response
            String jsonResponse = in.readLine();
            System.out.println("Received response: " + jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DemoClient client = new DemoClient("localhost", 8000);

        // Example usage of sending a request
        JsonObject body = new JsonObject();
        body.addProperty("key", "value"); // Customize the body as needed

        client.sendRequest("actionName", body); // Replace "actionName" with the specific action you're testing
    }
}
