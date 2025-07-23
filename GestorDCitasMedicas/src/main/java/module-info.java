module com.gestorcitasmedicas.gestorcitasmedicas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gestorcitasmedicas to javafx.fxml;
    exports com.gestorcitasmedicas;
}