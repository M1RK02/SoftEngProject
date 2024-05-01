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
    void nextPlayer() {
    }

    @Test
    void calculateStrategy() {
    }

    @Test
    void flipCard() {
    }

    @Test
    void playCard() {
    }

    @Test
    void drawCard() {
    }

    @Test
    void changeStateIfTwenty() {
    }

    @Test
    void endGame() {
    }
}