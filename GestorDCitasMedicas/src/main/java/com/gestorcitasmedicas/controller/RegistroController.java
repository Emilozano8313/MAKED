package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistroController {

    @FXML
    private TextField nombreField;
    
    @FXML
    private TextField apellidosField;
    
    @FXML
    private TextField matriculaField;
    
    @FXML
    private TextField curpField;
    
    @FXML
    private TextField correoField;
    
    @FXML
    private TextField telefonoField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button registrarButton;
    
    @FXML
    private Label volverLoginLabel;

    @FXML
    private void initialize() {
        // Configurar el evento para volver al login
        volverLoginLabel.setOnMouseClicked(this::volverAlLogin);
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return;
        }

        // Validar formato de correo
        if (!validarEmail(correoField.getText())) {
            mostrarAlerta("Error", "Formato de correo electrónico inválido", Alert.AlertType.ERROR);
            return;
        }

        // Validar formato de CURP
        if (!validarCURP(curpField.getText())) {
            mostrarAlerta("Error", "Formato de CURP inválido", Alert.AlertType.ERROR);
            return;
        }

        // Validar formato de teléfono
        if (!validarTelefono(telefonoField.getText())) {
            mostrarAlerta("Error", "Formato de teléfono inválido", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombreField.getText());
            nuevoUsuario.setApellidos(apellidosField.getText());
            nuevoUsuario.setMatricula(matriculaField.getText());
            nuevoUsuario.setCurp(curpField.getText());
            nuevoUsuario.setCorreo(correoField.getText());
            nuevoUsuario.setTelefono(telefonoField.getText());
            nuevoUsuario.setContrasena(passwordField.getText());
            nuevoUsuario.setRol("paciente"); // Por defecto se registra como paciente

            // Aquí se guardaría en la base de datos
            boolean registroExitoso = guardarUsuario(nuevoUsuario);
            
            if (registroExitoso) {
                mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                volverAlLogin(event);
            } else {
                mostrarAlerta("Error", "No se pudo registrar el usuario", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar usuario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (apellidosField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Los apellidos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        if (matriculaField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La matrícula es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (curpField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El CURP es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (correoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El correo es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (telefonoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        if (passwordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La contraseña es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (confirmPasswordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar la contraseña", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean validarCURP(String curp) {
        // CURP debe tener 18 caracteres alfanuméricos
        return curp.matches("^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$");
    }

    private boolean validarTelefono(String telefono) {
        // Validar formato de teléfono mexicano (10 dígitos)
        return telefono.matches("^[0-9]{10}$");
    }

    private boolean guardarUsuario(Usuario usuario) {
        // Aquí se implementaría la lógica para guardar en la base de datos
        // Por ahora retornamos true para simular éxito
        System.out.println("Guardando usuario: " + usuario.getNombre() + " " + usuario.getApellidos());
        return true;
    }

    private void volverAlLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void volverAlLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
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
