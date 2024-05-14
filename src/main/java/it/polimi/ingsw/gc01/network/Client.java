package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;

public interface Client {

    public void connect();

    public void createGame();

    public void joinGame();

    public void joinFirstGame();

    public void chooseColor(PlayerColor color);

    public void switchReady();

    public void chooseSecretObjective(int cardId);

    public void flipCard(int card);

    public void playCard (int cardId, int x, int y);

    public void drawCard (TablePosition card);

    public void leave();
}
