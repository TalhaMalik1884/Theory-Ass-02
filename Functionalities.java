import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Functionalities {
    public Scanner scanner;

    public Functionalities() {
        scanner = new Scanner(System.in);
    }

    public void sendMessage(String sender, String receiver, BufferedReader in, PrintWriter out, List<Message> messageList) {
        try {
            boolean inCommunication = true;

            while (inCommunication) {
                System.out.print(sender + ": ");
                String message = scanner.nextLine();

                Message sentMessage = new Message(receiver, message, true);
                messageList.add(sentMessage);  // Add sent message to list
                out.println(message);  // Send message to the other side

                if ("bye".equalsIgnoreCase(message)) {
                    System.out.println("Ending current conversation...");
                    break;
                }

                String response = in.readLine();  // Receive response from other side
                if (response != null) {
                    if (response.startsWith("DELETE ")) {
                        String messageId = response.split(" ")[1];
                        deleteMessageById(messageList, messageId);
                        System.out.println(receiver + " deleted a message.");
                    } else {
                        Message receivedMessage = new Message(sender, response, false);
                        messageList.add(receivedMessage);  // Add received message to list
                        System.out.println(receiver + ": " + response);

                        if ("bye".equalsIgnoreCase(response)) {
                            System.out.println(receiver + " ended the current conversation.");
                            break;
                        }
                    }
                } else {
                    System.out.println(receiver + " has disconnected.");
                    inCommunication = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Communication error: " + e.getMessage());
        }
    }

    public void displayMessages(List<Message> messages) {
        if (messages.isEmpty()) {
            System.out.println("No messages to display.\n");
            return;
        }

        System.out.println("\nList of Messages:");
        for (int i = 0; i < messages.size(); i++) {
            System.out.println((i + 1) + ". " + messages.get(i));
        }
        System.out.println();
    }

    public void searchMessages(List<Message> messages) {
        if (messages.isEmpty()) {
            System.out.println("No messages to search.\n");
            return;
        }

        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        System.out.println("\nSearch Results:");
        boolean found = false;

        for (Message message : messages) {
            if (message.getContent().toLowerCase().contains(keyword)) {
                System.out.println(message);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found containing the keyword: " + keyword);
        }

        System.out.println();
    }

    public void deleteMessage(List<Message> messages, PrintWriter out) {
        if (messages.isEmpty()) {
            System.out.println("No messages to delete.\n");
            return;
        }

        displayMessages(messages);

        System.out.print("Enter the number of the message to delete: ");
        int messageIndex = scanner.nextInt() - 1;
        scanner.nextLine();  // Consume newline

        if (messageIndex >= 0 && messageIndex < messages.size()) {
            Message removedMessage = messages.remove(messageIndex);
            System.out.println("Message deleted: " + removedMessage.getContent());

            out.println("DELETE " + removedMessage.getMessageId());  // Notify other side
        } else {
            System.out.println("Invalid message number.\n");
        }
    }

    private void deleteMessageById(List<Message> messages, String messageId) {
        messages.removeIf(message -> message.getMessageId().equals(messageId));
    }
}
