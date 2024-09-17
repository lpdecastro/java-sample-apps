import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the chat server");

            // Create a new thread to listen for messages from the server
            new Thread(new ServerListener(socket)).start();

            // Main thread for sending messages to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);  // Send user input to the server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ServerListener class to listen for messages from the server
class ServerListener implements Runnable {
    private Socket socket;

    public ServerListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message from server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
