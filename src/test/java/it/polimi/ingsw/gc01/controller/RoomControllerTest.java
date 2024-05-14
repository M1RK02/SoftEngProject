package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayersInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.cards.StarterCard;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomControllerTest {
    public static RoomController testRoomController;
    public static final List<Player> p = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testRoomController = new RoomController();
        p.add(new Player("Player1"));
        p.add(new Player("Player2"));
        p.add(new Player("Player3"));
        p.add(new Player("Player4"));
        p.add(new Player("Player5"));
    }

    @Test
    void addPlayer() {
        assert(testRoomController.getWaitingRoom().getPlayers().isEmpty());
        testRoomController.addPlayer(p.get(0).getName());
        assertThrows(PlayerAlreadyInException.class, () -> { testRoomController.addPlayer(p.get(0).getName());});
        testRoomController.addPlayer(p.get(1).getName());
        testRoomController.addPlayer(p.get(2).getName());
        testRoomController.addPlayer(p.get(3).getName());
        assertThrows(MaxPlayersInException.class, () -> { testRoomController.addPlayer(p.get(4).getName());});

    }

    @Test
    void prepareGame() {
        testRoomController.addPlayer(p.get(0).getName());
        testRoomController.addPlayer(p.get(1).getName());
        testRoomController.addPlayer(p.get(2).getName());
        testRoomController.addPlayer(p.get(3).getName());
        testRoomController.prepareGame();
        assertNotNull(testRoomController.getRoom());
        for (Player player : testRoomController.getRoom().getPlayers()) {
            assert(player.getHand().get(0) instanceof StarterCard);
        }
    }

    @Test
    void distributeCards() {
        testRoomController.addPlayer(p.get(0).getName());
        testRoomController.addPlayer(p.get(1).getName());
        testRoomController.addPlayer(p.get(2).getName());
        testRoomController.addPlayer(p.get(3).getName());
        testRoomController.prepareGame();
        for (Player player : testRoomController.getRoom().getPlayers()) {
            testRoomController.flipCard(player.getHand().get(0));
            player.playCard(player.getHand().get(0), new Position(0,0));
            assertNotNull(player.getField().getPositions().get(new Position(0,0)));
            assert(player.getField().getPositions().get(new Position(0,0)) instanceof StarterCard);
        }
        testRoomController.distributeCards();
        for (Player player : testRoomController.getRoom().getPlayers()) {
            assertEquals(3, player.getHand().size());
            assert(player.getHand().get(0) instanceof ResourceCard);
            assert(player.getHand().get(1) instanceof ResourceCard);
            assert(player.getHand().get(2) instanceof GoldenCard);
            assertEquals(2, player.getPossibleObjectives().size());
        }
    }

    @Test
    void startGame() {
        testRoomController.startGame();
        assertEquals(GameState.RUNNING, testRoomController.getState());
    }
}