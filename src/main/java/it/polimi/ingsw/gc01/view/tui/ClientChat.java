package it.polimi.ingsw.gc01.view.tui;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.util.ArrayList;
import java.util.List;

public class ClientChat {
    private List<ChatMessage> chat;

    public ClientChat(List<ChatMessage> chat){
        this.chat = chat;
    }
    public void addMessageToChat(ChatMessage newMessage) {
        chat.add(newMessage);
    }

    public void printChat(String playerName){
        if(chat.isEmpty()){
            System.out.println("No messages in chat yet!");
        }else{
            int columnWidth = 50;
            System.out.println("\n----------------------------------CHAT----------------------------------------");
            System.out.printf("%-" + columnWidth + "s %s\n", DefaultValue.ANSI_PURPLE+ "MESSAGES FROM OTHERS", "MESSAGES FROM YOU"+DefaultValue.ANSI_RESET );
            for (ChatMessage message : chat) {
                List<String> wrappedLines;

                if(!message.getSender().equals(playerName)){
                    wrappedLines = wrapText(DefaultValue.ANSI_YELLOW + "[from: \"" + message.getSender() + "\" to " + (message.getRecipient().equals(playerName) ? "you" : message.getRecipient()) + "]: " +DefaultValue.ANSI_RESET+ message.getContent(), columnWidth);
                    for (String line : wrappedLines) {
                        System.out.printf("%-" + columnWidth + "s %s\n", line, "");}

                }else {
                    wrappedLines = wrapText(message.getContent() + DefaultValue.ANSI_YELLOW + " [from: you to " + message.getRecipient() + "] "+ DefaultValue.ANSI_RESET, columnWidth);
                    for (String line : wrappedLines) {
                        System.out.printf("%-" + columnWidth + "s %s\n", "", line);
                    }
                }
            }
            System.out.println("------------------------------------------------------------------------------\n");

        }
    }


    public List<String> wrapText(String text, int columnWidth) {
        List<String> lines = new ArrayList<>();
        while (text.length() > columnWidth) {
            int spaceIndex = text.lastIndexOf(' ', columnWidth);
            if (spaceIndex == -1) spaceIndex = columnWidth;
            lines.add(text.substring(0, spaceIndex).trim());
            text = text.substring(spaceIndex).trim();
        }
        lines.add(text);
        return lines;
    }
}
