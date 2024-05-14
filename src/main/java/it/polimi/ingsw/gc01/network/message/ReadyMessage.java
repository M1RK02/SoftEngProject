package it.polimi.ingsw.gc01.network.message;

public class ReadyMessage implements Message{
    private boolean ready;

    public ReadyMessage(boolean ready) {
        this.ready = ready;
    }

    public boolean getReady() {
        return ready;
    }
}
