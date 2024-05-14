package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.CardColor;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.cards.StarterCard;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ObjectiveDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.Field;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StairStrategyTest {
    private static Player player;
    private static StairStrategy stairStrategyRed, stairStrategyBlue, stairStrategyGreen, stairStrategyPurple;

    @BeforeEach
    void setUp() {
        player = new Player("testPlayer");

        Field field = player.getField();

        StarterDeck starterDeck = new StarterDeck();
        ResourceDeck resourceDeck = new ResourceDeck();
        GoldenDeck goldenDeck = new GoldenDeck();

        stairStrategyRed = new StairStrategy(CardColor.RED);
        stairStrategyBlue = new StairStrategy(CardColor.BLUE);
        stairStrategyGreen = new StairStrategy(CardColor.GREEN);
        stairStrategyPurple = new StairStrategy(CardColor.PURPLE);

        //Origin
        field.getPositions().put(new Position(0,0), starterDeck.pick());

        //Carte rosse (punti = 4)
        field.getPositions().put(new Position(1,1), resourceDeck.pick());
        field.getPositions().put(new Position(2,2), resourceDeck.pick());
        field.getPositions().put(new Position(3,3), resourceDeck.pick());
        field.getPositions().put(new Position(2,0), resourceDeck.pick());
        field.getPositions().put(new Position(3,1), resourceDeck.pick());
        field.getPositions().put(new Position(4,2), resourceDeck.pick());

        //Carte verdi (punti = 4)
        field.getPositions().put(new Position(-1,1), resourceDeck.pickById(11));
        field.getPositions().put(new Position(-2,2), resourceDeck.pickById(12));
        field.getPositions().put(new Position(-3,3), resourceDeck.pickById(13));
        field.getPositions().put(new Position(-4,4), resourceDeck.pickById(26));
        field.getPositions().put(new Position(-5,5), resourceDeck.pickById(15));
        field.getPositions().put(new Position(-6,6), resourceDeck.pickById(16));
        field.getPositions().put(new Position(-7,7), resourceDeck.pickById(14));

        //Carte blu (punti = 2)
        field.getPositions().put(new Position(-1,-1), resourceDeck.pickById(21));
        field.getPositions().put(new Position(-2,-2), resourceDeck.pickById(22));
        field.getPositions().put(new Position(-3,-3), resourceDeck.pickById(23));
        field.getPositions().put(new Position(-4,-4), resourceDeck.pickById(24));
        field.getPositions().put(new Position(-5,-5), resourceDeck.pickById(25));
        field.getPositions().put(new Position(-6,-6), resourceDeck.pickById(17));
        field.getPositions().put(new Position(-7,-7), resourceDeck.pickById(26));

        //Carte viola (punti = 4)
        field.getPositions().put(new Position(1,-1), goldenDeck.pickById(71));
        field.getPositions().put(new Position(2,-2), goldenDeck.pickById(72));
        field.getPositions().put(new Position(3,-3), goldenDeck.pickById(73));
        field.getPositions().put(new Position(2,-4), goldenDeck.pickById(74));
        field.getPositions().put(new Position(3,-5), goldenDeck.pickById(75));
        field.getPositions().put(new Position(4,-6), goldenDeck.pickById(76));
    }

    @Test
    void check(){
        int redPoints, greenPoints, bluePoints, purplePoints;
        redPoints = stairStrategyRed.check(player);
        greenPoints = stairStrategyGreen.check(player);
        bluePoints = stairStrategyBlue.check(player);
        purplePoints = stairStrategyPurple.check(player);

        assertEquals(4, redPoints);
        assertEquals(4, greenPoints);
        assertEquals(2, bluePoints);
        assertEquals(4, purplePoints);

        System.out.println("Red points: "+redPoints);
        System.out.println("Green points: "+greenPoints);
        System.out.println("Blue points: "+bluePoints);
        System.out.println("Purple points: "+purplePoints);
    }
}