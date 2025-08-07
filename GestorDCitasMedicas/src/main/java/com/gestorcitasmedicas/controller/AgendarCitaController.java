package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AgendarCitaController {

    // Elementos del menú lateral
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemUsuarios;
    @FXML private VBox menuItemMedicos;
    @FXML private VBox menuItemCitas;
    @FXML private VBox menuItemSalir;
    
    // Elementos del formulario
    @FXML private Label lblTitulo;
    @FXML private ComboBox<String> comboPaciente;
    @FXML private ComboBox<String> comboMedico;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> comboHorario;
    @FXML private TextArea txtMotivo;
    @FXML private GridPane gridCalendario;
    @FXML private Label lblTituloCalendario;
    
    // Botones
    @FXML private Button btnAgendar;
    @FXML private Button btnActualizar;
    @FXML private Button btnCancelar;
    @FXML private Button btnMesAnterior;
    @FXML private Button btnMesSiguiente;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;
    
    // Datos simulados
    private ObservableList<String> pacientes;
    private ObservableList<String> medicos;
    private ObservableList<String> horariosDisponibles;
    private Map<LocalDate, List<String>> disponibilidadPorFecha;
    
    // Modo edición
    private boolean modoEdicion = false;
    private Cita citaEnEdicion;
    
    // Fecha del calendario
    private LocalDate fechaCalendario;

    @FXML
    private void initialize() {
        configurarMenuExpandible();
        configurarEventosMenuLateral();
        cargarDatosIniciales();
        configurarEventosControles();
        configurarEventosBotones();
        generarCalendario();
    }
    
    private void configurarMenuExpandible() {
        // Configurar animación del menú
        timelineExpansion = new Timeline();
        
        // Eventos del menú lateral
        menuLateral.setOnMouseEntered(e -> expandirMenu());
        menuLateral.setOnMouseExited(e -> contraerMenu());
        
        // Mostrar etiquetas inicialmente
        mostrarEtiquetasMenu(false);
    }
    
    private void expandirMenu() {
        if (!menuExpandido) {
            menuExpandido = true;
            
            // Animación de expansión
            KeyValue keyValue = new KeyValue(menuLateral.prefWidthProperty(), 200);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(keyFrame);
            timelineExpansion.play();
            
            mostrarEtiquetasMenu(true);
        }
    }
    
    private void contraerMenu() {
        if (menuExpandido) {
            menuExpandido = false;
            
            // Animación de contracción
            KeyValue keyValue = new KeyValue(menuLateral.prefWidthProperty(), 60);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
            timelineExpansion.getKeyFrames().clear();
            timelineExpansion.getKeyFrames().add(keyFrame);
            timelineExpansion.play();
            
            mostrarEtiquetasMenu(false);
        }
    }
    
    private void mostrarEtiquetasMenu(boolean mostrar) {
        // Mostrar/ocultar etiquetas de texto en los elementos del menú
        for (javafx.scene.Node node : menuItemUsuarios.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
        for (javafx.scene.Node node : menuItemMedicos.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
        for (javafx.scene.Node node : menuItemCitas.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
        for (javafx.scene.Node node : menuItemSalir.getChildren()) {
            if (node instanceof Label) {
                node.setVisible(mostrar);
                node.setManaged(mostrar);
            }
        }
    }
    
    private void configurarEventosMenuLateral() {
        // Configurar eventos de clic para cada elemento del menú
        menuItemUsuarios.setOnMouseClicked(e -> abrirGestionUsuarios());
        menuItemMedicos.setOnMouseClicked(e -> abrirGestionMedicos());
        menuItemCitas.setOnMouseClicked(e -> abrirGestionCitas());
        menuItemSalir.setOnMouseClicked(e -> salir());
    }
    
    private void abrirGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirGestionMedicos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1200, 800);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirGestionCitas() {
        // Ya estamos en la ventana de gestión de citas
        mostrarAlerta("Información", "Ya estás en la ventana de gestión de citas", Alert.AlertType.INFORMATION);
    }
    
    private void salir() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent loginRoot = loader.load();
            
            Scene nuevaEscena = new Scene(loginRoot, 759, 422);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Bienvenido a tu gestor de citas medicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cerrar sesión", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDatosIniciales() {
        // Cargar pacientes simulados
        pacientes = FXCollections.observableArrayList(
            "María García", "Carlos Rodríguez", "Laura Martínez", "Miguel Torres",
            "Carmen Vega", "Roberto Silva", "Ana López", "Fernando Díaz"
        );
        comboPaciente.setItems(pacientes);
        
        // Cargar médicos simulados
        medicos = FXCollections.observableArrayList(
            "Dr. Juan Pérez", "Dra. Ana López", "Dr. Roberto Silva", "Dra. Patricia Ruiz",
            "Dr. Fernando Díaz", "Dra. Carmen Vega", "Dr. Miguel Torres", "Dra. Laura Martínez"
        );
        comboMedico.setItems(medicos);
        
        // Cargar horarios disponibles
        horariosDisponibles = FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
        );
        comboHorario.setItems(horariosDisponibles);
        
        // Generar disponibilidad simulada
        generarDisponibilidadSimulada();
        
        // Configurar fecha actual
        datePicker.setValue(LocalDate.now());
    }
    
    private void generarDisponibilidadSimulada() {
        disponibilidadPorFecha = new HashMap<>();
        LocalDate fechaInicio = LocalDate.now();
        
        // Generar disponibilidad para los próximos 30 días
        for (int i = 0; i < 30; i++) {
            LocalDate fecha = fechaInicio.plusDays(i);
            List<String> horariosDisponibles = new ArrayList<>();
            
            // Simular disponibilidad (algunos días más ocupados que otros)
            if (fecha.getDayOfWeek().getValue() <= 5) { // Lunes a viernes
                if (i % 3 != 0) { // Algunos días más ocupados
                    // Agregar solo 8 horarios disponibles
                    horariosDisponibles.addAll(this.horariosDisponibles.subList(0, 8));
                } else {
                    // Agregar todos los horarios disponibles
                    horariosDisponibles.addAll(this.horariosDisponibles);
                }
            }
            
            disponibilidadPorFecha.put(fecha, horariosDisponibles);
        }
    }
    
    private void configurarEventosControles() {
        // Evento para cambio de fecha
        datePicker.setOnAction(e -> {
            LocalDate fechaSeleccionada = datePicker.getValue();
            if (fechaSeleccionada != null) {
                fechaCalendario = fechaSeleccionada; // Sincronizar calendario con DatePicker
                actualizarHorariosDisponibles(fechaSeleccionada);
                generarCalendario(); // Actualizar calendario cuando cambie la fecha
            }
        });
        
        // Configurar eventos de botones
        configurarEventosBotones();
    }
    
    private void actualizarHorariosDisponibles(LocalDate fecha) {
        List<String> horarios = disponibilidadPorFecha.get(fecha);
        if (horarios != null) {
            comboHorario.getItems().clear();
            comboHorario.getItems().addAll(horarios);
        } else {
            comboHorario.getItems().clear();
        }
    }
    
    private void configurarEventosBotones() {
        btnAgendar.setOnAction(e -> agendarCita());
        btnActualizar.setOnAction(e -> actualizarCita());
        btnCancelar.setOnAction(e -> cancelarCita());
        
        // Eventos para navegación del calendario
        btnMesAnterior.setOnAction(e -> mesAnterior());
        btnMesSiguiente.setOnAction(e -> mesSiguiente());
    }
    
    private void mesAnterior() {
        if (fechaCalendario != null) {
            fechaCalendario = fechaCalendario.minusMonths(1);
            generarCalendario();
        }
    }
    
    private void mesSiguiente() {
        if (fechaCalendario != null) {
            fechaCalendario = fechaCalendario.plusMonths(1);
            generarCalendario();
        }
    }
    
    private void generarCalendario() {
        // Limpiar calendario
        gridCalendario.getChildren().clear();
        
        // Inicializar fechaCalendario si es null
        if (fechaCalendario == null) {
            fechaCalendario = datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now();
        }
        
        LocalDate primerDia = fechaCalendario.withDayOfMonth(1);
        int diasEnMes = fechaCalendario.lengthOfMonth();
        
        // Actualizar título del calendario
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String tituloCalendario = meses[fechaCalendario.getMonthValue() - 1] + " " + fechaCalendario.getYear();
        lblTituloCalendario.setText(tituloCalendario);
        
        // Agregar días de la semana
        String[] diasSemana = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (int i = 0; i < 7; i++) {
            Label label = new Label(diasSemana[i]);
            label.setStyle("-fx-font-weight: bold; -fx-text-fill: #3B6F89;");
            gridCalendario.add(label, i, 0);
        }
        
        // Agregar días del mes
        int diaSemana = primerDia.getDayOfWeek().getValue() % 7;
        int fila = 1;
        int columna = diaSemana;
        
        for (int dia = 1; dia <= diasEnMes; dia++) {
            LocalDate fecha = fechaCalendario.withDayOfMonth(dia);
            
            // Crear círculo para el día
            Circle circulo = new Circle(15);
            
            // Verificar disponibilidad
            List<String> horarios = disponibilidadPorFecha.get(fecha);
            if (horarios != null && !horarios.isEmpty()) {
                circulo.setFill(Color.web("#4ECDC4")); // Verde - disponible
            } else {
                circulo.setFill(Color.web("#FF6B6B")); // Rojo - no disponible
            }
            
            // Crear contenedor para el día
            VBox diaBox = new VBox(2);
            diaBox.setAlignment(javafx.geometry.Pos.CENTER);
            
            Label labelDia = new Label(String.valueOf(dia));
            labelDia.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            
            // Hacer el día clickeable para seleccionar fecha
            diaBox.setOnMouseClicked(e -> {
                datePicker.setValue(fecha);
                actualizarHorariosDisponibles(fecha);
            });
            
            diaBox.getChildren().addAll(circulo, labelDia);
            
            gridCalendario.add(diaBox, columna, fila);
            
            columna++;
            if (columna > 6) {
                columna = 0;
                fila++;
            }
        }
    }
    
    @FXML
    private void agendarCita() {
        if (validarCampos()) {
            // Crear nueva cita
            Cita nuevaCita = new Cita(
                comboPaciente.getValue(),
                comboMedico.getValue(),
                datePicker.getValue(),
                comboHorario.getValue(),
                txtMotivo.getText()
            );
            
            // Aquí se guardaría la cita en la base de datos
            System.out.println("Cita agendada: " + nuevaCita.getPaciente() + " - " + nuevaCita.getMedico());
            
            mostrarAlerta("Éxito", "Cita agendada correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
        }
    }
    
    @FXML
    private void actualizarCita() {
        if (validarCampos() && citaEnEdicion != null) {
            // Actualizar cita existente
            citaEnEdicion.setPaciente(comboPaciente.getValue());
            citaEnEdicion.setMedico(comboMedico.getValue());
            citaEnEdicion.setFecha(datePicker.getValue());
            citaEnEdicion.setHora(comboHorario.getValue());
            citaEnEdicion.setMotivo(txtMotivo.getText());
            
            System.out.println("Cita actualizada: " + citaEnEdicion.getPaciente() + " - " + citaEnEdicion.getMedico());
            
            mostrarAlerta("Éxito", "Cita actualizada correctamente", Alert.AlertType.INFORMATION);
            cambiarAModoAgendar();
        }
    }
    
    @FXML
    private void cancelarCita() {
        if (modoEdicion) {
            cambiarAModoAgendar();
        } else {
            limpiarFormulario();
        }
    }
    
    private boolean validarCampos() {
        if (comboPaciente.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un paciente", Alert.AlertType.ERROR);
            return false;
        }
        if (comboMedico.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un médico", Alert.AlertType.ERROR);
            return false;
        }
        if (datePicker.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha", Alert.AlertType.ERROR);
            return false;
        }
        if (comboHorario.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un horario", Alert.AlertType.ERROR);
            return false;
        }
        if (txtMotivo.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un motivo", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    
    private void limpiarFormulario() {
        comboPaciente.setValue(null);
        comboMedico.setValue(null);
        datePicker.setValue(LocalDate.now());
        comboHorario.setValue(null);
        txtMotivo.clear();
    }
    
    private void cambiarAModoAgendar() {
        modoEdicion = false;
        citaEnEdicion = null;
        lblTitulo.setText("Agendar Cita Médica");
        btnAgendar.setVisible(true);
        btnActualizar.setVisible(false);
        limpiarFormulario();
    }
    
    // Método para cambiar a modo edición (llamado desde el panel principal)
    public void cargarCitaParaEditar(Cita cita) {
        modoEdicion = true;
        citaEnEdicion = cita;
        
        // Cargar datos de la cita en el formulario
        comboPaciente.setValue(cita.getPaciente());
        comboMedico.setValue(cita.getMedico());
        datePicker.setValue(cita.getFecha());
        comboHorario.setValue(cita.getHora());
        txtMotivo.setText(cita.getMotivo());
        
        // Cambiar interfaz a modo edición
        lblTitulo.setText("Actualizar Cita Médica");
        btnAgendar.setVisible(false);
        btnActualizar.setVisible(true);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    private void abrirPerfil() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mi Perfil");
        alert.setHeaderText(null);
        alert.setContentText("Función de perfil en desarrollo");
        alert.showAndWait();
    }
    
    // Clase interna para representar una cita
    public static class Cita {
        private String paciente;
        private String medico;
        private LocalDate fecha;
        private String hora;
        private String motivo;
        
        public Cita(String paciente, String medico, LocalDate fecha, String hora, String motivo) {
            this.paciente = paciente;
            this.medico = medico;
            this.fecha = fecha;
            this.hora = hora;
            this.motivo = motivo;
        }
        
        // Getters y Setters
        public String getPaciente() { return paciente; }
        public void setPaciente(String paciente) { this.paciente = paciente; }
        
        public String getMedico() { return medico; }
        public void setMedico(String medico) { this.medico = medico; }
        
        public LocalDate getFecha() { return fecha; }
        public void setFecha(LocalDate fecha) { this.fecha = fecha; }
        
        public String getHora() { return hora; }
        public void setHora(String hora) { this.hora = hora; }
        
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }
}
