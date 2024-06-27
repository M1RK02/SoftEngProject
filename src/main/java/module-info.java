module it.polimi.ingsw.gc01 {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.rmi;
    requires jdk.compiler;

    opens it.polimi.ingsw.gc01.model to com.google.gson;
    opens it.polimi.ingsw.gc01.model.cards to com.google.gson;
    opens it.polimi.ingsw.gc01.model.corners to com.google.gson;
    opens it.polimi.ingsw.gc01.network to java.rmi;
    opens it.polimi.ingsw.gc01.network.rmi to java.rmi;
    opens it.polimi.ingsw.gc01.model.player to java.rmi;
    opens it.polimi.ingsw.gc01.utils to com.google.gson;
    exports it.polimi.ingsw.gc01.view.gui to javafx.graphics;
    opens it.polimi.ingsw.gc01.view.gui.GUIControllers to javafx.fxml;
    opens it.polimi.ingsw.gc01.view.gui to javafx.fxml;
    exports it.polimi.ingsw.gc01.model.player;
}