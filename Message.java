import java.time.LocalDateTime;

public class Message {
    private String receiver;
    private String content;
    private LocalDateTime timestamp;
    private boolean status;
    private String messageId;
    private static int counter = 0;

    public Message(String receiver, String content, boolean status) {
        this.receiver = receiver;
        this.content = content;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.messageId = String.format("%03d", ++counter);
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessageId() {
        return messageId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Receiver: " + receiver +
                "\nReceiver ID: " + messageId +
                "\nMessage: " + content +
                "\nStatus: " + (status ? "Sent" : "Received") +
                "\nTime: " + timestamp;
    }
}
