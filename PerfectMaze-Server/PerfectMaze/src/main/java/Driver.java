import com.hit.server.Server;

public class Driver {
    public static void main(String[] args)  {
        int port = 8000; // Default port
        Server server = new Server(port);
        System.out.println("Server is starting on port: " + port);
        server.startServer();
    }
}
