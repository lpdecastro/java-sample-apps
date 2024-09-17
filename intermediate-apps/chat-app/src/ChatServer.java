import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server class to handle multiple clients
public class ChatServer {
    private static final int PORT = 12345;
    private static List<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept new client connection
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start(); // Start a new thread for each client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to broadcast messages to all clients
    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {  // Don't send the message to the sender
                client.sendMessage(message);
            }
        }
    }

    // Method to remove a client handler when a client disconnects
    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        System.out.println("A client has disconnected.");
    }
}

// ClientHandler class to handle each client
class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Setup input and output streams for communication
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                ChatServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // Close socket on client disconnect
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatServer.removeClient(this); // Remove client from the list
        }
    }

    // Send message to the client
    public void sendMessage(String message) {
        out.println(message);
    }
}
