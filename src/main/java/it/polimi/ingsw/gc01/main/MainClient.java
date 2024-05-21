package it.polimi.ingsw.gc01.main;

import it.polimi.ingsw.gc01.UI.TUI;

import java.rmi.RemoteException;

public class MainClient {
    public static void main(String[] args) throws RemoteException {
        TUI tui = new TUI();
        tui.printTitle();
        String playerName = tui.askPlayerName();
        tui.askAndSetIP();
        tui.askAndSetPort();
        tui.createRMIClient(playerName);
        tui.askModalityToEnterGame();
    }
}