package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PanelPrincipalController {

    @FXML
    private Label consultasProgramadasLabel;
    
    @FXML
    private Label consultasConcluidasLabel;
    
    @FXML
    private Label consultasReprogramadasLabel;
    
    @FXML
    private DatePicker calendario;
    
    @FXML
    private VBox contenedorConsultas;

    // Datos simulados de consultas
    private List<Consulta> consultasDelDia;
    
    @FXML
    private void initialize() {
        // Configurar el calendario
        configurarCalendario();
        
        // Cargar datos iniciales
        cargarDatosIniciales();
    }
    
    private void configurarCalendario() {
        // Establecer fecha actual
        calendario.setValue(LocalDate.now());
        
        // Configurar el evento de cambio de fecha
        calendario.setOnAction(event -> {
            LocalDate fechaSeleccionada = calendario.getValue();
            cargarConsultasDelDia(fechaSeleccionada);
        });
        
        // Cargar consultas del día actual
        cargarConsultasDelDia(LocalDate.now());
    }
    
    private void cargarDatosIniciales() {
        // Simular datos de contadores
        actualizarContadores(15, 8, 3);
        
        // Cargar consultas del día actual
        cargarConsultasDelDia(LocalDate.now());
    }
    
    private void actualizarContadores(int programadas, int concluidas, int reprogramadas) {
        if (consultasProgramadasLabel != null) {
            consultasProgramadasLabel.setText(String.valueOf(programadas));
        }
        if (consultasConcluidasLabel != null) {
            consultasConcluidasLabel.setText(String.valueOf(concluidas));
        }
        if (consultasReprogramadasLabel != null) {
            consultasReprogramadasLabel.setText(String.valueOf(reprogramadas));
        }
    }
    
    private void cargarConsultasDelDia(LocalDate fecha) {
        // Limpiar contenedor
        if (contenedorConsultas != null) {
            contenedorConsultas.getChildren().clear();
        }
        
        // Simular consultas del día
        consultasDelDia = generarConsultasSimuladas(fecha);
        
        // Crear y mostrar las consultas
        for (Consulta consulta : consultasDelDia) {
            VBox consultaBox = crearConsultaBox(consulta);
            if (contenedorConsultas != null) {
                contenedorConsultas.getChildren().add(consultaBox);
            }
        }
    }
    
    private List<Consulta> generarConsultasSimuladas(LocalDate fecha) {
        List<Consulta> consultas = new ArrayList<>();
        
        // Consultas simuladas para el día
        consultas.add(new Consulta("Dr. Juan Pérez", "María García", "Consultorio 1", 
            LocalTime.of(9, 0), LocalTime.of(9, 30), "Consulta general"));
        consultas.add(new Consulta("Dra. Ana López", "Carlos Rodríguez", "Consultorio 2", 
            LocalTime.of(10, 0), LocalTime.of(10, 30), "Revisión"));
        consultas.add(new Consulta("Dr. Roberto Silva", "Laura Martínez", "Consultorio 3", 
            LocalTime.of(11, 0), LocalTime.of(11, 30), "Control"));
        consultas.add(new Consulta("Dra. Patricia Ruiz", "Miguel Torres", "Consultorio 1", 
            LocalTime.of(14, 0), LocalTime.of(14, 30), "Consulta especializada"));
        consultas.add(new Consulta("Dr. Fernando Díaz", "Carmen Vega", "Consultorio 2", 
            LocalTime.of(15, 0), LocalTime.of(15, 30), "Seguimiento"));
        
        return consultas;
    }
    
    private VBox crearConsultaBox(Consulta consulta) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: #E7ECF0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        box.setPrefWidth(300);
        
        // Título con doctor y paciente
        Label tituloLabel = new Label(consulta.getDoctor() + " - " + consulta.getPaciente());
        tituloLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        // Información de la consulta
        Label consultorioLabel = new Label("Consultorio: " + consulta.getConsultorio());
        Label horarioLabel = new Label("Horario: " + consulta.getHoraInicio() + " - " + consulta.getHoraFin());
        Label motivoLabel = new Label("Motivo: " + consulta.getMotivo());
        
        // Botones de acción
        HBox botonesBox = new HBox(10);
        Button btnEditar = new Button("Editar");
        Button btnCancelar = new Button("Cancelar");
        
        btnEditar.setStyle("-fx-background-color: #3B6F89; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");
        
        // Evento para editar la consulta
        btnEditar.setOnAction(event -> editarConsulta(consulta));
        
        botonesBox.getChildren().addAll(btnEditar, btnCancelar);
        
        box.getChildren().addAll(tituloLabel, consultorioLabel, horarioLabel, motivoLabel, botonesBox);
        
        return box;
    }
    
    private void editarConsulta(Consulta consulta) {
        try {
            // Cargar la ventana de agendar citas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            // Obtener el controlador
            AgendarCitaController controller = loader.getController();
            
            // Crear objeto Cita para editar
            AgendarCitaController.Cita citaParaEditar = new AgendarCitaController.Cita(
                consulta.getPaciente(),
                consulta.getDoctor(),
                LocalDate.now(), // Usar fecha actual como ejemplo
                consulta.getHoraInicio().toString(),
                consulta.getMotivo()
            );
            
            // Cargar la cita en modo edición
            controller.cargarCitaParaEditar(citaParaEditar);
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) calendario.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Actualizar Cita Médica - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de edición", Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Método para navegar a agendar citas desde el panel principal
    public void navegarAAgendarCitas() {
        try {
            // Cargar la ventana de agendar citas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) calendario.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita Médica - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
        }
    }
    
    // Clase interna para representar una consulta
    private static class Consulta {
        private String doctor;
        private String paciente;
        private String consultorio;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private String motivo;
        
        public Consulta(String doctor, String paciente, String consultorio, 
                       LocalTime horaInicio, LocalTime horaFin, String motivo) {
            this.doctor = doctor;
            this.paciente = paciente;
            this.consultorio = consultorio;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.motivo = motivo;
        }
        
        // Getters
        public String getDoctor() { return doctor; }
        public String getPaciente() { return paciente; }
        public String getConsultorio() { return consultorio; }
        public LocalTime getHoraInicio() { return horaInicio; }
        public LocalTime getHoraFin() { return horaFin; }
        public String getMotivo() { return motivo; }
    }
}
