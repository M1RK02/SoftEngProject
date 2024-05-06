package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTestRunning {

    public static Controller testController;
    public static final List<Player> players = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testController = new Controller();
        players.add(new Player("Player1", PlayerColor.BLUE));
        players.add(new Player("Player2", PlayerColor.GREEN));
        players.add(new Player("Player3", PlayerColor.RED));
        players.add(new Player("Player4", PlayerColor.YELLOW));
        testController.addPlayer(players.get(0).getName(), players.get(0).getColor());
        testController.addPlayer(players.get(1).getName(), players.get(1).getColor());
        testController.addPlayer(players.get(2).getName(), players.get(2).getColor());
        testController.addPlayer(players.get(3).getName(), players.get(3).getColor());
        testController.prepareGame();
        for (Player player : testController.getRoom().getPlayers()) {
            Random generatore = new Random();
            int y = generatore.nextInt(2);
            //Choose random side of starterCard and plays it
            if (y == 1) {
                (player.getHand().get(0)).setFront(true);
            }
            player.playCard(player.getHand().get(0), new Position(0,0));
        }
        testController.distributeCards();
        for (Player player : testController.getRoom().getPlayers()) {
            Random generatore = new Random();
            int x = generatore.nextInt(2);
            //Choose random side of starterCard and plays it
            player.setSecretObjective(player.getPossibleObjective().get(x));
        }
        testController.startGame();
    }

    @Test
    void playCard() {
        testController.playCard(testController.getRoom().getCurrentPlayer().getHand().get(0), new Position(-1,-1));
        testController.playCard(testController.getRoom().getCurrentPlayer().getHand().get(0), new Position(-1,1));
        testController.playCard(testController.getRoom().getCurrentPlayer().getHand().get(0), new Position(1,-1));
        assertEquals(0, testController.getRoom().getCurrentPlayer().getHand().size());
        assertEquals(4, testController.getRoom().getCurrentPlayer().getField().getPositions().size());
    }

    @Test
    void nextPlayer() {
        for (Player player : testController.getRoom().getPlayers()) {
            assertEquals(player.getName(), testController.getRoom().getCurrentPlayer().getName());;
            testController.nextPlayer();
        }
    }

    @Test
    void flipCard() {
        assertFalse(testController.getRoom().getCurrentPlayer().getHand().get(0).isFront());
        testController.flipCard(testController.getRoom().getCurrentPlayer().getHand().get(0));
        assertTrue(testController.getRoom().getCurrentPlayer().getHand().get(0).isFront());
    }

    @Test
    void drawCard() {
        ResourceCard drawnCard;
        Position randomPosition;
        for (TablePosition position : testController.getRoom().getDrawableCards().keySet()){
            randomPosition = getRandomAvaliablePosition();
            testController.playCard(testController.getRoom().getCurrentPlayer().getHand().get(0), randomPosition);
            drawnCard = testController.getRoom().getDrawableCards().get(position);
            testController.drawCard(position);
            assertEquals(drawnCard, testController.getRoom().getCurrentPlayer().getHand().get(2));
            assertNotEquals(drawnCard, testController.getRoom().getDrawableCards().get(position));
            assertEquals(6, testController.getRoom().getDrawableCards().size());
        }
    }

    @Test
    void changeStateIfTwenty() {
        testController.getRoom().getCurrentPlayer().addPoints(19);
        testController.changeStateIfTwenty();
        assertEquals(GameState.RUNNING, testController.getState());
        testController.getRoom().getCurrentPlayer().addPoints(1);
        testController.changeStateIfTwenty();
        assertEquals(GameState.LAST_CIRCLE, testController.getState());

    }

    @Test
    void endGame() {
        testController.getRoom().getCurrentPlayer().addPoints(22);
        testController.nextPlayer();
        testController.getRoom().getCurrentPlayer().addPoints(19);
        testController.nextPlayer();
        testController.getRoom().getCurrentPlayer().addPoints(23);
        testController.nextPlayer();
        testController.getRoom().getCurrentPlayer().addPoints(23);
        testController.changeStateIfTwenty();
        testController.endGame();
        assertEquals(players.get(2).getName(), testController.getRoom().getWinners().get(0).getName());
        assertEquals(players.get(3).getName(), testController.getRoom().getWinners().get(1).getName());
    }

    /**
     *
     * @return a random position between the set of AvailablePosition from each player field
     */
    private Position getRandomAvaliablePosition(){
        Set<Position> availablePosition = testController.getRoom().getCurrentPlayer().getField().getAvailablePositions();
        int size = availablePosition.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(Position p : availablePosition)
        {
            if (i == item)
                return p;
            i++;
        }
        return null;
    }
}