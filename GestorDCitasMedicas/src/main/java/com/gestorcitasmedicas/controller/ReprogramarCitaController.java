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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReprogramarCitaController {

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
    @FXML private Label lblFechaActual;
    @FXML private Label lblHoraActual;
    @FXML private Label lblConsultorioSeleccionado;
    
    @FXML private VBox formularioReprogramacion;
    @FXML private DatePicker datePickerNueva;
    @FXML private ComboBox<String> comboHoraNueva;
    @FXML private TextArea txtMotivoReprogramacion;
    
    @FXML private Button btnRegresar;
    @FXML private Button btnReprogramarCita;
    
    private ObservableList<Consulta> citasActivas = FXCollections.observableArrayList();
    private ObservableList<String> horasDisponibles = FXCollections.observableArrayList();
    private Consulta citaSeleccionada = null;
    private Paciente pacienteActual = null;

    @FXML
    private void initialize() {
        System.out.println("ReprogramarCitaController inicializando...");
        
        configurarTabla();
        configurarComponentes();
        cargarCitasActivas();
        configurarEventos();
        
        System.out.println("ReprogramarCitaController inicializado correctamente");
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
    
    private void configurarComponentes() {
        // Configurar DatePicker para solo permitir fechas futuras
        datePickerNueva.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && empty) {
                    setDisable(date.isBefore(LocalDate.now()) || date.getDayOfWeek().getValue() > 5); // Solo lunes a viernes
                }
            }
        });
        
        // Configurar ComboBox de horas
        comboHoraNueva.setItems(horasDisponibles);
        comboHoraNueva.setDisable(true);
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
                mostrarAlerta("Información", "No tienes citas activas para reprogramar", Alert.AlertType.INFORMATION);
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
                validarPuedeReprogramar();
            } else {
                citaSeleccionada = null;
                ocultarInformacionCita();
            }
        });
        
        // Evento cuando se selecciona una nueva fecha
        datePickerNueva.setOnAction(event -> {
            if (datePickerNueva.getValue() != null && citaSeleccionada != null) {
                generarHorariosDisponibles();
            }
        });
        
        // Evento cuando se selecciona una nueva hora
        comboHoraNueva.setOnAction(event -> {
            validarFormularioCompleto();
        });
        
        // Evento de cambio en el motivo de reprogramación
        txtMotivoReprogramacion.textProperty().addListener((observable, oldValue, newValue) -> {
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
        
        lblFechaActual.setText(consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblHoraActual.setText(consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        
        infoCitaSeleccionada.setVisible(true);
        formularioReprogramacion.setVisible(true);
    }
    
    private void ocultarInformacionCita() {
        infoCitaSeleccionada.setVisible(false);
        formularioReprogramacion.setVisible(false);
        btnReprogramarCita.setDisable(true);
    }
    
    private void validarPuedeReprogramar() {
        if (citaSeleccionada != null) {
            boolean puedeReprogramar = Consulta.sePuedeCancelar(citaSeleccionada.getId());
            if (!puedeReprogramar) {
                mostrarAlerta("Advertencia", "Esta cita no se puede reprogramar porque ya pasó la fecha", Alert.AlertType.WARNING);
                btnReprogramarCita.setDisable(true);
            } else {
                btnReprogramarCita.setDisable(false);
            }
        }
    }
    
    private void generarHorariosDisponibles() {
        if (citaSeleccionada == null || datePickerNueva.getValue() == null) {
            return;
        }
        
        LocalDate nuevaFecha = datePickerNueva.getValue();
        horasDisponibles.clear();
        
        // Generar horarios de 8:00 AM a 5:00 PM con intervalos de 30 minutos
        LocalTime horaInicio = LocalTime.of(8, 0);
        LocalTime horaFin = LocalTime.of(17, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        while (horaInicio.isBefore(horaFin)) {
            String horaFormateada = horaInicio.format(formatter);
            
            // Verificar si la hora está disponible (excluyendo la cita actual)
            if (!Consulta.verificarDisponibilidad(citaSeleccionada.getIdMedico(), nuevaFecha, horaInicio)) {
                horasDisponibles.add(horaFormateada);
            }
            
            horaInicio = horaInicio.plusMinutes(30);
        }
        
        // Habilitar ComboBox de horas si hay horarios disponibles
        comboHoraNueva.setDisable(horasDisponibles.isEmpty());
        
        if (horasDisponibles.isEmpty()) {
            mostrarAlerta("Información", "No hay horarios disponibles para la fecha seleccionada", Alert.AlertType.INFORMATION);
        }
    }
    
    private void validarFormularioCompleto() {
        boolean formularioCompleto = citaSeleccionada != null && 
                                   datePickerNueva.getValue() != null && 
                                   comboHoraNueva.getValue() != null && 
                                   !txtMotivoReprogramacion.getText().trim().isEmpty();
        
        btnReprogramarCita.setDisable(!formularioCompleto);
    }
    
    @FXML
    private void reprogramarCita() {
        if (validarCampos()) {
            try {
                LocalDate nuevaFecha = datePickerNueva.getValue();
                LocalTime nuevaHora = LocalTime.parse(comboHoraNueva.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
                String motivoReprogramacion = txtMotivoReprogramacion.getText().trim();
                
                if (Consulta.reprogramarConsulta(citaSeleccionada.getId(), nuevaFecha, nuevaHora)) {
                    mostrarAlerta("Éxito", 
                        "Cita reprogramada correctamente\n\n" +
                        "ID de cita: " + citaSeleccionada.getId() + "\n" +
                        "Nueva fecha: " + nuevaFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                        "Nueva hora: " + nuevaHora.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n" +
                        "Motivo: " + motivoReprogramacion, 
                        Alert.AlertType.INFORMATION);
                    
                    // Limpiar formulario y recargar tabla
                    limpiarFormulario();
                    cargarCitasActivas();
                } else {
                    mostrarAlerta("Error", "No se pudo reprogramar la cita. Por favor, inténtelo nuevamente.", Alert.AlertType.ERROR);
                }
                
            } catch (Exception e) {
                System.err.println("Error al reprogramar cita: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error del Sistema", 
                    "Ocurrió un error inesperado durante la reprogramación:\n\n" + e.getMessage(), 
                    Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos() {
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para reprogramar", Alert.AlertType.ERROR);
            return false;
        }
        
        if (datePickerNueva.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una nueva fecha", Alert.AlertType.ERROR);
            datePickerNueva.requestFocus();
            return false;
        }
        
        if (comboHoraNueva.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una nueva hora", Alert.AlertType.ERROR);
            comboHoraNueva.requestFocus();
            return false;
        }
        
        if (txtMotivoReprogramacion.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el motivo de la reprogramación", Alert.AlertType.ERROR);
            txtMotivoReprogramacion.requestFocus();
            return false;
        }
        
        if (!Consulta.sePuedeCancelar(citaSeleccionada.getId())) {
            mostrarAlerta("Error", "Esta cita no se puede reprogramar", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        datePickerNueva.setValue(null);
        comboHoraNueva.getSelectionModel().clearSelection();
        comboHoraNueva.setDisable(true);
        txtMotivoReprogramacion.clear();
        citaSeleccionada = null;
        ocultarInformacionCita();
        tablaCitas.getSelectionModel().clearSelection();
        horasDisponibles.clear();
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
