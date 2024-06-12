module it.polimi.ingsw.gc01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;

    opens it.polimi.ingsw.gc01.model to com.google.gson;
    opens it.polimi.ingsw.gc01.model.cards to com.google.gson;
    opens it.polimi.ingsw.gc01.model.corners to com.google.gson;
    opens it.polimi.ingsw.gc01.network to java.rmi;
    opens it.polimi.ingsw.gc01.network.rmi to java.rmi;
    opens it.polimi.ingsw.gc01.model.player to java.rmi;
    opens it.polimi.ingsw.gc01.utils to com.google.gson;
}