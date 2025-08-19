package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.utils.ErrorHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CompletarCitaController {

    @FXML private Label lblPaciente;
    @FXML private Label lblFechaHora;
    @FXML private Label lblMotivo;
    @FXML private Label lblMatricula;
    @FXML private Label lblTelefono;
    
    @FXML private TextArea txtDiagnostico;
    @FXML private TextArea txtTratamiento;
    @FXML private TextArea txtObservaciones;
    
    @FXML private Button btnRegresar;
    @FXML private Button btnCompletar;
    
    private Consulta citaActual = null;
    private Paciente pacienteActual = null;
    private Medico medicoActual = null;

    @FXML
    private void initialize() {
        System.out.println("CompletarCitaController inicializando...");
        
        // La información de la cita se cargará desde la ventana anterior
        configurarEventos();
        
        System.out.println("CompletarCitaController inicializado correctamente");
    }
    
    public void cargarInformacionCita(Consulta cita) {
        this.citaActual = cita;
        
        try {
            // Obtener información del paciente
            List<Paciente> pacientes = Paciente.obtenerTodos();
            pacienteActual = pacientes.stream()
                    .filter(p -> p.getId() == cita.getIdPaciente())
                    .findFirst()
                    .orElse(null);
            
            // Obtener información del médico
            List<Medico> medicos = Medico.obtenerTodos();
            medicoActual = medicos.stream()
                    .filter(m -> m.getId() == cita.getIdMedico())
                    .findFirst()
                    .orElse(null);
            
            if (pacienteActual != null) {
                lblPaciente.setText(pacienteActual.getNombre());
                lblMatricula.setText(pacienteActual.getMatricula());
                lblTelefono.setText(pacienteActual.getTelefono());
            } else {
                lblPaciente.setText("N/A");
                lblMatricula.setText("N/A");
                lblTelefono.setText("N/A");
            }
            
            lblFechaHora.setText(
                cita.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
                cita.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))
            );
            
            lblMotivo.setText(cita.getMotivo());
            
        } catch (Exception e) {
            System.err.println("Error al cargar información de la cita: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la información de la cita", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarEventos() {
        // Evento para validar formulario cuando se escriba en los campos
        txtDiagnostico.textProperty().addListener((observable, oldValue, newValue) -> {
            validarFormularioCompleto();
        });
        
        txtTratamiento.textProperty().addListener((observable, oldValue, newValue) -> {
            validarFormularioCompleto();
        });
    }
    
    private void validarFormularioCompleto() {
        boolean formularioCompleto = !txtDiagnostico.getText().trim().isEmpty() && 
                                   !txtTratamiento.getText().trim().isEmpty();
        
        btnCompletar.setDisable(!formularioCompleto);
    }
    
    @FXML
    private void completarCita() {
        if (validarCampos()) {
            try {
                String diagnostico = txtDiagnostico.getText().trim();
                String tratamiento = txtTratamiento.getText().trim();
                String observaciones = txtObservaciones.getText().trim();
                
                if (Consulta.agregarDiagnostico(citaActual.getId(), diagnostico, tratamiento, observaciones)) {
                    mostrarAlerta("Éxito", 
                        "Cita completada correctamente\n\n" +
                        "Paciente: " + pacienteActual.getNombre() + "\n" +
                        "Diagnóstico: " + diagnostico + "\n" +
                        "Tratamiento: " + tratamiento + "\n" +
                        "Estado: Completada", 
                        Alert.AlertType.INFORMATION);
                    
                    regresar();
                } else {
                    mostrarAlerta("Error", "No se pudo completar la cita. Por favor, inténtelo nuevamente.", Alert.AlertType.ERROR);
                }
                
            } catch (Exception e) {
                System.err.println("Error al completar cita: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error del Sistema", 
                    "Ocurrió un error inesperado durante la completación:\n\n" + e.getMessage(), 
                    Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos() {
        if (citaActual == null) {
            mostrarAlerta("Error", "No hay información de cita disponible", Alert.AlertType.ERROR);
            return false;
        }
        
        // Verificar que la cita no esté ya completada
        if ("completada".equals(citaActual.getEstado())) {
            mostrarAlerta("Error", "Esta cita ya ha sido completada anteriormente", Alert.AlertType.ERROR);
            return false;
        }
        
        // Verificar que la cita no esté cancelada
        if ("cancelada".equals(citaActual.getEstado())) {
            mostrarAlerta("Error", "No se puede completar una cita cancelada", Alert.AlertType.ERROR);
            return false;
        }
        
        String diagnostico = txtDiagnostico.getText().trim();
        String tratamiento = txtTratamiento.getText().trim();
        
        // Usar ErrorHandler para validaciones
        if (!ErrorHandler.validarTextoNoVacio(diagnostico, "Diagnóstico")) {
            txtDiagnostico.requestFocus();
            return false;
        }
        
        if (!ErrorHandler.validarTextoNoVacio(tratamiento, "Tratamiento")) {
            txtTratamiento.requestFocus();
            return false;
        }
        
        if (!ErrorHandler.validarLongitudTexto(diagnostico, "Diagnóstico", 10, 500)) {
            txtDiagnostico.requestFocus();
            return false;
        }
        
        if (!ErrorHandler.validarLongitudTexto(tratamiento, "Tratamiento", 10, 500)) {
            txtTratamiento.requestFocus();
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/AgendaCitas.fxml"));
            Parent agendaCitasRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agendaCitasRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Agenda de Citas - Doctor");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la agenda de citas", Alert.AlertType.ERROR);
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
