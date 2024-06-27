package it.polimi.ingsw.gc01.model;

import java.io.Serializable;

/**
 * Class that represents how the instance of a message in the chat is
 */
public class ChatMessage implements Serializable {
    private final String sender;
    private final String content;
    private final String recipient;

    /**
     * @param sender    sender of the message
     * @param content   content of the message
     * @param recipient recipient of the message
     */
    public ChatMessage(String sender, String content, String recipient) {
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    /**
     * @return sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * @return recipient of the message
     */
    public String getRecipient() {
        return recipient;
    }
}