package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayerInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.cards.StarterCard;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
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
        p.add(new Player("Player5", PlayerColor.YELLOW));
    }

    @Test
    void addPlayer() {
        assert(testController.getWaitingRoom().getPlayers().isEmpty());
        testController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        assertThrows(PlayerAlreadyInException.class, () -> { testController.addPlayer(p.get(0).getName(), p.get(0).getColor());});
        testController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testController.addPlayer(p.get(3).getName(), p.get(3).getColor());
        assertThrows(MaxPlayerInException.class, () -> { testController.addPlayer(p.get(4).getName(), p.get(4).getColor());});

    }

    @Test
    void prepareGame() {
        testController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        testController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testController.addPlayer(p.get(3).getName(), p.get(3).getColor());
        testController.prepareGame();
        assertNotNull(testController.getRoom());
        for (Player player : testController.getRoom().getPlayers()) {
            assert(player.getHand().get(0) instanceof StarterCard);
        }
    }

    @Test
    void distributeCards() {
        testController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        testController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testController.addPlayer(p.get(3).getName(), p.get(3).getColor());
        testController.prepareGame();
        for (Player player : testController.getRoom().getPlayers()) {
            testController.flipCard(player.getHand().get(0));
            player.playCard(player.getHand().get(0), new Position(0,0));
            assertNotNull(player.getField().getPositions().get(new Position(0,0)));
            assert(player.getField().getPositions().get(new Position(0,0)) instanceof StarterCard);
        }
        testController.distributeCards();
        for (Player player : testController.getRoom().getPlayers()) {
            assertEquals(3, player.getHand().size());
            assert(player.getHand().get(0) instanceof ResourceCard);
            assert(player.getHand().get(1) instanceof ResourceCard);
            assert(player.getHand().get(2) instanceof GoldenCard);
            assertEquals(2, player.getPossibleObjective().size());
        }
    }

    @Test
    void startGame() {
        testController.startGame();
        assertEquals(GameState.RUNNING, testController.getState());
    }
}