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
<<<<<<< HEAD
=======
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1

import java.io.IOException;
import java.util.regex.Pattern;

public class RegMedicosController {

    // Elementos del menú lateral
    @FXML private VBox menuLateral;
    @FXML private VBox menuItemUsuarios;
    @FXML private VBox menuItemMedicos;
    @FXML private VBox menuItemCitas;
    @FXML private VBox menuItemSalir;
    
    // Elementos del formulario
    @FXML private TextField nombreField;
<<<<<<< HEAD
    @FXML private TextField telefonoField;
    @FXML private TextField especialidadField;
    @FXML private TextField horarioField;
    @FXML private TextField cedulaField;
    @FXML private TextField consultorioField;
    @FXML private TextField correoField;
=======
    @FXML private TextField especialidadField;
    @FXML private TextField cedulaField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private TextField horarioField;
    @FXML private TextField consultorioField;
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    
    // Botones
    @FXML private Button btnRegistrar;
    @FXML private Button btnRegresar;
<<<<<<< HEAD
    @FXML private Button btnLimpiar;
=======
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
    
    // Variables para el menú expandible
    private Timeline timelineExpansion;
    private boolean menuExpandido = false;

    @FXML
    private void initialize() {
        configurarMenuExpandible();
        configurarEventosMenuLateral();
        configurarEventosBotones();
<<<<<<< HEAD
=======
        configurarValidaciones();
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
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
<<<<<<< HEAD
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
=======
        if (menuItemUsuarios != null) {
            Label labelUsuarios = (Label) menuItemUsuarios.getChildren().get(1);
            labelUsuarios.setVisible(mostrar);
            labelUsuarios.setManaged(mostrar);
        }
        if (menuItemMedicos != null) {
            Label labelMedicos = (Label) menuItemMedicos.getChildren().get(1);
            labelMedicos.setVisible(mostrar);
            labelMedicos.setManaged(mostrar);
        }
        if (menuItemCitas != null) {
            Label labelCitas = (Label) menuItemCitas.getChildren().get(1);
            labelCitas.setVisible(mostrar);
            labelCitas.setManaged(mostrar);
        }
        if (menuItemSalir != null) {
            Label labelSalir = (Label) menuItemSalir.getChildren().get(1);
            labelSalir.setVisible(mostrar);
            labelSalir.setManaged(mostrar);
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
        }
    }
    
    private void configurarEventosMenuLateral() {
<<<<<<< HEAD
        // Configurar eventos de clic para cada elemento del menú
        if (menuItemUsuarios != null) {
            menuItemUsuarios.setOnMouseClicked(e -> abrirGestionUsuarios());
        }
        if (menuItemMedicos != null) {
            menuItemMedicos.setOnMouseClicked(e -> abrirGestionMedicos());
        }
        if (menuItemCitas != null) {
            menuItemCitas.setOnMouseClicked(e -> abrirGestionCitas());
=======
        // Eventos para los elementos del menú
        if (menuItemUsuarios != null) {
            menuItemUsuarios.setOnMouseClicked(e -> navegarAGestionUsuarios());
        }
        if (menuItemMedicos != null) {
            menuItemMedicos.setOnMouseClicked(e -> navegarAGestionMedicos());
        }
        if (menuItemCitas != null) {
            menuItemCitas.setOnMouseClicked(e -> navegarAGestionCitas());
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
        }
        if (menuItemSalir != null) {
            menuItemSalir.setOnMouseClicked(e -> salir());
        }
    }
    
    private void configurarEventosBotones() {
<<<<<<< HEAD
        // Configurar eventos de los botones
=======
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
        if (btnRegistrar != null) {
            btnRegistrar.setOnAction(e -> registrarMedico());
        }
        if (btnRegresar != null) {
            btnRegresar.setOnAction(e -> regresar());
        }
<<<<<<< HEAD
        if (btnLimpiar != null) {
            btnLimpiar.setOnAction(e -> limpiarFormulario());
        }
    }
    
    private void abrirGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent gestUsuariosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestUsuariosRoot, 1200, 800);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Pacientes - Gestor de Citas Médicas");
=======
    }
    
    private void configurarValidaciones() {
        // Validación de teléfono (solo números)
        if (telefonoField != null) {
            telefonoField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    telefonoField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        
        // Validación de cédula (solo números)
        if (cedulaField != null) {
            cedulaField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    cedulaField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
    }
    
    @FXML
    private void registrarMedico() {
        if (validarFormulario()) {
            // Aquí se implementaría la lógica para guardar el médico en la base de datos
            mostrarAlerta("Éxito", "Médico registrado correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
        }
    }
    
    private boolean validarFormulario() {
        // Validar campos obligatorios
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            nombreField.requestFocus();
            return false;
        }
        
        if (especialidadField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La especialidad es obligatoria", Alert.AlertType.ERROR);
            especialidadField.requestFocus();
            return false;
        }
        
        if (cedulaField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La cédula es obligatoria", Alert.AlertType.ERROR);
            cedulaField.requestFocus();
            return false;
        }
        
        if (correoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El correo es obligatorio", Alert.AlertType.ERROR);
            correoField.requestFocus();
            return false;
        }
        
        if (telefonoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            telefonoField.requestFocus();
            return false;
        }
        
        if (horarioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El horario es obligatorio", Alert.AlertType.ERROR);
            horarioField.requestFocus();
            return false;
        }
        
        if (consultorioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El consultorio es obligatorio", Alert.AlertType.ERROR);
            consultorioField.requestFocus();
            return false;
        }
        
        if (passwordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La contraseña es obligatoria", Alert.AlertType.ERROR);
            passwordField.requestFocus();
            return false;
        }
        
        if (confirmPasswordField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe confirmar la contraseña", Alert.AlertType.ERROR);
            confirmPasswordField.requestFocus();
            return false;
        }
        
        // Validar formato de correo
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailPattern, correoField.getText().trim())) {
            mostrarAlerta("Error", "El formato del correo no es válido", Alert.AlertType.ERROR);
            correoField.requestFocus();
            return false;
        }
        
        // Validar longitud del teléfono
        if (telefonoField.getText().trim().length() != 10) {
            mostrarAlerta("Error", "El teléfono debe tener 10 dígitos", Alert.AlertType.ERROR);
            telefonoField.requestFocus();
            return false;
        }
        
        // Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            confirmPasswordField.requestFocus();
            return false;
        }
        
        // Validar longitud mínima de contraseña
        if (passwordField.getText().length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            passwordField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        nombreField.clear();
        especialidadField.clear();
        cedulaField.clear();
        correoField.clear();
        telefonoField.clear();
        horarioField.clear();
        consultorioField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
    
    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent root = loader.load();
            
            Scene nuevaEscena = new Scene(root, 1200, 600);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
<<<<<<< HEAD
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de pacientes", Alert.AlertType.ERROR);
        }
    }
    
    private void abrirGestionMedicos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1200, 800);
=======
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    

    
    private void navegarAGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestUsuarios.fxml"));
            Parent root = loader.load();
            
            Scene nuevaEscena = new Scene(root, 1200, 600);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Usuarios - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    private void navegarAGestionMedicos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent root = loader.load();
            
            Scene nuevaEscena = new Scene(root, 1200, 600);
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
<<<<<<< HEAD
    private void abrirGestionCitas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/agendarCitaAdm.fxml"));
            Parent agendarCitaRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agendarCitaRoot, 1080, 720);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Agendar Cita - Gestor de Citas Médicas");
=======
    private void navegarAGestionCitas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/AgendarCita.fxml"));
            Parent root = loader.load();
            
            Scene nuevaEscena = new Scene(root, 1200, 600);
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Citas - Gestor de Citas Médicas");
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
<<<<<<< HEAD
            mostrarAlerta("Error", "No se pudo cargar la ventana de agendar citas", Alert.AlertType.ERROR);
=======
            mostrarAlerta("Error", "No se pudo cargar la ventana de gestión de citas", Alert.AlertType.ERROR);
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
        }
    }
    
    private void salir() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
<<<<<<< HEAD
            Parent loginRoot = loader.load();
            
            Scene nuevaEscena = new Scene(loginRoot, 759, 422);
=======
            Parent root = loader.load();
            
            Scene nuevaEscena = new Scene(root, 759, 422);
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
            Stage currentStage = (Stage) menuLateral.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Bienvenido a tu gestor de citas medicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
<<<<<<< HEAD
            mostrarAlerta("Error", "No se pudo cerrar sesión", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void registrarMedico() {
        if (validarCampos()) {
            // Aquí se guardaría el médico en la base de datos
            System.out.println("Médico registrado: " + nombreField.getText() + " - " + especialidadField.getText());
            
            mostrarAlerta("Éxito", "Médico registrado correctamente", Alert.AlertType.INFORMATION);
            limpiarFormulario();
        }
    }
    
    @FXML
    private void regresar() {
        mostrarAlerta("Información", "Operación cancelada", Alert.AlertType.INFORMATION);
        volverAGestionMedicos();
    }
    
    @FXML
    private void volverAlMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/mainRecepcionista.fxml"));
            Parent mainRoot = loader.load();
            
            Scene nuevaEscena = new Scene(mainRoot, 1080, 720);
            Stage currentStage = (Stage) btnRegistrar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Panel Principal - Recepcionista");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al menú principal", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void limpiarFormulario() {
        nombreField.clear();
        telefonoField.clear();
        especialidadField.clear();
        horarioField.clear();
        cedulaField.clear();
        consultorioField.clear();
        correoField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        mostrarAlerta("Información", "Formulario limpiado", Alert.AlertType.INFORMATION);
    }
    
    private void volverAGestionMedicos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/gestMedicos.fxml"));
            Parent gestMedicosRoot = loader.load();
            
            Scene nuevaEscena = new Scene(gestMedicosRoot, 1200, 800);
            Stage currentStage = (Stage) btnRegistrar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Gestión de Médicos - Gestor de Citas Médicas");
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la gestión de médicos", Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos() {
        // Validar campos vacíos
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (telefonoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El teléfono es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (especialidadField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La especialidad es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        
        if (horarioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El horario es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (cedulaField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "La cédula es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        
        if (consultorioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El consultorio es obligatorio", Alert.AlertType.ERROR);
            return false;
        }
        
        if (correoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El correo institucional es obligatorio", Alert.AlertType.ERROR);
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
        
        // Validar formato de correo
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailPattern, correoField.getText().trim())) {
            mostrarAlerta("Error", "El formato del correo no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de teléfono (10 dígitos)
        String telefonoPattern = "^[0-9]{10}$";
        if (!Pattern.matches(telefonoPattern, telefonoField.getText().trim().replaceAll("[^0-9]", ""))) {
            mostrarAlerta("Error", "El formato del teléfono no es válido (debe tener 10 dígitos)", Alert.AlertType.ERROR);
            return false;
        }
        
        // Validar formato de cédula (8 dígitos)
        String cedulaPattern = "^[0-9]{8}$";
        if (!Pattern.matches(cedulaPattern, cedulaField.getText().trim().replaceAll("[^0-9]", ""))) {
            mostrarAlerta("Error", "El formato de la cédula no es válido (debe tener 8 dígitos)", Alert.AlertType.ERROR);
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
    
=======
            mostrarAlerta("Error", "No se pudo cargar la ventana de login", Alert.AlertType.ERROR);
        }
    }
    
>>>>>>> 3ea16f87b533c31ff796f367d2d8cc8f6d7e99d1
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
