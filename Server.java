import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private List<Message> messageList;
    private Functionalities functionalities;

    public Server() {
        messageList = new ArrayList<>();
        functionalities = new Functionalities();
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started, waiting for Client on Port Number: " + port);

            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            boolean running = true;

            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1. Send Message");
                System.out.println("2. Display Messages");
                System.out.println("3. Search Messages");
                System.out.println("4. Delete Message");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int choice = functionalities.scanner.nextInt();
                functionalities.scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        functionalities.sendMessage("Server", "Client", in, out, messageList);
                        break;
                    case 2:
                        functionalities.displayMessages(messageList);
                        break;
                    case 3:
                        functionalities.searchMessages(messageList);
                        break;
                    case 4:
                        functionalities.deleteMessage(messageList, out);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(5000);
    }
}
