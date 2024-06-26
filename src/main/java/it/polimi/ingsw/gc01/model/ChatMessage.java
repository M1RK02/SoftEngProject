package it.polimi.ingsw.gc01.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private final String sender;
    private final String content;
    private final String recipient;

    public ChatMessage(String sender, String content, String recipient){
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }
}