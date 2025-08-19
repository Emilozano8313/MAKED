package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CancelarCitaController {

    @FXML private TableView<Consulta> tablaCitas;
    @FXML private TableColumn<Consulta, Integer> colId;
    @FXML private TableColumn<Consulta, String> colDoctor;
    @FXML private TableColumn<Consulta, String> colEspecialidad;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TableColumn<Consulta, String> colMotivo;
    @FXML private TableColumn<Consulta, String> colEstado;
    
    @FXML private VBox infoCitaSeleccionada;
    @FXML private Label lblDoctorSeleccionado;
    @FXML private Label lblEspecialidadSeleccionada;
    @FXML private Label lblFechaHoraSeleccionada;
    @FXML private Label lblMotivoSeleccionado;
    @FXML private Label lblConsultorioSeleccionado;
    
    @FXML private VBox formularioCancelacion;
    @FXML private TextArea txtMotivoCancelacion;
    
    @FXML private Button btnRegresar;
    @FXML private Button btnCancelarCita;
    
    private ObservableList<Consulta> citasActivas = FXCollections.observableArrayList();
    private Consulta citaSeleccionada = null;
    private Paciente pacienteActual = null;

    @FXML
    private void initialize() {
        System.out.println("CancelarCitaController inicializando...");
        
        configurarTabla();
        cargarCitasActivas();
        configurarEventos();
        
        System.out.println("CancelarCitaController inicializado correctamente");
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDoctor.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
            return new SimpleStringProperty(medico != null ? medico.getNombre() : "N/A");
        });
        colEspecialidad.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
            return new SimpleStringProperty(medico != null ? medico.getEspecialidad() : "N/A");
        });
        colFecha.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            return new SimpleStringProperty(consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        colHora.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            return new SimpleStringProperty(consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        });
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        // Configurar tabla
        tablaCitas.setItems(citasActivas);
    }
    
    private void cargarCitasActivas() {
        try {
            // Obtener el paciente actual (por ahora usamos el primer paciente como ejemplo)
            List<Paciente> pacientes = Paciente.obtenerTodos();
            if (pacientes.isEmpty()) {
                mostrarAlerta("Error", "No hay pacientes registrados", Alert.AlertType.ERROR);
                return;
            }
            pacienteActual = pacientes.get(0); // Usar el primer paciente como ejemplo
            
            // Cargar citas activas del paciente
            List<Consulta> citas = Consulta.obtenerActivasPorPaciente(pacienteActual.getId());
            citasActivas.clear();
            citasActivas.addAll(citas);
            
            if (citas.isEmpty()) {
                mostrarAlerta("Información", "No tienes citas activas para cancelar", Alert.AlertType.INFORMATION);
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar citas activas: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las citas activas", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarEventos() {
        // Evento de selección en la tabla
        tablaCitas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                citaSeleccionada = newValue;
                mostrarInformacionCita(newValue);
                validarPuedeCancelar();
            } else {
                citaSeleccionada = null;
                ocultarInformacionCita();
            }
        });
        
        // Evento de cambio en el motivo de cancelación
        txtMotivoCancelacion.textProperty().addListener((observable, oldValue, newValue) -> {
            validarFormularioCompleto();
        });
    }
    
    private void mostrarInformacionCita(Consulta consulta) {
        Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
        
        if (medico != null) {
            lblDoctorSeleccionado.setText(medico.getNombre());
            lblEspecialidadSeleccionada.setText(medico.getEspecialidad());
            lblConsultorioSeleccionado.setText(medico.getConsultorio());
        } else {
            lblDoctorSeleccionado.setText("N/A");
            lblEspecialidadSeleccionada.setText("N/A");
            lblConsultorioSeleccionado.setText("N/A");
        }
        
        lblFechaHoraSeleccionada.setText(
            consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
            consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        
        lblMotivoSeleccionado.setText(consulta.getMotivo());
        
        infoCitaSeleccionada.setVisible(true);
        formularioCancelacion.setVisible(true);
    }
    
    private void ocultarInformacionCita() {
        infoCitaSeleccionada.setVisible(false);
        formularioCancelacion.setVisible(false);
        btnCancelarCita.setDisable(true);
    }
    
    private void validarPuedeCancelar() {
        if (citaSeleccionada != null) {
            boolean puedeCancelar = Consulta.sePuedeCancelar(citaSeleccionada.getId());
            if (!puedeCancelar) {
                mostrarAlerta("Advertencia", "Esta cita no se puede cancelar porque ya pasó la fecha", Alert.AlertType.WARNING);
                btnCancelarCita.setDisable(true);
            } else {
                btnCancelarCita.setDisable(false);
            }
        }
    }
    
    private void validarFormularioCompleto() {
        boolean formularioCompleto = citaSeleccionada != null && 
                                   !txtMotivoCancelacion.getText().trim().isEmpty();
        
        btnCancelarCita.setDisable(!formularioCompleto);
    }
    
    @FXML
    private void cancelarCita() {
        if (validarCampos()) {
            try {
                String motivoCancelacion = txtMotivoCancelacion.getText().trim();
                
                if (Consulta.cancelarConsulta(citaSeleccionada.getId(), motivoCancelacion)) {
                    mostrarAlerta("Éxito", 
                        "Cita cancelada correctamente\n\n" +
                        "ID de cita: " + citaSeleccionada.getId() + "\n" +
                        "Motivo: " + motivoCancelacion, 
                        Alert.AlertType.INFORMATION);
                    
                    // Limpiar formulario y recargar tabla
                    limpiarFormulario();
                    cargarCitasActivas();
                } else {
                    mostrarAlerta("Error", "No se pudo cancelar la cita. Por favor, inténtelo nuevamente.", Alert.AlertType.ERROR);
                }
                
            } catch (Exception e) {
                System.err.println("Error al cancelar cita: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error del Sistema", 
                    "Ocurrió un error inesperado durante la cancelación:\n\n" + e.getMessage(), 
                    Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos() {
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para cancelar", Alert.AlertType.ERROR);
            return false;
        }
        
        if (txtMotivoCancelacion.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el motivo de la cancelación", Alert.AlertType.ERROR);
            txtMotivoCancelacion.requestFocus();
            return false;
        }
        
        if (!Consulta.sePuedeCancelar(citaSeleccionada.getId())) {
            mostrarAlerta("Error", "Esta cita no se puede cancelar", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        txtMotivoCancelacion.clear();
        citaSeleccionada = null;
        ocultarInformacionCita();
        tablaCitas.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/VistaPaciente.fxml"));
            Parent vistaPacienteRoot = loader.load();
            
            Scene nuevaEscena = new Scene(vistaPacienteRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Vista Paciente - Gestor de Citas Médicas");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la vista del paciente", Alert.AlertType.ERROR);
        }
    }
    
    private Medico obtenerMedicoPorId(int idMedico) {
        List<Medico> medicos = Medico.obtenerTodos();
        return medicos.stream()
                .filter(m -> m.getId() == idMedico)
                .findFirst()
                .orElse(null);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
