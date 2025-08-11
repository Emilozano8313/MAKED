package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.regex.Pattern;

public class AgregarUsuarioController {

    // Elementos del menú lateral
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemUsuarios;
    @FXML private VBox menuItemMedicos;
    @FXML private VBox menuItemCitas;
    @FXML private VBox menuItemSalir;
    
    // Elementos del formulario
    @FXML private TextField nombreField;
    @FXML private TextField apellidosField;
    @FXML private TextField matriculaField;
    @FXML private TextField curpField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> tipoUsuarioCombo;
    
    // Botones
    @FXML private Button btnAgregar;
    @FXML private Button btnCancelar;
    @FXML private Button btnLimpiar;
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    @FXML
    private void initialize() {
        configurarMenuExpandible();
        configurarEventosMenuLateral();
        configurarEventosBotones();
        cargarTiposUsuario();
    }
    
    private void configurarMenuExpandible() {
        // Configurar animación del menú
        timelineExpansion = new Timeline();
        
        // Verificar que los elementos del menú no sean null antes de configurar eventos
        if (menuLateral != null) {
            // Eventos del menú lateral
            menuLateral.setOnMouseEntered(e -> expandirMenu());
            menuLateral.setOnMouseExited(e -> contraerMenu());
            
            // Mostrar etiquetas inicialmente
            mostrarEtiquetasMenu(false);
        }
    }
    
    private void expandirMenu() {
        if (!menuExpandido && menuLateral != null && timelineExpansion != null) {
            menuExpandido = true;
            
            // Detener animación anterior si está en curso
            timelineExpansion.stop();
            
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
        if (menuExpandido && menuLateral != null && timelineExpansion != null) {
            menuExpandido = false;
            
            // Detener animación anterior si está en curso
            timelineExpansion.stop();
            
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
        if (menuItemUsuarios != null) {
            for (javafx.scene.Node node : menuItemUsuarios.getChildren()) {
                if (node instanceof Label) {
                    node.setVisible(mostrar);
                    node.setManaged(mostrar);
                }
            }
        }
        if (menuItemMedicos != null) {
            for (javafx.scene.Node node : menuItemMedicos.getChildren()) {
                if (node instanceof Label) {
                    node.setVisible(mostrar);
                    node.setManaged(mostrar);
                }
            }
        }
        if (menuItemCitas != null) {
            for (javafx.scene.Node node : menuItemCitas.getChildren()) {
                if (node instanceof Label) {
                    node.setVisible(mostrar);
                    node.setManaged(mostrar);
                }
            }
        }
        if (menuItemSalir != null) {
            for (javafx.scene.Node node : menuItemSalir.getChildren()) {
                if (node instanceof Label) {
                    node.setVisible(mostrar);
                    node.setManaged(mostrar);
                }
            }
        }
    }
    
    private void configurarEventosMenuLateral() {
        // Configurar eventos de clic para cada elemento del menú
        if (menuItemUsuarios != null) {
            menuItemUsuarios.setOnMouseClicked(e -> abrirGestionUsuarios());
        }
        if (menuItemMedicos != null) {
            menuItemMedicos.setOnMouseClicked(e -> abrirGestionMedicos());
        }
        if (menuItemCitas != null) {
            menuItemCitas.setOnMouseClicked(e -> abrirGestionCitas());
        }
        if (menuItemSalir != null) {
            menuItemSalir.setOnMouseClicked(e -> salir());
        }
    }
    
    private void configurarEventosBotones() {
        // Configurar eventos de los botones
        if (btnAgregar != null) {
            btnAgregar.setOnAction(e -> agregarUsuario());
        }
        if (btnCancelar != null) {
            btnCancelar.setOnAction(e -> cancelarAgregar());
        }
        if (btnLimpiar != null) {
            btnLimpiar.setOnAction(e -> limpiarFormulario());
        }
    }
    
    private void cargarTiposUsuario() {
        // Cargar tipos de usuario en el ComboBox (solo Paciente para esta ventana)
        ObservableList<String> tiposUsuario = FXCollections.observableArrayList(
            "Paciente"
        );
        tipoUsuarioCombo.setItems(tiposUsuario);
        tipoUsuarioCombo.setValue("Paciente"); // Seleccionar por defecto
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1080, 720);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
        }
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
    
    @FXML
    private void agregarUsuario() {
        if (validarCampos()) {
            // Aquí se guardaría el paciente en la base de datos
            System.out.println("Paciente agregado: " + nombreField.getText() + " " + apellidosField.getText());
            
            mostrarAlerta("Éxito", "Paciente agregado correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
        }
    }
    
    @FXML
    private void cancelarAgregar() {
        mostrarAlerta("Información", "Operación cancelada", Alert.AlertType.INFORMATION);
        volverAGestionPacientes();
    }
    
    @FXML
    private void volverAlMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRoot, 1080, 720);
            Stage currentStage = (Stage) btnAgregar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Recepcionista");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos() {
        // Validar campos vacíos
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (apellidosField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Los apellidos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        
        if (matriculaField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La matrícula es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        
        if (curpField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El CURP es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (correoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El correo es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (telefonoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (passwordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La contraseña es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        
        if (confirmPasswordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar la contraseña", Alert.AlertType.ERROR);
            return false;
        }
        
        // No validar tipo de usuario ya que siempre será "Paciente"
        // tipoUsuarioCombo.setValue("Paciente") se establece automáticamente
        
        // Validar formato de correo
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailPattern, correoField.getText().trim())) {
            mostrarAlerta("Error", "El formato del correo no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de CURP (18 caracteres alfanuméricos)
        String curpPattern = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$";
        if (!Pattern.matches(curpPattern, curpField.getText().trim().toUpperCase())) {
            mostrarAlerta("Error", "El formato del CURP no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de teléfono (10 dígitos)
        String telefonoPattern = "^[0-9]{10}$";
        if (!Pattern.matches(telefonoPattern, telefonoField.getText().trim().replaceAll("[^0-9]", ""))) {
            mostrarAlerta("Error", "El formato del teléfono no es válido (debe tener 10 dígitos)", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar longitud de contraseña
        if (passwordField.getText().length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void limpiarFormulario() {
        nombreField.clear();
        apellidosField.clear();
        matriculaField.clear();
        curpField.clear();
        correoField.clear();
        telefonoField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        tipoUsuarioCombo.setValue("Paciente");
        mostrarAlerta("Información", "Formulario limpiado", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void volverAGestionPacientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            Stage currentStage = (Stage) btnAgregar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Pacientes - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la gestión de pacientes", Alert.AlertType.ERROR);
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
