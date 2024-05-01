package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayerInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    public static Controller testController;
    public static final List<Player> p = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testController = new Controller();
        p.add(new Player("Player1", PlayerColor.BLUE));
        p.add(new Player("Player2", PlayerColor.GREEN));
        p.add(new Player("Player3", PlayerColor.RED));
        p.add(new Player("Player4", PlayerColor.YELLOW));
    }

    @Test
    void addPlayer() {
        assert(testController.getWaitingRoom().getPlayers().isEmpty());
        testController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        assertThrows(PlayerAlreadyInException.class, () -> { testController.addPlayer("Prend", PlayerColor.BLUE);});
        testController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testController.addPlayer(p.get(3).getName(), p.get(3).getColor());
        assertThrows(MaxPlayerInException.class, () -> { testController.addPlayer("Franco", PlayerColor.YELLOW);});

    }

    @Test
    void distributeCards() {
    }

    @Test
    void prepareGame() {
    }

    @Test
    void startGame() {
    }

    @Test
    void nextPlayer() {
    }

    @Test
    void calculateStrategy() {
    }
}