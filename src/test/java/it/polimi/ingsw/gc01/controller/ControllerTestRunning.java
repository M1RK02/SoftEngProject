package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTestRunning {

    public static Controller testController;
    public static final List<Player> p = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testController = new Controller();
        p.add(new Player("Player1", PlayerColor.BLUE));
        p.add(new Player("Player2", PlayerColor.GREEN));
        p.add(new Player("Player3", PlayerColor.RED));
        p.add(new Player("Player4", PlayerColor.YELLOW));
        testController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        testController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testController.addPlayer(p.get(3).getName(), p.get(3).getColor());
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
        Random random = new Random();
        for (int i = 0; i < 40; i++){
            int z = random.nextInt(5);
            testController.playCard(testController.getRoom().getCurrentPlayer().getHand().get(0), getRandomAvaliablePosition());
            testController.drawCard(testController.getRoom().getDrawableCards().get(z));
            assertEquals(3, testController.getRoom().getCurrentPlayer().getHand().size());
            testController.nextPlayer();
        }

        for (Player p : testController.getRoom().getPlayers()){
            assertEquals(11, p.getField().getPositions().size());
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
        assertEquals(p.get(2).getName(), testController.getRoom().getWinners().get(0).getName());
        assertEquals(p.get(3).getName(), testController.getRoom().getWinners().get(1).getName());
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