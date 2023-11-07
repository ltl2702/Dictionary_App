module com.example.btl {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    //requires javafx.sql;
    requires mysql.connector.j;

    opens app to javafx.fxml;
    exports app;
}