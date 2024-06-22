package it.polimi.ingsw.gc01.view.tui;

import it.polimi.ingsw.gc01.model.ChatMessage;

import java.util.List;

public class ClientChat {
    private List<ChatMessage> chat;

    public ClientChat(List<ChatMessage> chat){
        this.chat = chat;
    }
    public void addMessageToChat(ChatMessage newMessage) {
        chat.add(newMessage);
    }

    public void printChat(){
        if(chat.isEmpty()){
            System.out.println("No messages in chat yet!");
        }else{
            for (ChatMessage message : chat) {
               System.out.println("[from: \"" + message.getSender() + "\"]: " + message.getContent());
            }
        }
    }
}
