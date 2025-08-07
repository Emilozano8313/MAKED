package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label olvidoContra;
    @FXML
    private Label crearCuenta;
    @FXML
    private TextField correoIngresar;
    @FXML
    private PasswordField contraIngresar;
    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        System.out.println("LoginController inicializado correctamente");
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String correo = correoIngresar.getText();
        String contra = contraIngresar.getText();
        
        System.out.println("Iniciando sesión con: " + correo);
        
        // Simulación simple de autenticación
        if ("recepcionista@test.com".equals(correo) && "recepcionista123".equals(contra)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel Principal - Recepcionista");
                stage.show();

                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar el panel principal", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
