package it.polimi.ingsw.gc01.network.socket.messages;

public class ReadyMessage implements Message {
    private final boolean ready;

    public ReadyMessage(boolean ready) {
        this.ready = ready;
    }

    public boolean getReady() {
        return ready;
    }
}
