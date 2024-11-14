import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private List<Message> messageList;
    private Functionalities functionalities;

    public Client() {
        messageList = new ArrayList<>();
        functionalities = new Functionalities();
    }

    public void startClient(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to the server at " + ip + ":" + port);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
                        functionalities.sendMessage("Client", "Server", in, out, messageList);
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
            stopClient();
        }
    }

    public void stopClient() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client stopped.");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient("10.135.53.246", 5000);
    }
}
