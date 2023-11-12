module com.example.btl {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    //requires javafx.sql;
    requires mysql.connector.j;
    requires javafx.web;
    requires com.jfoenix;

    opens com.example.btl to javafx.fxml;
    exports com.example.btl;
}