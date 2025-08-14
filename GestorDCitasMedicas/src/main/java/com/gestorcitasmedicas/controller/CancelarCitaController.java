package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class CancelarCitaController {

    @FXML private TextArea txtMotivo;
    @FXML private Button btnCancelar;
    @FXML private Button btnContinuar;

    @FXML
    private void initialize() {
        System.out.println("CancelarCitaController inicializando...");
        
        // Configurar efectos hover para los botones
        configurarEfectosHover();
        
        System.out.println("CancelarCitaController inicializado correctamente");
    }
    
    private void configurarEfectosHover() {
        // Efecto hover para el botón Cancelar
        btnCancelar.setOnMouseEntered(e -> {
            btnCancelar.setStyle("-fx-background-color: #666666; -fx-cursor: hand;");
        });
        
        btnCancelar.setOnMouseExited(e -> {
            btnCancelar.setStyle("-fx-background-color: #787a7d; -fx-cursor: hand;");
        });
        
        // Efecto hover para el botón Continuar
        btnContinuar.setOnMouseEntered(e -> {
            btnContinuar.setStyle("-fx-background-color: #2C5A7A; -fx-cursor: hand;");
        });
        
        btnContinuar.setOnMouseExited(e -> {
            btnContinuar.setStyle("-fx-background-color: #3b6f89; -fx-cursor: hand;");
        });
    }

    @FXML
    private void continuar(ActionEvent event) {
        System.out.println("Continuando con la cancelación de cita...");
        
        // Validar que se haya ingresado un motivo
        if (!validarMotivo()) {
            return;
        }
        
        // Mostrar confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Cancelación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea cancelar su cita?\n\nMotivo: " + txtMotivo.getText());
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Simular cancelación exitosa
                try {
                    System.out.println("Cita cancelada exitosamente");
                    System.out.println("Motivo: " + txtMotivo.getText());
                    
                    // Mostrar ventana de confirmación exitosa
                    mostrarConfirmacionExitosa();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al cancelar la cita: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        System.out.println("Cancelando operación...");
        
        // Regresar al panel principal del paciente
        regresarAlPanelPrincipal();
    }
    
    private boolean validarMotivo() {
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un motivo para la cancelación", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        if (txtMotivo.getText().trim().length() < 10) {
            mostrarAlerta("Error", "El motivo debe tener al menos 10 caracteres", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void mostrarConfirmacionExitosa() {
        try {
            // Cargar la ventana de confirmación exitosa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CancelarCitaExitosa.fxml"));
            Parent exitosaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(exitosaRoot, 400, 200);
            Stage currentStage = (Stage) btnContinuar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cita Cancelada Exitosamente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Éxito", "Su cita ha sido cancelada exitosamente", Alert.AlertType.INFORMATION);
            regresarAlPanelPrincipal();
        }
    }
    
    private void regresarAlPanelPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent pacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(pacienteRoot, 1024, 768);
            Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Paciente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al panel principal", Alert.AlertType.ERROR);
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
