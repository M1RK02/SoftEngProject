package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.CornerValue;
import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.corners.Corner;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static it.polimi.ingsw.gc01.model.CornerValue.EMPTY;
import static it.polimi.ingsw.gc01.model.CornerValue.FULL;
import static org.junit.jupiter.api.Assertions.*;

class GoldenCardTest {
    private static Player player;
    private static GoldenDeck goldenDeck;
    private static ResourceDeck resourceDeck;
    private static StarterDeck starterDeck;
    private static Field field;



    @BeforeEach
    void setUp() {
        player = new Player("testPlayer", null);
        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        goldenDeck = new GoldenDeck();
        field = player.getField();
    }

    @Test
    void checkRequirements() {
        //FUNGI: 3, ANIMAL: 1, INSECT: 0, PLANT: 0
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.ANIMAL);

        assert(goldenDeck.pickById(41).checkRequirements(player));
        assert(!goldenDeck.pickById(42).checkRequirements(player));
        assert(!goldenDeck.pickById(43).checkRequirements(player));
        assert(goldenDeck.pickById(44).checkRequirements(player));
        assert(!goldenDeck.pickById(45).checkRequirements(player));
        assert(!goldenDeck.pickById(46).checkRequirements(player));
        assert(goldenDeck.pickById(47).checkRequirements(player));
        assert(!goldenDeck.pickById(50).checkRequirements(player));

        //FUNGI: 5, ANIMAL: 1, INSECT: 1, PLANT: 1
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.PLANT);
        player.addResource(Resource.INSECT);

        assert(goldenDeck.pickById(42).checkRequirements(player));
        assert(goldenDeck.pickById(43).checkRequirements(player));
        assert(goldenDeck.pickById(50).checkRequirements(player));

    }

    @Test
    void calculatePoints() {
        //Origin
        field.getPositions().put(new Position(0,0), starterDeck.get());
        addRes(starterDeck.pick(), new Position(0,0));

        //scoreCondition.equals(Item.QUILL)
        field.getPositions().put(new Position(-1,-1), resourceDeck.pickById(5));
        addRes(resourceDeck.pickById(5), new Position(-1,-1));
        field.getPositions().put(new Position(-1,1), resourceDeck.pickById(15));
        addRes(resourceDeck.pickById(15), new Position(-1,1));
        field.getPositions().put(new Position(-2, -2), goldenDeck.pickById(51));
        addRes(goldenDeck.pickById(51), new Position(-2, -2));
        assertEquals(3, goldenDeck.pickById(51).calculatePoints(player, new Position(-2,-2)));

        //scoreCondition.equals(Item.INKWELL)
        field.getPositions().put(new Position(0,-2), resourceDeck.pickById(6));
        addRes(resourceDeck.pickById(6), new Position(0,-2));
        field.getPositions().put(new Position(0,2), goldenDeck.pickById(42));
        addRes(goldenDeck.pickById(42), new Position(0,2));
        assertEquals(2, goldenDeck.pickById(42).calculatePoints(player, new Position(0,2)));

        //scoreCondition.equals(Item.MANUSCRIPT)
        field.getPositions().put(new Position(-3,-3), resourceDeck.pickById(7));
        addRes(resourceDeck.pickById(7), new Position(-3,-3));
        field.getPositions().put(new Position(-4,-2), goldenDeck.pickById(62));
        addRes(goldenDeck.pickById(62), new Position(-4,-2));
        assertEquals(2, goldenDeck.pickById(62).calculatePoints(player, new Position(-4,-2)));

        //scoreCondition.equals(ConditionType.EMPTY)
        field.getPositions().put(new Position(-5,-3), goldenDeck.pickById(79));
        addRes(goldenDeck.pickById(79), new Position(-5,-3));
        assertEquals(3, goldenDeck.pickById(79).calculatePoints(player, new Position(-5,-3)));

        //scoreCondition.equals(ConditionType.CORNER)
        field.getPositions().put(new Position(1,1), resourceDeck.pickById(2));
        assertEquals(2, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

        field.getPositions().put(new Position(1,-1), resourceDeck.pickById(4));
        assertEquals(4, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

        field.getPositions().put(new Position(2,-2), resourceDeck.pickById(1));
        field.getPositions().put(new Position(2,2), resourceDeck.pickById(3));
        field.getPositions().put(new Position(3,1), resourceDeck.pickById(5));
        assertEquals(6, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

        field.getPositions().put(new Position(3,-1), resourceDeck.pickById(6));
        assertEquals(8, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

    }


    private void addRes(PlayableCard card, Position p){
        //Adding Resources
        Map<CornerPosition, Corner> corners = card.getCorners();
        PlayableCard c;
        Position nextPosition;
        for (Corner corner : corners.values()) {
            if (!corner.getResource().equals(EMPTY) && !corner.getResource().equals(CornerValue.FULL)) {
                player.addResource((PlayerResource) corner.getResource());
            }
        }

        //Removing Resources from covered cards
        nextPosition = new Position(p.getX() + 1, p.getY() + 1);
        if (field.getPositions().containsKey(nextPosition)){
            c = field.getPositions().get(nextPosition);
            Corner bottomLeft = c.getCorners().get(CornerPosition.BOTTOM_LEFT);
            if (bottomLeft.getResource() != EMPTY && bottomLeft.getResource() != FULL){
                player.removeResource((PlayerResource) bottomLeft.getResource());
            }
        }

        nextPosition = new Position(p.getX() - 1, p.getY() + 1);
        if (field.getPositions().containsKey(nextPosition)){
            c = field.getPositions().get(nextPosition);
            Corner bottomRight = c.getCorners().get(CornerPosition.BOTTOM_RIGHT);
            if (bottomRight.getResource() != EMPTY && bottomRight.getResource() != FULL){
                player.removeResource((PlayerResource) bottomRight.getResource());
            }
        }

        nextPosition = new Position(p.getX() + 1, p.getY() - 1);
        if (field.getPositions().containsKey(nextPosition)){
            c = field.getPositions().get(nextPosition);
            Corner topLeft = c.getCorners().get(CornerPosition.TOP_LEFT);
            if (topLeft.getResource() != EMPTY && topLeft.getResource() != FULL){
                player.removeResource((PlayerResource) topLeft.getResource());
            }
        }

        nextPosition = new Position(p.getX() - 1, p.getY() - 1);
        if (field.getPositions().containsKey(nextPosition)){
            c = field.getPositions().get(nextPosition);
            Corner topRight = c.getCorners().get(CornerPosition.TOP_RIGHT);
            if (topRight.getResource() != EMPTY && topRight.getResource() != FULL){
                player.removeResource((PlayerResource) topRight.getResource());
            }
        }
    }

}