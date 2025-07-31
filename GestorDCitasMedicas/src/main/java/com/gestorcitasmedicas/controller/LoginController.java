package com.gestorcitasmedicas.controller;
import com.gestorcitasmedicas.model.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label olvidoContra;
    @FXML
    private Label crearCuenta;
    @FXML
    private TextField correoIngresar;
    @FXML
    private PasswordField contraIngresar;
    @FXML
    private Button loginButton;

    @FXML
    private void iniciarSesion(ActionEvent event) {
//Accion para el inicio de sesion
        String correo = correoIngresar.getText();
        String contra = contraIngresar.getText();
        System.out.println("Correo ingresado: " + correo);
        System.out.println("Contraseña ingresada: " + contra);

        Usuario usuario = Usuario.autenticar(correo, contra);
        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso como " + usuario.getRol());
            switch (usuario.getRol()) {
                case "doctor":
                    //aqui se van a cargar la siguiente ventana dependiendo a que rol se abra
                    System.out.println("Bienvenido doctor");
                    //abrirVentana("debes poner la ruta del siguiente fxml", event);
                    break;
                case "paciente":
                    System.out.println("Bienvenido paciente");
                    //abrirVentana("vistaPaciente.fxml", event);
                    break;
                case "recepcionista":
                    System.out.println("Bienvenido patron");
                    //abrirVentana("vistaRecepcionista.fxml", event);
                    break;
                default:
                    System.out.println("Rol no reconocido.");
            }
        } else {
            System.out.println("Correo o contraseña incorrectos");
        }

        }

    @FXML
    private void initialize(){
        //Accion para el caso de olvido contrasena
        olvidoContra.setOnMouseClicked(event -> {

        });

        //Accion para el caso de crear cuenta
        crearCuenta.setOnMouseClicked(event -> {

        });
    }

    private void abrirVentana(String rutaFXML, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cierra la ventana actual
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
