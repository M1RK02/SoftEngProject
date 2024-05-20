module it.polimi.ingsw.gc01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;


    opens it.polimi.ingsw.gc01 to javafx.fxml;
    exports it.polimi.ingsw.gc01;
    exports it.polimi.ingsw.gc01.model;
    opens it.polimi.ingsw.gc01.model to javafx.fxml;
    opens it.polimi.ingsw.gc01.model.cards to com.google.gson;
    opens it.polimi.ingsw.gc01.model.corners to com.google.gson;
    exports it.polimi.ingsw.gc01.network to java.rmi;

}