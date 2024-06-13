package it.polimi.ingsw.gc01.network.socket.messages;

public class ServiceMessage implements Message {
    private final String message;

    public ServiceMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}