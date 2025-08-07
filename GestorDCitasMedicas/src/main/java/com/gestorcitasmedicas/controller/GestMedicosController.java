package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestMedicosController {

    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private VBox menuLateral;
    
    @FXML
    private VBox menuItemUsuarios;
    
    @FXML
    private VBox menuItemMedicos;
    
    @FXML
    private VBox menuItemCitas;
    
    @FXML
    private VBox menuItemSalir;
    
    @FXML
    private TableView<Medico> tablaMedicos;
    
    @FXML
    private TableColumn<Medico, String> colNombre;
    
    @FXML
    private TableColumn<Medico, String> colEspecialidad;
    
    @FXML
    private TableColumn<Medico, String> colCedula;
    
    @FXML
    private TableColumn<Medico, String> colGenero;
    
    @FXML
    private TableColumn<Medico, Integer> colEdad;
    
    @FXML
    private TableColumn<Medico, String> colTelefono;
    
    @FXML
    private TableColumn<Medico, String> colEstatus;

    // Variables para el menú expandible
    private boolean menuExpandido = false;
    private Timeline timelineExpansion;
    
    // Lista de médicos
    private ObservableList<Medico> medicos;

    @FXML
    private void initialize() {
        // Configurar la tabla
        configurarTabla();
        
        // Cargar datos simulados
        cargarDatosSimulados();
        
        // Configurar menú expandible
        configurarMenuExpandible();
        
        // Configurar eventos del menú lateral
        configurarEventosMenuLateral();
        
        // Configurar eventos de botones
        configurarEventosBotones();
    }
    
    private void configurarTabla() {
        // Configurar las columnas
        colNombre.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getNombre()));
        
        colEspecialidad.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEspecialidad()));
        
        colCedula.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCedula()));
        
        colGenero.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getGenero()));
        
        colEdad.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());
        
        colTelefono.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTelefono()));
        
        colEstatus.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEstatus()));
    }
    
    private void cargarDatosSimulados() {
        medicos = FXCollections.observableArrayList();
        
        // Agregar médicos simulados
        medicos.add(new Medico("Dr. Juan Carlos Pérez", "Cardiología", "CARD001", "Masculino", 45, "555-0101", "Activo"));
        medicos.add(new Medico("Dra. Ana María López", "Dermatología", "DERM002", "Femenino", 38, "555-0102", "Activo"));
        medicos.add(new Medico("Dr. Roberto Silva", "Ortopedia", "ORT003", "Masculino", 52, "555-0103", "Activo"));
        medicos.add(new Medico("Dra. Patricia Ruiz", "Pediatría", "PED004", "Femenino", 41, "555-0104", "Activo"));
        medicos.add(new Medico("Dr. Fernando Díaz", "Neurología", "NEU005", "Masculino", 48, "555-0105", "Inactivo"));
        medicos.add(new Medico("Dra. Carmen Vega", "Ginecología", "GIN006", "Femenino", 44, "555-0106", "Activo"));
        medicos.add(new Medico("Dr. Miguel Torres", "Psiquiatría", "PSI007", "Masculino", 39, "555-0107", "Activo"));
        medicos.add(new Medico("Dra. Laura Martínez", "Oftalmología", "OFT008", "Femenino", 36, "555-0108", "Activo"));
        
        tablaMedicos.setItems(medicos);
    }
    
    private void configurarMenuExpandible() {
        // Crear timeline para la animación
        timelineExpansion = new Timeline();
        
        // Configurar eventos del mouse
        menuLateral.setOnMouseEntered(event -> {
            if (!menuExpandido) {
                expandirMenu();
            }
        });
        
        menuLateral.setOnMouseExited(event -> {
            javafx.application.Platform.runLater(() -> {
                if (menuExpandido && !menuLateral.isHover()) {
                    contraerMenu();
                }
            });
        });
    }
    
    private void expandirMenu() {
        if (!menuExpandido && timelineExpansion.getStatus() != javafx.animation.Animation.Status.RUNNING) {
            menuExpandido = true;
            
            // Animar el ancho del menú
            KeyValue kvWidth = new KeyValue(menuLateral.prefWidthProperty(), 160);
            KeyFrame kfWidth = new KeyFrame(Duration.millis(300), kvWidth);
            
            // Mostrar etiquetas
            mostrarEtiquetasMenu(true);
            
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(kfWidth);
            timelineExpansion.play();
        }
    }
    
    private void contraerMenu() {
        if (menuExpandido && timelineExpansion.getStatus() != javafx.animation.Animation.Status.RUNNING) {
            menuExpandido = false;
            
            // Animar el ancho del menú
            KeyValue kvWidth = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame kfWidth = new KeyFrame(Duration.millis(300), kvWidth);
            
            // Ocultar etiquetas
            mostrarEtiquetasMenu(false);
            
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(kfWidth);
            timelineExpansion.play();
        }
    }
    
    private void mostrarEtiquetasMenu(boolean mostrar) {
        for (VBox menuItem : List.of(menuItemUsuarios, menuItemMedicos, menuItemCitas, menuItemSalir)) {
            for (javafx.scene.Node child : menuItem.getChildren()) {
                if (child instanceof Label) {
                    child.setVisible(mostrar);
                    child.setManaged(mostrar);
                }
            }
        }
    }
    
    private void configurarEventosMenuLateral() {
        // Configurar eventos de clic para cada elemento del menú
        menuItemUsuarios.setOnMouseClicked(event -> abrirGestionUsuarios());
        menuItemMedicos.setOnMouseClicked(event -> abrirGestionMedicos());
        menuItemCitas.setOnMouseClicked(event -> abrirGestionCitas());
        menuItemSalir.setOnMouseClicked(event -> salir());
    }
    
    private void configurarEventosBotones() {
        btnAgregar.setOnAction(event -> agregarMedico());
    }
    
    @FXML
    private void abrirPerfil(ActionEvent event) {
        mostrarAlerta("Mi Perfil", "Función de perfil en desarrollo", Alert.AlertType.INFORMATION);
    }
    
    private void abrirGestionUsuarios() {
        try {
            // Cargar la nueva ventana completa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirGestionMedicos() {
        // Ya estamos en gestión de médicos
        mostrarAlerta("Gestión de Médicos", "Ya estás en la gestión de médicos", Alert.AlertType.INFORMATION);
    }
    
    private void abrirGestionCitas() {
        try {
            // Cargar la ventana de agendar citas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            // Crear nueva escena
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1200, 800);
            
            // Obtener la ventana actual y reemplazarla
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita Médica - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
        }
    }
    
    private void salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea salir del sistema?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    Stage currentStage = (Stage) menuLateral.getScene().getWindow();
                    currentStage.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRecepcionistaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRecepcionistaRoot, 1200, 800);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    private void agregarMedico() {
        mostrarAlerta("Agregar Médico", "Función de agregar médico en desarrollo", Alert.AlertType.INFORMATION);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Clase interna para representar un médico
    public static class Medico {
        private String nombre;
        private String especialidad;
        private String cedula;
        private String genero;
        private int edad;
        private String telefono;
        private String estatus;
        
        public Medico(String nombre, String especialidad, String cedula, String genero, 
                     int edad, String telefono, String estatus) {
            this.nombre = nombre;
            this.especialidad = especialidad;
            this.cedula = cedula;
            this.genero = genero;
            this.edad = edad;
            this.telefono = telefono;
            this.estatus = estatus;
        }
        
        // Getters
        public String getNombre() { return nombre; }
        public String getEspecialidad() { return especialidad; }
        public String getCedula() { return cedula; }
        public String getGenero() { return genero; }
        public int getEdad() { return edad; }
        public String getTelefono() { return telefono; }
        public String getEstatus() { return estatus; }
    }
}
