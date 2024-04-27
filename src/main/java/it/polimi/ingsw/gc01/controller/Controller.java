package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.*;
import it.polimi.ingsw.gc01.model.strategy.*;

public class Controller {
    private Room model;

    public Controller(Room model){
        this.model = model;
    }

    public Room getModel() {
        return model;
    }

}
