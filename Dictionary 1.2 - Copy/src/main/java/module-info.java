module OOP {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.web;
    requires com.jfoenix;
    requires jlayer;
    requires freetts;
    //requires jsapi;

    exports App;
    opens App to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Connect;
    opens Connect to javafx.fxml;
    exports MatchGame;
    opens MatchGame to javafx.fxml;
    //exports API;
   // opens API to javafx.fxml;
}
