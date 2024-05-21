package it.polimi.ingsw.gc01.network.message;

public class ErrorMessage implements Message{
    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}