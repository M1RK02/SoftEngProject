package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.*;

public class ResourceDeck extends Deck{
    public ResourceDeck() {
        super("Resource");
    }

    public ResourceCard pick() {
        return (ResourceCard) super.pick();
    }

    public ResourceCard get(){
        return (ResourceCard) super.get();
    }
    public ResourceCard pickById(int id){
        return (ResourceCard) super.pickById(id);
    }
}