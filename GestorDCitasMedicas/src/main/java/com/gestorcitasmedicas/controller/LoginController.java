package com.gestorcitasmedicas.controller;
import com.gestorcitasmedicas.model.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private Label olvidoContra;
    @FXML
    private Label crearCuenta;
    @FXML
    private TextField correoIngresar;
    @FXML
    private TextField contraIngresar;
    @FXML
    private Button loginButton;

    @FXML
    private void iniciarSesion(ActionEvent event) {
//Accion para el inicio de sesion
        String correo = correoIngresar.getText();
        String contra = contraIngresar.getText();

        Usuario user = new Usuario();
        user.setNombre(correo);
        user.setContrasena(contra);

        if(user.usuarioValido(correo, contra)){
            System.out.println("Inicio de sesión exitoso");
        }else{
            System.out.println("Usuario o contraseña incorrectos");
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

}
