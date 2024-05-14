package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.PlayerResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceStrategyTest {
    private static Player player;
    private static ResourceStrategy FUNGIstrategy, ANIMALstrategy, INSECTstrategy, PLANTstrategy;


    @BeforeEach
    void setUp() {
        player = new Player("lazzaro");
        FUNGIstrategy = new ResourceStrategy(Resource.FUNGI);
        ANIMALstrategy = new ResourceStrategy(Resource.ANIMAL);
        INSECTstrategy = new ResourceStrategy(Resource.INSECT);
        PLANTstrategy = new ResourceStrategy(Resource.PLANT);


        //8 FUNGI
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);

        //2 ANIMAL
        player.addResource(Resource.ANIMAL);
        player.addResource(Resource.ANIMAL);
        player.addResource(Resource.ANIMAL);

        //0 INSECT
            player.addResource(Resource.INSECT);

        //3 PLANT
        player.addResource(Resource.PLANT);
        player.addResource(Resource.PLANT);
        player.addResource(Resource.PLANT);
        player.addResource(Resource.PLANT);
        player.addResource(Resource.PLANT);
        player.addResource(Resource.PLANT);





    }

    @Test
    void check() {
        int FUNGI, ANIMAL, INSECT, PLANT;
        FUNGI = FUNGIstrategy.check(player);
        ANIMAL =ANIMALstrategy.check(player);
        INSECT = INSECTstrategy.check(player);
        PLANT = PLANTstrategy.check(player);

        assertEquals(6,FUNGI);
        assertEquals(2,ANIMAL);
        assertEquals(0,INSECT);
        assertEquals(4,PLANT);

        System.out.println("FUNGI points: "+FUNGI);
        System.out.println("ANIMAL points: "+ANIMAL);
        System.out.println("INSECT points: "+INSECT);
        System.out.println("PLANT points: "+PLANT);
    }
}