package com.gestorcitasmedicas.controller;
import com.gestorcitasmedicas.model.Usuario;

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
    private void iniciarSesion(ActionEvent event) {
        //Accion para el inicio de sesion
        String correo = correoIngresar.getText();
        String contra = contraIngresar.getText();
        System.out.println("Correo ingresado: " + correo);
        System.out.println("Contraseña ingresada: " + contra);

        // Simulación de autenticación para pruebas
        Usuario usuario = autenticarSimulado(correo, contra);
        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso como " + usuario.getRol());
            mostrarAlerta("Éxito", "Inicio de sesión exitoso como " + usuario.getRol(), Alert.AlertType.INFORMATION);
            switch (usuario.getRol()) {
                case "doctor":
                    System.out.println("Bienvenido doctor");
                    //abrirVentana("debes poner la ruta del siguiente fxml", event);
                    break;
                case "paciente":
                    System.out.println("Bienvenido paciente");
                    //abrirVentana("vistaPaciente.fxml", event);
                    break;
                case "recepcionista":
                    System.out.println("Bienvenido recepcionista");
                    //abrirVentana("vistaRecepcionista.fxml", event);
                    break;
                default:
                    System.out.println("Rol no reconocido.");
            }
        } else {
            System.out.println("Correo o contraseña incorrectos");
            mostrarAlerta("Error", "Correo o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    // Método temporal para simular autenticación
    private Usuario autenticarSimulado(String correo, String contra) {
        // Datos de prueba
        if ("admin@test.com".equals(correo) && "admin123".equals(contra)) {
            return new Usuario(1, "admin", contra, "Administrador", correo);
        } else if ("doctor@test.com".equals(correo) && "doctor123".equals(contra)) {
            return new Usuario(2, "doctor", contra, "Dr. Juan Pérez", correo);
        } else if ("paciente@test.com".equals(correo) && "paciente123".equals(contra)) {
            return new Usuario(3, "paciente", contra, "María García", correo);
        } else if ("recepcionista@test.com".equals(correo) && "recepcionista123".equals(contra)) {
            return new Usuario(4, "recepcionista", contra, "Ana López", correo);
        }
        return null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void initialize(){
        //Accion para el caso de olvido contrasena
        olvidoContra.setOnMouseClicked(event -> {

        });

        //Accion para el caso de crear cuenta
        crearCuenta.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/Registro.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Registro - Gestor de Citas Médicas");
                stage.show();

                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void abrirVentana(String rutaFXML, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cierra la ventana actual
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
