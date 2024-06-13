package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;

public abstract class GenericController {
    protected GUI gui;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void setAttributes(Object... o) {
    }
}