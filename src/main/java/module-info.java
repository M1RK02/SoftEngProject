module it.polimi.ingsw.gc01 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.gc01 to javafx.fxml;
    exports it.polimi.ingsw.gc01;
}