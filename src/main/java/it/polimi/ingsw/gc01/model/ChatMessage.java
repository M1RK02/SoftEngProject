package it.polimi.ingsw.gc01.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String sender;
    private String content;
    private String recipient;

    public ChatMessage(String sender, String content, String recipient){
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
