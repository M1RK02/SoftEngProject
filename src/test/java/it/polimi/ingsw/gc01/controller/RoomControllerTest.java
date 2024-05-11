package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayersInException;
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

class RoomControllerTest {
    public static RoomController testRoomController;
    public static final List<Player> p = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testRoomController = new RoomController();
        p.add(new Player("Player1", PlayerColor.BLUE));
        p.add(new Player("Player2", PlayerColor.GREEN));
        p.add(new Player("Player3", PlayerColor.RED));
        p.add(new Player("Player4", PlayerColor.YELLOW));
        p.add(new Player("Player5", PlayerColor.YELLOW));
    }

    @Test
    void addPlayer() {
        assert(testRoomController.getWaitingRoom().getPlayers().isEmpty());
        testRoomController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        assertThrows(PlayerAlreadyInException.class, () -> { testRoomController.addPlayer(p.get(0).getName(), p.get(0).getColor());});
        testRoomController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testRoomController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testRoomController.addPlayer(p.get(3).getName(), p.get(3).getColor());
        assertThrows(MaxPlayersInException.class, () -> { testRoomController.addPlayer(p.get(4).getName(), p.get(4).getColor());});

    }

    @Test
    void prepareGame() {
        testRoomController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        testRoomController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testRoomController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testRoomController.addPlayer(p.get(3).getName(), p.get(3).getColor());
        testRoomController.prepareGame();
        assertNotNull(testRoomController.getRoom());
        for (Player player : testRoomController.getRoom().getPlayers()) {
            assert(player.getHand().get(0) instanceof StarterCard);
        }
    }

    @Test
    void distributeCards() {
        testRoomController.addPlayer(p.get(0).getName(), p.get(0).getColor());
        testRoomController.addPlayer(p.get(1).getName(), p.get(1).getColor());
        testRoomController.addPlayer(p.get(2).getName(), p.get(2).getColor());
        testRoomController.addPlayer(p.get(3).getName(), p.get(3).getColor());
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
            assertEquals(2, player.getPossibleObjective().size());
        }
    }

    @Test
    void startGame() {
        testRoomController.startGame();
        assertEquals(GameState.RUNNING, testRoomController.getState());
    }
}