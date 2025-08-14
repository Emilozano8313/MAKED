package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReprogramarCitaController {

    @FXML private ComboBox<String> cmbHora;
    @FXML private ComboBox<String> cmbFecha;
    @FXML private TextArea txtMotivo;
    @FXML private Button btnCancelar;
    @FXML private Button btnAceptar;

    @FXML
    private void initialize() {
        System.out.println("ReprogramarCitaController inicializando...");
        
        // Cargar opciones de hora y fecha
        cargarOpcionesHora();
        cargarOpcionesFecha();
        
        // Configurar efectos hover para los botones
        configurarEfectosHover();
        
        System.out.println("ReprogramarCitaController inicializado correctamente");
    }
    
    private void cargarOpcionesHora() {
        ObservableList<String> horas = FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"
        );
        cmbHora.setItems(horas);
    }
    
    private void cargarOpcionesFecha() {
        ObservableList<String> fechas = FXCollections.observableArrayList();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Generar fechas para los próximos 30 días
        for (int i = 1; i <= 30; i++) {
            LocalDate fecha = hoy.plusDays(i);
            fechas.add(fecha.format(formatter));
        }
        
        cmbFecha.setItems(fechas);
    }
    
    private void configurarEfectosHover() {
        // Efecto hover para el botón Cancelar
        btnCancelar.setOnMouseEntered(e -> {
            btnCancelar.setStyle("-fx-background-color: #666666; -fx-cursor: hand;");
        });
        
        btnCancelar.setOnMouseExited(e -> {
            btnCancelar.setStyle("-fx-background-color: #787a7d; -fx-cursor: hand;");
        });
        
        // Efecto hover para el botón Aceptar
        btnAceptar.setOnMouseEntered(e -> {
            btnAceptar.setStyle("-fx-background-color: #2C5A7A; -fx-cursor: hand;");
        });
        
        btnAceptar.setOnMouseExited(e -> {
            btnAceptar.setStyle("-fx-background-color: #3b6f89; -fx-cursor: hand;");
        });
    }

    @FXML
    private void aceptar(ActionEvent event) {
        System.out.println("Aceptando reprogramación de cita...");
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Mostrar confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Reprogramación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
            "¿Está seguro de que desea reprogramar su cita?\n\n" +
            "Nueva fecha: " + cmbFecha.getValue() + "\n" +
            "Nueva hora: " + cmbHora.getValue() + "\n" +
            "Motivo: " + txtMotivo.getText()
        );
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Simular reprogramación exitosa
                try {
                    System.out.println("Cita reprogramada exitosamente");
                    System.out.println("Nueva fecha: " + cmbFecha.getValue());
                    System.out.println("Nueva hora: " + cmbHora.getValue());
                    System.out.println("Motivo: " + txtMotivo.getText());
                    
                    // Mostrar ventana de confirmación exitosa
                    mostrarConfirmacionExitosa();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al reprogramar la cita: " + e.getMessage(), Alert.AlertType.ERROR);
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
    
    private boolean validarCampos() {
        // Validar hora seleccionada
        if (cmbHora.getValue() == null || cmbHora.getValue().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una hora", Alert.AlertType.ERROR);
            cmbHora.requestFocus();
            return false;
        }
        
        // Validar fecha seleccionada
        if (cmbFecha.getValue() == null || cmbFecha.getValue().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una fecha", Alert.AlertType.ERROR);
            cmbFecha.requestFocus();
            return false;
        }
        
        // Validar motivo
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un motivo para la reprogramación", Alert.AlertType.ERROR);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReprogramarCitaExitosa.fxml"));
            Parent exitosaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(exitosaRoot, 400, 200);
            Stage currentStage = (Stage) btnAceptar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Cita Reprogramada Exitosamente");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Éxito", "Su cita ha sido reprogramada exitosamente", Alert.AlertType.INFORMATION);
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
