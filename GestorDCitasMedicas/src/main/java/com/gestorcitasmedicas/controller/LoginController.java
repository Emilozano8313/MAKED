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
        
        // Configurar eventos para los enlaces
        configurarEventos();
    }
    
    private void configurarEventos() {
        // Evento para "¿Has olvidado tu contraseña?"
        olvidoContra.setOnMouseClicked(e -> abrirRecuperarContrasena());
        
        // Evento para "¡Crea una ahora!"
        crearCuenta.setOnMouseClicked(e -> crearCuenta());
    }
    
    @FXML
    private void abrirRecuperarContrasena() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/recuperarContra.fxml"));
            Parent recuperarContraRoot = loader.load();
            
            Scene nuevaEscena = new Scene(recuperarContraRoot, 1200, 600);
            Stage currentStage = (Stage) olvidoContra.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Recuperar Contraseña - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de recuperación", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void crearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/Registro.fxml"));
            Parent registroRoot = loader.load();
            
<<<<<<< HEAD
            Scene nuevaEscena = new Scene(registroRoot, 773, 400);
            Stage currentStage = (Stage) crearCuenta.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Registro - Gestor de Citas Médicas");
=======
            Scene nuevaEscena = new Scene(registroRoot, 1200, 600);
            Stage currentStage = (Stage) crearCuenta.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Registro de Usuario - Gestor de Citas Médicas");
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de registro", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String correo = correoIngresar.getText();
        String contra = contraIngresar.getText();
        
        System.out.println("Iniciando sesión con: " + correo);
        
        // Simulación de autenticación para diferentes roles
        if ("recepcionista@test.com".equals(correo) && "recepcionista123".equals(contra)) {
            // Autenticación como recepcionista
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1200, 800));
                stage.setTitle("Panel Principal - Recepcionista");
                stage.show();

                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar el panel principal del recepcionista", Alert.AlertType.ERROR);
            }
        } else if ("doctor@test.com".equals(correo) && "doctor123".equals(contra)) {
            // Autenticación como médico
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainDoctor.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1200, 800));
                stage.setTitle("Panel Principal - Médico");
                stage.show();

                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar el panel principal del médico", Alert.AlertType.ERROR);
            }
        } else if ("paciente@test.com".equals(correo) && "paciente123".equals(contra)) {
            // Autenticación como paciente
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1024, 768));
                stage.setTitle("Panel Principal - Paciente");
                stage.show();

                // Cerrar la ventana actual
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar el panel principal del paciente", Alert.AlertType.ERROR);
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
