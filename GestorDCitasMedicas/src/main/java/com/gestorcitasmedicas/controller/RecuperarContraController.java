package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RecuperarContraController {

    @FXML
    private TextField txtCurp;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    @FXML
    private Button btnListo;
    
    @FXML
    private Button btnRegresar;
    
    @FXML
    private Button btnRegresarInferior;
    
    @FXML
    private Label lblCrearCuenta;

    @FXML
    private void initialize() {
        System.out.println("RecuperarContraController inicializado");
        
        // Configurar eventos
        configurarEventos();
    }
    
    private void configurarEventos() {
        // Evento para el botón de regresar (ya configurado en FXML con onAction="#regresarALogin")
        // btnRegresar.setOnAction(e -> regresarALogin());
        
        // Evento para crear cuenta
        lblCrearCuenta.setOnMouseClicked(e -> crearCuenta());
        
        System.out.println("Eventos configurados en RecuperarContraController");
    }
    
    @FXML
    private void actualizarContrasena() {
        String curp = txtCurp.getText().trim();
        String contrasena = txtContrasena.getText();
        String confirmarContrasena = txtConfirmarContrasena.getText();
        
        // Validaciones
        if (!validarCampos(curp, contrasena, confirmarContrasena)) {
            return;
        }
        
        // Verificar si el CURP existe en la base de datos
        if (!verificarCurpEnBD(curp)) {
            mostrarAlerta("Error", "El CURP ingresado no está registrado en la base de datos", Alert.AlertType.ERROR);
            return;
        }
        
        // Actualizar contraseña en la base de datos
        if (actualizarContrasenaEnBD(curp, contrasena)) {
            mostrarAlerta("Éxito", "Contraseña actualizada correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
            regresarALogin();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la contraseña", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos(String curp, String contrasena, String confirmarContrasena) {
        // Validar CURP
        if (curp.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar su CURP", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de CURP (18 caracteres alfanuméricos)
        if (!Pattern.matches("^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$", curp)) {
            mostrarAlerta("Error", "El formato del CURP no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar contraseña
        if (contrasena.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        if (contrasena.length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar confirmación de contraseña
        if (confirmarContrasena.isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar su contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!contrasena.equals(confirmarContrasena)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private boolean verificarCurpEnBD(String curp) {
        // Simulación de verificación en base de datos
        // En un entorno real, aquí se haría la consulta a la BD
        
        // CURPs simulados en la base de datos
        String[] curpsRegistrados = {
            "ABCD123456HMCDEF01",
            "EFGH789012HMGHIJ02",
            "IJKL345678HMKLMN03",
            "MNOP901234HMNOPQ04",
            "QRST567890HMQRST05"
        };
        
        for (String curpRegistrado : curpsRegistrados) {
            if (curpRegistrado.equals(curp)) {
                return true;
            }
        }
        
        return false;
        
        /* Código real para consultar la base de datos:
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE curp = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, curp);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al verificar el CURP en la base de datos", Alert.AlertType.ERROR);
        }
        return false;
        */
    }
    
    private boolean actualizarContrasenaEnBD(String curp, String contrasena) {
        // Simulación de actualización en base de datos
        // En un entorno real, aquí se haría la actualización en la BD
        
        System.out.println("Actualizando contraseña para CURP: " + curp);
        
        // Simular éxito
        return true;
        
        /* Código real para actualizar la base de datos:
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE usuarios SET contrasena = ? WHERE curp = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, contrasena); // En producción, debería estar hasheada
                pstmt.setString(2, curp);
                int filasActualizadas = pstmt.executeUpdate();
                return filasActualizadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al actualizar la contraseña en la base de datos", Alert.AlertType.ERROR);
        }
        return false;
        */
    }
    
    private void limpiarFormulario() {
        txtCurp.clear();
        txtContrasena.clear();
        txtConfirmarContrasena.clear();
    }
    
    @FXML
    private void regresarALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent loginRoot = loader.load();
            Scene nuevaEscena = new Scene(loginRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Bienvenido a tu gestor de citas medicas");
            currentStage.setMaximized(true);
            currentStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al login", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void crearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/Registro.fxml"));
            Parent registroRoot = loader.load();
            Scene nuevaEscena = new Scene(registroRoot);
            Stage currentStage = (Stage) lblCrearCuenta.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Registro - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de registro", Alert.AlertType.ERROR);
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
