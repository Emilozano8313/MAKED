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

public class HistorialMedicoController {

    @FXML private Label lblNombrePaciente;
    @FXML private Label lblMatricula;
    @FXML private Label lblTelefono;
    @FXML private Label lblCurp;
    
    @FXML private ComboBox<String> comboEstadoFiltro;
    @FXML private ComboBox<String> comboMedicoFiltro;
    @FXML private Button btnLimpiarFiltros;
    @FXML private Button btnRegresar;
    
    @FXML private TableView<Consulta> tablaHistorial;
    @FXML private TableColumn<Consulta, Integer> colId;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private TableColumn<Consulta, String> colEspecialidad;
    @FXML private TableColumn<Consulta, String> colMotivo;
    @FXML private TableColumn<Consulta, String> colEstado;
    @FXML private TableColumn<Consulta, String> colAcciones;
    
    @FXML private Label lblContadorConsultas;
    
    @FXML private VBox detallesConsulta;
    @FXML private Label lblMedicoConsulta;
    @FXML private Label lblFechaHoraConsulta;
    @FXML private Label lblMotivoConsulta;
    @FXML private Label lblDiagnosticoConsulta;
    @FXML private Label lblTratamientoConsulta;
    
    private ObservableList<Consulta> todasLasConsultas = FXCollections.observableArrayList();
    private ObservableList<Consulta> consultasFiltradas = FXCollections.observableArrayList();
    private Paciente pacienteActual = null;
    private Consulta consultaSeleccionada = null;

    @FXML
    private void initialize() {
        System.out.println("HistorialMedicoController inicializando...");
        
        configurarTabla();
        configurarFiltros();
        configurarEventos();
        
        System.out.println("HistorialMedicoController inicializado correctamente");
    }
    
    public void cargarHistorialPaciente(Paciente paciente) {
        this.pacienteActual = paciente;
        
        try {
            // Mostrar información del paciente
            lblNombrePaciente.setText(paciente.getNombre());
            lblMatricula.setText(paciente.getMatricula());
            lblTelefono.setText(paciente.getTelefono());
            lblCurp.setText(paciente.getCurp());
            
            // Cargar todas las consultas del paciente
            List<Consulta> consultas = Consulta.obtenerPorPaciente(paciente.getId());
            todasLasConsultas.clear();
            todasLasConsultas.addAll(consultas);
            
            aplicarFiltros();
            
        } catch (Exception e) {
            System.err.println("Error al cargar historial del paciente: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el historial del paciente", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFecha.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            return new SimpleStringProperty(consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        colHora.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            return new SimpleStringProperty(consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        });
        colMedico.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
            return new SimpleStringProperty(medico != null ? medico.getNombre() : "N/A");
        });
        colEspecialidad.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
            return new SimpleStringProperty(medico != null ? medico.getEspecialidad() : "N/A");
        });
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAcciones.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            String acciones = "";
            if ("completada".equals(consulta.getEstado())) {
                acciones = "Ver detalles";
            } else if ("programada".equals(consulta.getEstado())) {
                acciones = "Pendiente";
            } else if ("cancelada".equals(consulta.getEstado())) {
                acciones = "Ver motivo";
            }
            return new SimpleStringProperty(acciones);
        });
        
        // Configurar tabla
        tablaHistorial.setItems(consultasFiltradas);
    }
    
    private void configurarFiltros() {
        // Configurar ComboBox de estados
        ObservableList<String> estados = FXCollections.observableArrayList("Todos", "programada", "completada", "cancelada");
        comboEstadoFiltro.setItems(estados);
        comboEstadoFiltro.setValue("Todos");
        
        // Configurar ComboBox de médicos
        ObservableList<String> medicos = FXCollections.observableArrayList("Todos");
        List<Medico> listaMedicos = Medico.obtenerTodos();
        for (Medico medico : listaMedicos) {
            medicos.add(medico.getNombre());
        }
        comboMedicoFiltro.setItems(medicos);
        comboMedicoFiltro.setValue("Todos");
    }
    
    private void configurarEventos() {
        // Evento de selección en la tabla
        tablaHistorial.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                consultaSeleccionada = newValue;
                mostrarDetallesConsulta(newValue);
            } else {
                consultaSeleccionada = null;
                ocultarDetallesConsulta();
            }
        });
        
        // Eventos de filtros
        comboEstadoFiltro.setOnAction(event -> aplicarFiltros());
        comboMedicoFiltro.setOnAction(event -> aplicarFiltros());
    }
    
    private void aplicarFiltros() {
        consultasFiltradas.clear();
        
        String estadoFiltro = comboEstadoFiltro.getValue();
        String medicoFiltro = comboMedicoFiltro.getValue();
        
        for (Consulta consulta : todasLasConsultas) {
            boolean cumpleFiltroEstado = "Todos".equals(estadoFiltro) || consulta.getEstado().equals(estadoFiltro);
            boolean cumpleFiltroMedico = "Todos".equals(medicoFiltro);
            
            if (!cumpleFiltroMedico) {
                Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
                cumpleFiltroMedico = medico != null && medico.getNombre().equals(medicoFiltro);
            }
            
            if (cumpleFiltroEstado && cumpleFiltroMedico) {
                consultasFiltradas.add(consulta);
            }
        }
        
        actualizarContador();
    }
    
    private void actualizarContador() {
        int total = consultasFiltradas.size();
        lblContadorConsultas.setText("(" + total + " consultas encontradas)");
    }
    
    private void mostrarDetallesConsulta(Consulta consulta) {
        Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
        
        if (medico != null) {
            lblMedicoConsulta.setText(medico.getNombre() + " - " + medico.getEspecialidad());
        } else {
            lblMedicoConsulta.setText("N/A");
        }
        
        lblFechaHoraConsulta.setText(
            consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " +
            consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        
        lblMotivoConsulta.setText(consulta.getMotivo());
        lblDiagnosticoConsulta.setText(consulta.getDiagnostico() != null ? consulta.getDiagnostico() : "No disponible");
        lblTratamientoConsulta.setText(consulta.getTratamiento() != null ? consulta.getTratamiento() : "No disponible");
        
        detallesConsulta.setVisible(true);
    }
    
    private void ocultarDetallesConsulta() {
        detallesConsulta.setVisible(false);
    }
    
    @FXML
    private void limpiarFiltros() {
        comboEstadoFiltro.setValue("Todos");
        comboMedicoFiltro.setValue("Todos");
        aplicarFiltros();
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
