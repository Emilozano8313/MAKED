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
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendarCitaPacienteController {

    @FXML private ComboBox<String> comboDoctor;
    @FXML private ComboBox<String> comboHora;
    @FXML private DatePicker datePicker;
    @FXML private TextArea txtMotivo;
    @FXML private Label lblEspecialidad;
    @FXML private VBox infoDoctor;
    @FXML private Label lblNombreDoctor;
    @FXML private Label lblEspecialidadDoctor;
    @FXML private Label lblConsultorio;
    
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;
    @FXML private Button btnAgendar;
    
    private ObservableList<String> horasDisponibles = FXCollections.observableArrayList();
    private Medico medicoSeleccionado = null;
    private Paciente pacienteActual = null;

    @FXML
    private void initialize() {
        System.out.println("AgendarCitaPacienteController inicializando...");
        
        configurarComponentes();
        cargarDoctores();
        configurarEventos();
        
        System.out.println("AgendarCitaPacienteController inicializado correctamente");
    }
    
    private void configurarComponentes() {
        // Configurar DatePicker para solo permitir fechas futuras
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && empty) {
                    setDisable(date.isBefore(LocalDate.now()) || date.getDayOfWeek().getValue() > 5); // Solo lunes a viernes
                }
            }
        });
        
        // Configurar ComboBox de horas
        comboHora.setItems(horasDisponibles);
        comboHora.setDisable(true);
    }
    
    private void cargarDoctores() {
        try {
            List<Medico> medicos = Medico.obtenerTodos();
            ObservableList<String> nombresMedicos = FXCollections.observableArrayList();
            
            for (Medico medico : medicos) {
                nombresMedicos.add(medico.getNombre() + " - " + medico.getEspecialidad());
            }
            
            comboDoctor.setItems(nombresMedicos);
            
            if (!nombresMedicos.isEmpty()) {
                comboDoctor.getSelectionModel().selectFirst();
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar doctores: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los doctores", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarEventos() {
        // Evento cuando se selecciona un doctor
        comboDoctor.setOnAction(event -> {
            actualizarInformacionDoctor();
            generarHorariosDisponibles();
        });
        
        // Evento cuando se selecciona una fecha
        datePicker.setOnAction(event -> {
            if (datePicker.getValue() != null && medicoSeleccionado != null) {
                generarHorariosDisponibles();
            }
        });
        
        // Evento cuando se selecciona una hora
        comboHora.setOnAction(event -> {
            // Habilitar el botón de agendar si todos los campos están completos
            validarFormularioCompleto();
        });
    }
    
    private void actualizarInformacionDoctor() {
        String seleccion = comboDoctor.getValue();
        if (seleccion != null && !seleccion.isEmpty()) {
            // Extraer el nombre del doctor de la selección
            String nombreDoctor = seleccion.split(" - ")[0];
            
            // Buscar el médico en la lista
            List<Medico> medicos = Medico.obtenerTodos();
            for (Medico medico : medicos) {
                if (medico.getNombre().equals(nombreDoctor)) {
                    medicoSeleccionado = medico;
                    break;
                }
            }
            
            if (medicoSeleccionado != null) {
                // Actualizar información del doctor
                lblEspecialidad.setText(medicoSeleccionado.getEspecialidad());
                lblNombreDoctor.setText(medicoSeleccionado.getNombre());
                lblEspecialidadDoctor.setText(medicoSeleccionado.getEspecialidad());
                lblConsultorio.setText(medicoSeleccionado.getConsultorio());
                
                // Mostrar información del doctor
                infoDoctor.setVisible(true);
                
                // Limpiar selecciones anteriores
                comboHora.getSelectionModel().clearSelection();
                comboHora.setDisable(true);
                horasDisponibles.clear();
            }
        }
    }
    
    private void generarHorariosDisponibles() {
        if (medicoSeleccionado == null || datePicker.getValue() == null) {
            return;
        }
        
        LocalDate fechaSeleccionada = datePicker.getValue();
        horasDisponibles.clear();
        
        // Generar horarios de 8:00 AM a 5:00 PM con intervalos de 30 minutos
        LocalTime horaInicio = LocalTime.of(8, 0);
        LocalTime horaFin = LocalTime.of(17, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        while (horaInicio.isBefore(horaFin)) {
            String horaFormateada = horaInicio.format(formatter);
            
            // Verificar si la hora está disponible
            if (!Consulta.verificarDisponibilidad(medicoSeleccionado.getId(), fechaSeleccionada, horaInicio)) {
                horasDisponibles.add(horaFormateada);
            }
            
            horaInicio = horaInicio.plusMinutes(30);
        }
        
        // Habilitar ComboBox de horas si hay horarios disponibles
        comboHora.setDisable(horasDisponibles.isEmpty());
        
        if (horasDisponibles.isEmpty()) {
            mostrarAlerta("Información", "No hay horarios disponibles para la fecha seleccionada", Alert.AlertType.INFORMATION);
        }
    }
    
    private void validarFormularioCompleto() {
        boolean formularioCompleto = medicoSeleccionado != null && 
                                   datePicker.getValue() != null && 
                                   comboHora.getValue() != null && 
                                   !txtMotivo.getText().trim().isEmpty();
        
        btnAgendar.setDisable(!formularioCompleto);
    }
    
    @FXML
    private void agendarCita() {
        if (validarCampos()) {
            try {
                // Obtener el paciente actual (esto debería venir del login)
                // Por ahora usaremos el primer paciente como ejemplo
                List<Paciente> pacientes = Paciente.obtenerTodos();
                if (pacientes.isEmpty()) {
                    mostrarAlerta("Error", "No hay pacientes registrados", Alert.AlertType.ERROR);
                    return;
                }
                pacienteActual = pacientes.get(0); // Usar el primer paciente como ejemplo
                
                // Crear la consulta
                LocalTime hora = LocalTime.parse(comboHora.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
                
                Consulta nuevaConsulta = new Consulta(
                    pacienteActual.getId(),
                    medicoSeleccionado.getId(),
                    datePicker.getValue(),
                    hora,
                    txtMotivo.getText().trim()
                );
                
                if (nuevaConsulta.guardar()) {
                    mostrarAlerta("Éxito", 
                        "Cita agendada correctamente\n\n" +
                        "Doctor: " + medicoSeleccionado.getNombre() + "\n" +
                        "Fecha: " + datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                        "Hora: " + comboHora.getValue() + "\n" +
                        "Motivo: " + txtMotivo.getText().trim(), 
                        Alert.AlertType.INFORMATION);
                    
                    limpiarFormulario();
                } else {
                    mostrarAlerta("Error", "No se pudo agendar la cita. Por favor, inténtelo nuevamente.", Alert.AlertType.ERROR);
                }
                
            } catch (Exception e) {
                System.err.println("Error al agendar cita: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error del Sistema", 
                    "Ocurrió un error inesperado durante el agendado:\n\n" + e.getMessage(), 
                    Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos() {
        if (medicoSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un doctor", Alert.AlertType.ERROR);
            comboDoctor.requestFocus();
            return false;
        }
        
        if (datePicker.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha", Alert.AlertType.ERROR);
            datePicker.requestFocus();
            return false;
        }
        
        if (comboHora.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una hora", Alert.AlertType.ERROR);
            comboHora.requestFocus();
            return false;
        }
        
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe describir el motivo de la consulta", Alert.AlertType.ERROR);
            txtMotivo.requestFocus();
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void limpiarFormulario() {
        comboDoctor.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        comboHora.getSelectionModel().clearSelection();
        comboHora.setDisable(true);
        txtMotivo.clear();
        lblEspecialidad.setText("Selecciona un doctor primero");
        infoDoctor.setVisible(false);
        horasDisponibles.clear();
        medicoSeleccionado = null;
        btnAgendar.setDisable(true);
        
        // Enfocar el primer campo
        comboDoctor.requestFocus();
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
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
