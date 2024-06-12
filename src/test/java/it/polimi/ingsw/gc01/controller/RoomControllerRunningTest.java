package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.VirtualViewStub;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.model.room.Room;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RoomControllerRunningTest {
    private static VirtualViewStub client;
    private static RoomController controller;
    private static List<String> playerNames;
    @BeforeEach
    void setup(){
        controller = new RoomController();
        client = new VirtualViewStub();
        playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");
        playerNames.add("p3");
        playerNames.add("p4");

        //Adding players
        controller.addPlayer(playerNames.get(0), client);
        controller.addPlayer(playerNames.get(1), client);
        assertEquals(GameState.WAITING, controller.getState());

        //Players ready
        controller.switchReady(playerNames.get(0));
        controller.switchReady(playerNames.get(1));
        assertEquals(GameState.STARTER_SELECTION, controller.getState());

        //Playing starters
        assertEquals(controller.getRoom().getCurrentPlayer().getName(), playerNames.get(0));
        assertEquals(0, controller.getRoom().getCurrentPlayer().getField().getPositions().values().stream().toList().size());
        controller.playCard(controller.getRoom().getCurrentPlayer().getName(), controller.getRoom().getCurrentPlayer().getHand().getFirst().getId(), new Position(0,0));
        assertEquals(1, controller.getRoom().getPlayers().get(0).getField().getPositions().values().stream().toList().size());

        assertEquals(controller.getRoom().getCurrentPlayer().getName(), playerNames.get(1));
        assertEquals(0, controller.getRoom().getCurrentPlayer().getField().getPositions().values().stream().toList().size());
        controller.playCard(controller.getRoom().getCurrentPlayer().getName(), controller.getRoom().getCurrentPlayer().getHand().getFirst().getId(), new Position(0,0));
        assertEquals(1, controller.getRoom().getPlayers().get(1).getField().getPositions().values().stream().toList().size());

        assertEquals(GameState.COLOR_SELECTION, controller.getState());

        //Choosing colors
        assertNull(controller.getRoom().getPlayers().get(0).getColor());
        assertNull(controller.getRoom().getPlayers().get(1).getColor());
        assertEquals(4, controller.getRoom().getAvailableColors().size());
        controller.chooseColor(controller.getRoom().getCurrentPlayer().getName(), PlayerColor.RED);
        assertEquals(PlayerColor.RED, controller.getRoom().getPlayers().get(0).getColor());
        assertEquals(3, controller.getRoom().getAvailableColors().size());
        controller.chooseColor(controller.getRoom().getCurrentPlayer().getName(), PlayerColor.BLUE);
        assertEquals(PlayerColor.BLUE, controller.getRoom().getPlayers().get(1).getColor());
        assertEquals(2, controller.getRoom().getAvailableColors().size());

        assertEquals(GameState.OBJECTIVE_SELECTION, controller.getState());

        //Choosing objectives
        assertEquals(controller.getRoom().getCurrentPlayer().getName(), playerNames.get(0));
        assertEquals(3, controller.getRoom().getCurrentPlayer().getHand().size());
        assertEquals(2, controller.getRoom().getCurrentPlayer().getPossibleObjectives().size());
        controller.chooseSecretObjective(controller.getRoom().getCurrentPlayer().getName(), controller.getRoom().getCurrentPlayer().getPossibleObjectives().get(0).getId());
        assertEquals(controller.getRoom().getPlayers().get(0).getPossibleObjectives().get(0), controller.getRoom().getPlayers().get(0).getSecretObjective());

        assertEquals(controller.getRoom().getCurrentPlayer().getName(), playerNames.get(1));
        assertEquals(3, controller.getRoom().getCurrentPlayer().getHand().size());
        assertEquals(2, controller.getRoom().getCurrentPlayer().getPossibleObjectives().size());
        controller.chooseSecretObjective(controller.getRoom().getCurrentPlayer().getName(), controller.getRoom().getCurrentPlayer().getPossibleObjectives().get(0).getId());
        assertEquals(controller.getRoom().getPlayers().get(1).getPossibleObjectives().get(0), controller.getRoom().getPlayers().get(1).getSecretObjective());

        assertEquals(GameState.RUNNING, controller.getState());

    }

    @Test
    void playAndDraw(){
        Room room = controller.getRoom();
        while (controller.getState().equals(GameState.RUNNING)){
            Player currentPlayer = controller.getRoom().getCurrentPlayer();
            assertEquals(3, currentPlayer.getHand().size());
            Random random = new Random();
            int x = random.nextInt(3);
            if (currentPlayer.getHand().get(x) instanceof GoldenCard){
                if (!((GoldenCard) currentPlayer.getHand().get(x)).checkRequirements(currentPlayer)){
                    if (currentPlayer.getHand().get(x).isFront()){
                        controller.flipCard(currentPlayer.getName(), currentPlayer.getHand().get(x).getId());
                    }
                }
            }
            controller.playCard(currentPlayer.getName(), currentPlayer.getHand().get(x).getId(), getRandomAvaliablePosition());
            assertEquals(2, currentPlayer.getHand().size());

            TablePosition position = getRandomDrawable();
            if (position != null){
                controller.drawCard(currentPlayer.getName(), position);
                assertEquals(3, currentPlayer.getHand().size());
            }
            else {
                controller.nextPlayer();
            }

            if (room.getResourceDeck().isEmpty() && room.getGoldenDeck().isEmpty() && currentPlayer.equals(room.getPlayers().get(0))){
                assertEquals(GameState.LAST_CIRCLE, controller.getState());
            }
        }

        while (controller.getState().equals(GameState.LAST_CIRCLE) ){
            Player currentPlayer = controller.getRoom().getCurrentPlayer();
            if (currentPlayer.getHand().get(0) instanceof GoldenCard){
                if (!((GoldenCard) currentPlayer.getHand().get(0)).checkRequirements(currentPlayer)){
                    if (currentPlayer.getHand().get(0).isFront()){
                        controller.flipCard(currentPlayer.getName(), currentPlayer.getHand().get(0).getId());
                    }
                }
            }
            controller.playCard(currentPlayer.getName(), currentPlayer.getHand().get(0).getId(), getRandomAvaliablePosition());
            controller.nextPlayer();
        }
        assertEquals(GameState.ENDED, controller.getState());
    }


    /**
     *
     * @return a random position between the set of AvailablePosition from each player field
     */
    private Position getRandomAvaliablePosition(){
        Set<Position> availablePosition = controller.getRoom().getCurrentPlayer().getField().getAvailablePositions();
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

    private TablePosition getRandomDrawable(){
        Set<TablePosition> availablePosition = Arrays.stream(TablePosition.values()).collect(Collectors.toSet());
        int size = availablePosition.size();
        int item = new Random().nextInt(size);
        int i = 0;
        boolean deckEmpty = false;


        Map<TablePosition, ResourceCard> drawableCards = controller.getRoom().getDrawableCards();
        for (TablePosition pos : TablePosition.values()){
            if (drawableCards.get(pos) == null){
                deckEmpty = true;
            }
        }

        if (deckEmpty){
            for (TablePosition pos : TablePosition.values()){
                if (drawableCards.get(pos) != null){
                    return pos;
                }
            }
        }

        for(TablePosition p : availablePosition)
        {
            if (i == item){
                if (drawableCards.get(p) == null){
                    return null;
                }
                else {
                    return p;
                }
            }
            i++;
        }
        return null;
    }
}