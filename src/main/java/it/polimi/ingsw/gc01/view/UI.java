package it.polimi.ingsw.gc01.view;

public interface UI extends Runnable {
    void showRoom(String roomId);
    void showError(String error);
    void showServiceMessage(String message);
}