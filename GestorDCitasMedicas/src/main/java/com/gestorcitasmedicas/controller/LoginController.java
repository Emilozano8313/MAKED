package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink; // Importa Hyperlink
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador para la vista de inicio de sesión (LoginView.fxml).
 * Maneja la lógica de interacción del usuario en la pantalla de login.
 */
public class LoginController {

    @FXML
    private TextField usernameField; // Campo de texto para el nombre de usuario (correo)

    @FXML
    private PasswordField passwordField; // Campo de contraseña

    @FXML
    private Button loginButton; // Botón de inicio de sesión

    @FXML
    private Label messageLabel; // Etiqueta para mostrar mensajes al usuario (ej. error de login)

    @FXML
    private Hyperlink forgotPasswordLink; // Enlace para "¿Olvidaste tu contraseña?"

    @FXML
    private Hyperlink signupLink; // Enlace para "¡Crea una ahora!"

    /**
     * Método que se ejecuta automáticamente cuando se carga la vista FXML.
     * Aquí se pueden inicializar componentes o configurar listeners.
     */
    @FXML
    public void initialize() {
        // Por ahora, no haremos nada aquí.
        // Más adelante, aquí se podría configurar el foco inicial,
        // o añadir listeners para la validación en tiempo real.
    }

    /**
     * Maneja el evento de clic del botón de inicio de sesión.
     * Aquí se procesarán las credenciales y se intentará autenticar al usuario.
     */
    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validaciones básicas (se mejorarán más adelante)
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Por favor, introduce tu correo y contraseña.");
            return;
        }

        // TODO: Aquí se implementará la lógica de autenticación con la base de datos.
        // Por ahora, solo un mensaje de prueba.
        if (username.equals("test@example.com") && password.equals("password")) {
            messageLabel.setText("¡Inicio de sesión exitoso!");
            // TODO: Redirigir al dashboard correspondiente al rol.
            System.out.println("Usuario logueado: " + username);
        } else {
            messageLabel.setText("Correo o contraseña incorrectos.");
        }
    }

    /**
     * Maneja el evento de clic del enlace "¿Olvidaste tu contraseña?".
     */
    @FXML
    private void handleForgotPassword() {
        System.out.println("Se hizo clic en '¿Olvidaste tu contraseña?'");
        // TODO: Implementar lógica para la recuperación de contraseña
    }

    /**
     * Maneja el evento de clic del enlace "¡Crea una ahora!".
     */
    @FXML
    private void handleSignupAction() {
        System.out.println("Se hizo clic en '¡Crea una ahora!'");
        // TODO: Implementar lógica para abrir la pantalla de registro
    }
}
