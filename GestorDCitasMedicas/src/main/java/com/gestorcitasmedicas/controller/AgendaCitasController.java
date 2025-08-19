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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendaCitasController {

    @FXML private Label lblNombreMedico;
    @FXML private Label lblEspecialidad;
    @FXML private Label lblConsultorio;
    @FXML private Label lblCedula;
    
    @FXML private DatePicker datePickerFiltro;
    @FXML private ComboBox<String> comboEstadoFiltro;
    @FXML private Button btnLimpiarFiltros;
    @FXML private Button btnHistorialPaciente;
    @FXML private Button btnReportes;
    @FXML private Button btnRegresar;
    
    @FXML private TableView<Consulta> tablaCitas;
    @FXML private TableColumn<Consulta, Integer> colId;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TableColumn<Consulta, String> colMotivo;
    @FXML private TableColumn<Consulta, String> colEstado;
    @FXML private TableColumn<Consulta, String> colAcciones;
    
    @FXML private Label lblContadorCitas;
    
    @FXML private VBox infoCitaSeleccionada;
    @FXML private Label lblPacienteSeleccionado;
    @FXML private Label lblTelefonoPaciente;
    @FXML private Label lblFechaHoraSeleccionada;
    @FXML private Label lblMatriculaPaciente;
    @FXML private Label lblMotivoSeleccionado;
    @FXML private Label lblObservacionesSeleccionadas;
    
    @FXML private Button btnCompletarCita;
    @FXML private Button btnCancelarCita;
    
    private ObservableList<Consulta> todasLasCitas = FXCollections.observableArrayList();
    private ObservableList<Consulta> citasFiltradas = FXCollections.observableArrayList();
    private Medico medicoActual = null;
    private Consulta citaSeleccionada = null;

    @FXML
    private void initialize() {
        System.out.println("AgendaCitasController inicializando...");
        
        configurarTabla();
        configurarFiltros();
        cargarInformacionMedico();
        cargarCitas();
        configurarEventos();
        
        System.out.println("AgendaCitasController inicializado correctamente");
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPaciente.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Paciente paciente = obtenerPacientePorId(consulta.getIdPaciente());
            return new SimpleStringProperty(paciente != null ? paciente.getNombre() : "N/A");
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
        colAcciones.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            String acciones = "";
            if ("programada".equals(consulta.getEstado())) {
                acciones = "Ver detalles";
            } else if ("completada".equals(consulta.getEstado())) {
                acciones = "Ver diagnóstico";
            } else if ("cancelada".equals(consulta.getEstado())) {
                acciones = "Ver motivo";
            }
            return new SimpleStringProperty(acciones);
        });
        
        // Configurar tabla
        tablaCitas.setItems(citasFiltradas);
    }
    
    private void configurarFiltros() {
        // Configurar ComboBox de estados
        ObservableList<String> estados = FXCollections.observableArrayList("Todos", "programada", "completada", "cancelada");
        comboEstadoFiltro.setItems(estados);
        comboEstadoFiltro.setValue("Todos");
        
        // Configurar DatePicker para solo permitir fechas futuras
        datePickerFiltro.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && empty) {
                    setDisable(false); // Permitir todas las fechas para filtros
                }
            }
        });
    }
    
    private void cargarInformacionMedico() {
        try {
            // Obtener el médico actual (por ahora usamos el primer médico como ejemplo)
            List<Medico> medicos = Medico.obtenerTodos();
            if (medicos.isEmpty()) {
                mostrarAlerta("Error", "No hay médicos registrados", Alert.AlertType.ERROR);
                return;
            }
            medicoActual = medicos.get(0); // Usar el primer médico como ejemplo
            
            // Mostrar información del médico
            lblNombreMedico.setText(medicoActual.getNombre());
            lblEspecialidad.setText(medicoActual.getEspecialidad());
            lblConsultorio.setText(medicoActual.getConsultorio());
            lblCedula.setText(medicoActual.getCedula());
            
        } catch (Exception e) {
            System.err.println("Error al cargar información del médico: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la información del médico", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarCitas() {
        try {
            if (medicoActual == null) {
                return;
            }
            
            // Cargar todas las citas del médico
            List<Consulta> citas = Consulta.obtenerPorMedico(medicoActual.getId());
            todasLasCitas.clear();
            todasLasCitas.addAll(citas);
            
            aplicarFiltros();
            
        } catch (Exception e) {
            System.err.println("Error al cargar citas: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las citas", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarEventos() {
        // Evento de selección en la tabla
        tablaCitas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                citaSeleccionada = newValue;
                mostrarInformacionCita(newValue);
            } else {
                citaSeleccionada = null;
                ocultarInformacionCita();
            }
        });
        
        // Eventos de filtros
        datePickerFiltro.setOnAction(event -> aplicarFiltros());
        comboEstadoFiltro.setOnAction(event -> aplicarFiltros());
    }
    
    private void aplicarFiltros() {
        citasFiltradas.clear();
        
        LocalDate fechaFiltro = datePickerFiltro.getValue();
        String estadoFiltro = comboEstadoFiltro.getValue();
        
        for (Consulta consulta : todasLasCitas) {
            boolean cumpleFiltroFecha = fechaFiltro == null || consulta.getFecha().equals(fechaFiltro);
            boolean cumpleFiltroEstado = "Todos".equals(estadoFiltro) || consulta.getEstado().equals(estadoFiltro);
            
            if (cumpleFiltroFecha && cumpleFiltroEstado) {
                citasFiltradas.add(consulta);
            }
        }
        
        actualizarContador();
    }
    
    private void actualizarContador() {
        int total = citasFiltradas.size();
        lblContadorCitas.setText("(" + total + " citas encontradas)");
    }
    
    private void mostrarInformacionCita(Consulta consulta) {
        Paciente paciente = obtenerPacientePorId(consulta.getIdPaciente());
        
        if (paciente != null) {
            lblPacienteSeleccionado.setText(paciente.getNombre());
            lblTelefonoPaciente.setText(paciente.getTelefono());
            lblMatriculaPaciente.setText(paciente.getMatricula());
        } else {
            lblPacienteSeleccionado.setText("N/A");
            lblTelefonoPaciente.setText("N/A");
            lblMatriculaPaciente.setText("N/A");
        }
        
        lblFechaHoraSeleccionada.setText(
            consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
            consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        
        lblMotivoSeleccionado.setText(consulta.getMotivo());
        lblObservacionesSeleccionadas.setText(consulta.getObservaciones() != null ? consulta.getObservaciones() : "Sin observaciones");
        
        infoCitaSeleccionada.setVisible(true);
        
        // Configurar botones según el estado de la cita
        configurarBotonesAccion(consulta);
    }
    
    private void ocultarInformacionCita() {
        infoCitaSeleccionada.setVisible(false);
    }
    
    private void configurarBotonesAccion(Consulta consulta) {
        if ("programada".equals(consulta.getEstado())) {
            btnCompletarCita.setVisible(true);
            btnCancelarCita.setVisible(true);
        } else {
            btnCompletarCita.setVisible(false);
            btnCancelarCita.setVisible(false);
        }
    }
    
    @FXML
    private void completarCita() {
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para completar", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/CompletarCita.fxml"));
            Parent completarCitaRoot = loader.load();
            
            CompletarCitaController controller = loader.getController();
            controller.cargarInformacionCita(citaSeleccionada);
            
            Scene nuevaEscena = new Scene(completarCitaRoot);
            Stage currentStage = (Stage) btnCompletarCita.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Completar Cita - Doctor");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de completar cita", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelarCita() {
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para cancelar", Alert.AlertType.ERROR);
            return;
        }
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Cancelación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea cancelar esta cita?\n\nPaciente: " + lblPacienteSeleccionado.getText() + "\nFecha: " + lblFechaHoraSeleccionada.getText());
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (Consulta.cancelarConsulta(citaSeleccionada.getId(), "Cancelada por el médico")) {
                    mostrarAlerta("Éxito", "Cita cancelada correctamente", Alert.AlertType.INFORMATION);
                    cargarCitas(); // Recargar la tabla
                } else {
                    mostrarAlerta("Error", "No se pudo cancelar la cita", Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    @FXML
    private void limpiarFiltros() {
        datePickerFiltro.setValue(null);
        comboEstadoFiltro.setValue("Todos");
        aplicarFiltros();
    }
    
    @FXML
    private void verHistorialPaciente() {
        if (citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita para ver el historial del paciente", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            // Obtener el paciente de la cita seleccionada
            Paciente paciente = obtenerPacientePorId(citaSeleccionada.getIdPaciente());
            if (paciente == null) {
                mostrarAlerta("Error", "No se pudo obtener la información del paciente", Alert.AlertType.ERROR);
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/HistorialMedico.fxml"));
            Parent historialRoot = loader.load();
            
            HistorialMedicoController controller = loader.getController();
            controller.cargarHistorialPaciente(paciente);
            
            Scene nuevaEscena = new Scene(historialRoot);
            Stage currentStage = (Stage) btnHistorialPaciente.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Historial Médico - " + paciente.getNombre());
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el historial médico", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void verReportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/ReportesEstadisticas.fxml"));
            Parent reportesRoot = loader.load();
            
            Scene nuevaEscena = new Scene(reportesRoot);
            Stage currentStage = (Stage) btnReportes.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Reportes y Estadísticas - Doctor");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir los reportes", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainDoctor.fxml"));
            Parent mainDoctorRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainDoctorRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Doctor");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al panel principal", Alert.AlertType.ERROR);
        }
    }
    
    private Paciente obtenerPacientePorId(int idPaciente) {
        List<Paciente> pacientes = Paciente.obtenerTodos();
        return pacientes.stream()
                .filter(p -> p.getId() == idPaciente)
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
