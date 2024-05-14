package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.CardColor;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.Field;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LStrategyTest {
    private static Player player;
    private static LStrategy LStrategyRed, LStrategyBlue, LStrategyGreen, LStrategyPurple;
    @BeforeEach
    void setUp() {
        player = new Player("testPlayer");

        Field field = player.getField();

        StarterDeck starterDeck = new StarterDeck();
        ResourceDeck resourceDeck = new ResourceDeck();
        GoldenDeck goldenDeck = new GoldenDeck();

        LStrategyRed = new LStrategy(CardColor.RED);
        LStrategyBlue = new LStrategy(CardColor.BLUE);
        LStrategyGreen = new LStrategy(CardColor.GREEN);
        LStrategyPurple = new LStrategy(CardColor.PURPLE);

        //Origin
        field.getPositions().put(new Position(0,0), starterDeck.pick());

        //Red Strategy
        field.getPositions().put(new Position(-1,1), resourceDeck.pickById(11));
        field.getPositions().put(new Position(-2,2), resourceDeck.pickById(1));
        field.getPositions().put(new Position(-1,3), resourceDeck.pickById(12));
        field.getPositions().put(new Position(-2,4), resourceDeck.pickById(2));
        field.getPositions().put(new Position(-1,5), resourceDeck.pickById(13));
        field.getPositions().put(new Position(-2,6), resourceDeck.pickById(3));
        field.getPositions().put(new Position(-1,7), resourceDeck.pickById(14));
        field.getPositions().put(new Position(-2,8), resourceDeck.pickById(4));

        //Green Strategy
        field.getPositions().put(new Position(1,1), resourceDeck.pickById(31));
        field.getPositions().put(new Position(2,2), resourceDeck.pickById(15));
        field.getPositions().put(new Position(1,3), resourceDeck.pickById(32));
        field.getPositions().put(new Position(2,4), resourceDeck.pickById(16));
        field.getPositions().put(new Position(1,5), resourceDeck.pickById(33));
        field.getPositions().put(new Position(2,6), resourceDeck.pickById(17));
        field.getPositions().put(new Position(2,8), resourceDeck.pickById(18));
        field.getPositions().put(new Position(3,3), resourceDeck.pickById(34));
        field.getPositions().put(new Position(4,4), resourceDeck.pickById(19));
        field.getPositions().put(new Position(3,5), resourceDeck.pickById(35));
        field.getPositions().put(new Position(4,6), resourceDeck.pickById(20));
        field.getPositions().put(new Position(3,7), resourceDeck.pickById(36));

        //Blue Strategy
        field.getPositions().put(new Position(-1,-1), resourceDeck.pickById(5));
        field.getPositions().put(new Position(-2,-2), resourceDeck.pickById(21));
        field.getPositions().put(new Position(-1,-3), resourceDeck.pickById(6));
        field.getPositions().put(new Position(-2,-4), resourceDeck.pickById(22));
        field.getPositions().put(new Position(-1,-5), resourceDeck.pickById(23));
        field.getPositions().put(new Position(-2,-6), resourceDeck.pickById(24));



        //Purple Strategy
        field.getPositions().put(new Position(1,-1), resourceDeck.pickById(24));
        field.getPositions().put(new Position(2,-2), resourceDeck.pickById(37));
        field.getPositions().put(new Position(3,-3), resourceDeck.pickById(25));
        field.getPositions().put(new Position(4,-4), resourceDeck.pickById(38));
        field.getPositions().put(new Position(3,-5), resourceDeck.pickById(26));
        field.getPositions().put(new Position(4,-6), resourceDeck.pickById(39));

    }

    @Test
    void check() {
        int redPoints, greenPoints, bluePoints, purplePoints;
        redPoints = LStrategyRed.check(player);
        greenPoints = LStrategyGreen.check(player);
        bluePoints = LStrategyBlue.check(player);
        purplePoints = LStrategyPurple.check(player);

        assertEquals(6, redPoints);
        assertEquals(9, greenPoints);
        assertEquals(3, bluePoints);
        assertEquals(3, purplePoints);

        System.out.println("Red points: "+redPoints);
        System.out.println("Green points: "+greenPoints);
        System.out.println("Blue points: "+bluePoints);
        System.out.println("Purple points: "+purplePoints);
    }
}