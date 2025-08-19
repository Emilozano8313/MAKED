package com.gestorcitasmedicas.utils;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria para manejo centralizado de errores
 */
public class ErrorHandler {
    
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Muestra una alerta de error con formato consistente
     */
    public static void mostrarError(String titulo, String mensaje) {
        mostrarAlerta(titulo, mensaje, Alert.AlertType.ERROR);
    }
    
    /**
     * Muestra una alerta de información
     */
    public static void mostrarInformacion(String titulo, String mensaje) {
        mostrarAlerta(titulo, mensaje, Alert.AlertType.INFORMATION);
    }
    
    /**
     * Muestra una alerta de advertencia
     */
    public static void mostrarAdvertencia(String titulo, String mensaje) {
        mostrarAlerta(titulo, mensaje, Alert.AlertType.WARNING);
    }
    
    /**
     * Muestra una alerta de confirmación
     */
    public static boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        return alert.showAndWait()
                .filter(response -> response == javafx.scene.control.ButtonType.OK)
                .isPresent();
    }
    
    /**
     * Maneja excepciones y las registra
     */
    public static void manejarExcepcion(String contexto, Exception e) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String mensaje = String.format("[%s] Error en %s: %s", timestamp, contexto, e.getMessage());
        
        System.err.println(mensaje);
        e.printStackTrace();
        
        // Mostrar alerta al usuario
        mostrarError("Error del Sistema", 
            "Ha ocurrido un error inesperado en " + contexto + ".\n\n" +
            "Detalles: " + e.getMessage());
    }
    
    /**
     * Valida que un texto no esté vacío
     */
    public static boolean validarTextoNoVacio(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            mostrarError("Campo requerido", "El campo '" + nombreCampo + "' es obligatorio");
            return false;
        }
        return true;
    }
    
    /**
     * Valida la longitud de un texto
     */
    public static boolean validarLongitudTexto(String texto, String nombreCampo, int minimo, int maximo) {
        if (texto == null) texto = "";
        
        if (texto.length() < minimo) {
            mostrarError("Texto muy corto", 
                "El campo '" + nombreCampo + "' debe tener al menos " + minimo + " caracteres");
            return false;
        }
        
        if (texto.length() > maximo) {
            mostrarError("Texto muy largo", 
                "El campo '" + nombreCampo + "' no puede exceder " + maximo + " caracteres");
            return false;
        }
        
        return true;
    }
    
    /**
     * Método privado para mostrar alertas con formato consistente
     */
    private static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        // Establecer tamaño mínimo para alertas largas
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(400);
        
        alert.showAndWait();
    }
    
    /**
     * Convierte una excepción a string para logging
     */
    public static String excepcionAString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
