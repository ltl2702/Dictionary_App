module com.example.btl {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.web;
    //requires javafx.sql;
    //requires mysql.connector.j;

    opens com.example.btl to javafx.fxml;
    exports com.example.btl;
}