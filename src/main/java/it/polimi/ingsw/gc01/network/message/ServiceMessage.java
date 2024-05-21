package it.polimi.ingsw.gc01.network.message;

public class ServiceMessage implements Message {
    private String message;

    public ServiceMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}