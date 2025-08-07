package com.gestorcitasmedicas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/gestorcitasmedicas/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 759 , 422);
        stage.setTitle("Bienvenido a tu gestor de citas medicas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
