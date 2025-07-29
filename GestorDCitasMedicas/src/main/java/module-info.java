module com.gestorcitasmedicas.gestorcitasmedicas {
    requires javafx.controls;
    requires javafx.fxml;
    requires ojdbc8;
    requires java.sql;
    requires ucp;


    opens com.gestorcitasmedicas to javafx.fxml;
    exports com.gestorcitasmedicas.controller;

    opens com.gestorcitasmedicas.controller to javafx.fxml;
    exports com.gestorcitasmedicas;
}