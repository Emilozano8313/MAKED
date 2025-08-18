package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistroController {

    @FXML
    private TextField nombreField;
    
    @FXML
    private TextField apellidosField;
    
    @FXML
    private TextField matriculaField;
    
    @FXML
    private TextField curpField;
    
    @FXML
    private TextField correoField;
    
    @FXML
    private TextField telefonoField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button registrarButton;
    
    @FXML
    private Label volverLoginLabel;

    @FXML
    private void initialize() {
        // Configurar el evento para volver al login
        volverLoginLabel.setOnMouseClicked(this::volverAlLogin);
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            mostrarAlerta("Error de Contraseñas", 
                "Las contraseñas no coinciden.\n\nPor favor, asegúrese de que ambos campos de contraseña sean idénticos.", 
                Alert.AlertType.ERROR);
            return;
        }

        // Validar formato de correo
        if (!validarEmail(correoField.getText())) {
            mostrarAlerta("Formato de Correo Inválido", 
                "El formato del correo electrónico no es válido.\n\nEjemplo de formato válido: usuario@dominio.com", 
                Alert.AlertType.ERROR);
            return;
        }

        // Validar formato de CURP
        if (!validarCURP(curpField.getText())) {
            mostrarAlerta("Formato de CURP Inválido", 
                "El formato del CURP no es válido.\n\nEl CURP debe tener 18 caracteres alfanuméricos.\nEjemplo: ABCD123456HDFGHI01", 
                Alert.AlertType.ERROR);
            return;
        }

        // Validar formato de teléfono
        if (!validarTelefono(telefonoField.getText())) {
            mostrarAlerta("Formato de Teléfono Inválido", 
                "El formato del teléfono no es válido.\n\nDebe contener exactamente 10 dígitos numéricos.\nEjemplo: 5512345678", 
                Alert.AlertType.ERROR);
            return;
        }

        try {
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombreField.getText());
            nuevoUsuario.setApellidos(apellidosField.getText());
            nuevoUsuario.setMatricula(matriculaField.getText());
            nuevoUsuario.setCurp(curpField.getText());
            nuevoUsuario.setCorreo(correoField.getText());
            nuevoUsuario.setTelefono(telefonoField.getText());
            nuevoUsuario.setContrasena(passwordField.getText());
            nuevoUsuario.setRol("paciente"); // Por defecto se registra como paciente

            // Aquí se guardaría en la base de datos
            boolean registroExitoso = guardarUsuario(nuevoUsuario);
            
            if (registroExitoso) {
                mostrarAlerta("Registro Exitoso", 
                    "¡Usuario registrado correctamente!\n\n" +
                    "Nombre: " + nuevoUsuario.getNombre() + " " + nuevoUsuario.getApellidos() + "\n" +
                    "Correo: " + nuevoUsuario.getCorreo() + "\n\n" +
                    "Ahora puede iniciar sesión con sus credenciales.", 
                    Alert.AlertType.INFORMATION);
                volverAlLogin(event);
            } else {
                mostrarAlerta("Error de Registro", 
                    "No se pudo completar el registro del usuario.\n\n" +
                    "Por favor, inténtelo nuevamente o contacte al administrador del sistema.", 
                    Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error del Sistema", 
                "Ocurrió un error inesperado durante el registro:\n\n" + e.getMessage() + "\n\n" +
                "Por favor, verifique su conexión e inténtelo nuevamente.", 
                Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        StringBuilder camposFaltantes = new StringBuilder();
        boolean hayErrores = false;
        
        // Validar cada campo y acumular errores
        if (nombreField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Nombre\n");
            hayErrores = true;
        }
        if (apellidosField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Apellidos\n");
            hayErrores = true;
        }
        if (matriculaField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Matrícula o No. de empleado\n");
            hayErrores = true;
        }
        if (curpField.getText().trim().isEmpty()) {
            camposFaltantes.append("• CURP\n");
            hayErrores = true;
        }
        if (correoField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Correo electrónico\n");
            hayErrores = true;
        }
        if (telefonoField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Teléfono\n");
            hayErrores = true;
        }
        if (passwordField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Contraseña\n");
            hayErrores = true;
        }
        if (confirmPasswordField.getText().trim().isEmpty()) {
            camposFaltantes.append("• Confirmar contraseña\n");
            hayErrores = true;
        }
        
        // Si hay errores, mostrar alerta con todos los campos faltantes
        if (hayErrores) {
            String mensaje = "Por favor, complete los siguientes campos obligatorios:\n\n" + camposFaltantes.toString();
            mostrarAlerta("Campos Requeridos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }

    private boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean validarCURP(String curp) {
        // CURP debe tener 18 caracteres alfanuméricos
        return curp.matches("^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z][0-9]$");
    }

    private boolean validarTelefono(String telefono) {
        // Validar formato de teléfono mexicano (10 dígitos)
        return telefono.matches("^[0-9]{10}$");
    }

    private boolean guardarUsuario(Usuario usuario) {
        // Aquí se implementaría la lógica para guardar en la base de datos
        // Por ahora retornamos true para simular éxito
        System.out.println("Guardando usuario: " + usuario.getNombre() + " " + usuario.getApellidos());
        return true;
    }

    private void volverAlLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bienvenido a tu gestor de citas medicas");
            stage.setMaximized(true);
            stage.show();

            // Cerrar la ventana actual
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void volverAlLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
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
