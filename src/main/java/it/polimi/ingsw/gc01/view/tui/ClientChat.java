package it.polimi.ingsw.gc01.view.tui;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.util.*;

/**
 * Represents a client-side chat interface that manages chat messages.
 */
public class ClientChat {
    private final List<ChatMessage> chat;

    /**
     * Constructs a new ClientChat instance with an empty chat message list.
     */
    public ClientChat() {
        chat = new ArrayList<>();
    }

    /**
     * Adds a new message to the chat.
     *
     * @param newMessage The ChatMessage object to add to the chat.
     */
    public void addMessageToChat(ChatMessage newMessage) {
        chat.add(newMessage);
    }

    /**
     * Prints the chat messages to the console, formatted and wrapped based on the provided playerName.
     *
     * @param playerName The name of the player viewing the chat, used for formatting purposes.
     */
    public void printChat(String playerName) {
        if (chat.isEmpty()) {
            System.out.println("No messages in chat yet!");
        } else {
            int columnWidth = 50;
            System.out.println("\n----------------------------------------CHAT----------------------------------------------");
            System.out.printf("%-" + columnWidth + "s %s\n", DefaultValue.ANSI_PURPLE + "MESSAGES FROM OTHERS", "     MESSAGES FROM YOU" + DefaultValue.ANSI_RESET);
            for (ChatMessage message : chat) {
                List<String> wrappedLines;

                if (!message.getSender().equals(playerName)) {
                    wrappedLines = wrapText(DefaultValue.ANSI_YELLOW + "[from: \"" + message.getSender() + "\" to " + (message.getRecipient().equals(playerName) ? "you" : message.getRecipient()) + "]: " + DefaultValue.ANSI_RESET + message.getContent(), columnWidth);
                    for (String line : wrappedLines) {
                        System.out.printf("%-" + columnWidth + "s %s\n", line, "");
                    }

                } else {
                    wrappedLines = wrapText(message.getContent() + DefaultValue.ANSI_YELLOW + " [from: you to " + message.getRecipient() + "] " + DefaultValue.ANSI_RESET, columnWidth);
                    for (String line : wrappedLines) {
                        System.out.printf("%-" + columnWidth + "s %s\n", "", line);
                    }
                }
            }
            System.out.println("------------------------------------------------------------------------------------------\n");

        }
    }

    /**
     * Wraps the given text into lines of the specified column width.
     *
     * @param text        The text to wrap into lines.
     * @param columnWidth The width of each line in characters.
     * @return A list of strings where each string represents a wrapped line of text.
     */
    public List<String> wrapText(String text, int columnWidth) {
        List<String> lines = new ArrayList<>();
        while (text.length() > columnWidth) {
            int spaceIndex = text.lastIndexOf(' ', columnWidth);
            if (spaceIndex == -1) spaceIndex = columnWidth;
            lines.add(text.substring(0, spaceIndex));
            text = text.substring(spaceIndex + 1);
        }
        lines.add(text);
        return lines;
    }
}
