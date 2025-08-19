package com.gestorcitasmedicas.utils;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * Clase para gestionar temas personalizables de la interfaz
 */
public class TemaManager {
    
    public enum Tema {
        CLARO("Claro"),
        OSCURO("Oscuro"),
        AZUL("Azul Médico"),
        VERDE("Verde Naturaleza");
        
        private final String nombre;
        
        Tema(String nombre) {
            this.nombre = nombre;
        }
        
        public String getNombre() {
            return nombre;
        }
    }
    
    private static Tema temaActual = Tema.CLARO;
    
    /**
     * Aplica un tema a la escena
     */
    public static void aplicarTema(Scene scene, Tema tema) {
        temaActual = tema;
        
        switch (tema) {
            case CLARO:
                aplicarTemaClaro(scene);
                break;
            case OSCURO:
                aplicarTemaOscuro(scene);
                break;
            case AZUL:
                aplicarTemaAzul(scene);
                break;
            case VERDE:
                aplicarTemaVerde(scene);
                break;
        }
    }
    
    /**
     * Obtiene el tema actual
     */
    public static Tema getTemaActual() {
        return temaActual;
    }
    
    /**
     * Aplica el tema claro
     */
    private static void aplicarTemaClaro(Scene scene) {
        String css = """
            .root {
                -fx-background-color: #F5F7FA;
                -fx-font-family: 'Segoe UI', Arial, sans-serif;
            }
            
            .header {
                -fx-background-color: #FFFFFF;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);
            }
            
            .content-panel {
                -fx-background-color: #FFFFFF;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 1);
            }
            
            .info-panel {
                -fx-background-color: #E8F4FD;
                -fx-background-radius: 10;
            }
            
            .button-primary {
                -fx-background-color: #28A745;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-secondary {
                -fx-background-color: #6C757D;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-danger {
                -fx-background-color: #DC3545;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .label-title {
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-text-fill: #2C5AA0;
            }
            
            .label-subtitle {
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-text-fill: #3B6F89;
            }
            
            .text-field {
                -fx-background-color: #FFFFFF;
                -fx-border-color: #D1D5DB;
                -fx-border-radius: 5;
                -fx-background-radius: 5;
                -fx-padding: 8;
            }
            
            .table-view {
                -fx-background-color: #FFFFFF;
                -fx-border-color: #D1D5DB;
                -fx-border-radius: 5;
            }
            
            .table-view .column-header {
                -fx-background-color: #F8F9FA;
                -fx-border-color: #D1D5DB;
            }
            """;
        
        scene.getStylesheets().clear();
        scene.getStylesheets().add("data:text/css;base64," + java.util.Base64.getEncoder().encodeToString(css.getBytes()));
    }
    
    /**
     * Aplica el tema oscuro
     */
    private static void aplicarTemaOscuro(Scene scene) {
        String css = """
            .root {
                -fx-background-color: #1A1A1A;
                -fx-font-family: 'Segoe UI', Arial, sans-serif;
            }
            
            .header {
                -fx-background-color: #2D2D2D;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);
            }
            
            .content-panel {
                -fx-background-color: #2D2D2D;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 1);
            }
            
            .info-panel {
                -fx-background-color: #3A3A3A;
                -fx-background-radius: 10;
            }
            
            .button-primary {
                -fx-background-color: #007ACC;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-secondary {
                -fx-background-color: #555555;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-danger {
                -fx-background-color: #D13438;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .label-title {
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-text-fill: #FFFFFF;
            }
            
            .label-subtitle {
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-text-fill: #CCCCCC;
            }
            
            .text-field {
                -fx-background-color: #3A3A3A;
                -fx-border-color: #555555;
                -fx-border-radius: 5;
                -fx-background-radius: 5;
                -fx-padding: 8;
                -fx-text-fill: #FFFFFF;
            }
            
            .table-view {
                -fx-background-color: #2D2D2D;
                -fx-border-color: #555555;
                -fx-border-radius: 5;
            }
            
            .table-view .column-header {
                -fx-background-color: #3A3A3A;
                -fx-border-color: #555555;
            }
            """;
        
        scene.getStylesheets().clear();
        scene.getStylesheets().add("data:text/css;base64," + java.util.Base64.getEncoder().encodeToString(css.getBytes()));
    }
    
    /**
     * Aplica el tema azul médico
     */
    private static void aplicarTemaAzul(Scene scene) {
        String css = """
            .root {
                -fx-background-color: #E3F2FD;
                -fx-font-family: 'Segoe UI', Arial, sans-serif;
            }
            
            .header {
                -fx-background-color: #1976D2;
                -fx-effect: dropshadow(gaussian, rgba(25,118,210,0.3), 10, 0, 0, 2);
            }
            
            .content-panel {
                -fx-background-color: #FFFFFF;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(25,118,210,0.1), 5, 0, 0, 1);
            }
            
            .info-panel {
                -fx-background-color: #E8F4FD;
                -fx-background-radius: 10;
            }
            
            .button-primary {
                -fx-background-color: #1976D2;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-secondary {
                -fx-background-color: #5C6BC0;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-danger {
                -fx-background-color: #F44336;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .label-title {
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-text-fill: #1565C0;
            }
            
            .label-subtitle {
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-text-fill: #1976D2;
            }
            
            .text-field {
                -fx-background-color: #FFFFFF;
                -fx-border-color: #BBDEFB;
                -fx-border-radius: 5;
                -fx-background-radius: 5;
                -fx-padding: 8;
            }
            
            .table-view {
                -fx-background-color: #FFFFFF;
                -fx-border-color: #BBDEFB;
                -fx-border-radius: 5;
            }
            
            .table-view .column-header {
                -fx-background-color: #E3F2FD;
                -fx-border-color: #BBDEFB;
            }
            """;
        
        scene.getStylesheets().clear();
        scene.getStylesheets().add("data:text/css;base64," + java.util.Base64.getEncoder().encodeToString(css.getBytes()));
    }
    
    /**
     * Aplica el tema verde naturaleza
     */
    private static void aplicarTemaVerde(Scene scene) {
        String css = """
            .root {
                -fx-background-color: #E8F5E8;
                -fx-font-family: 'Segoe UI', Arial, sans-serif;
            }
            
            .header {
                -fx-background-color: #4CAF50;
                -fx-effect: dropshadow(gaussian, rgba(76,175,80,0.3), 10, 0, 0, 2);
            }
            
            .content-panel {
                -fx-background-color: #FFFFFF;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(76,175,80,0.1), 5, 0, 0, 1);
            }
            
            .info-panel {
                -fx-background-color: #E8F5E8;
                -fx-background-radius: 10;
            }
            
            .button-primary {
                -fx-background-color: #4CAF50;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-secondary {
                -fx-background-color: #66BB6A;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .button-danger {
                -fx-background-color: #EF5350;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
            }
            
            .label-title {
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-text-fill: #2E7D32;
            }
            
            .label-subtitle {
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-text-fill: #388E3C;
            }
            
            .text-field {
                -fx-background-color: #FFFFFF;
                -fx-border-color: #C8E6C9;
                -fx-border-radius: 5;
                -fx-background-radius: 5;
                -fx-padding: 8;
            }
            
            .table-view {
                -fx-background-color: #FFFFFF;
                -fx-border-color: #C8E6C9;
                -fx-border-radius: 5;
            }
            
            .table-view .column-header {
                -fx-background-color: #E8F5E8;
                -fx-border-color: #C8E6C9;
            }
            """;
        
        scene.getStylesheets().clear();
        scene.getStylesheets().add("data:text/css;base64," + java.util.Base64.getEncoder().encodeToString(css.getBytes()));
    }
    
    /**
     * Aplica estilos específicos a componentes
     */
    public static void aplicarEstilosComponentes(Scene scene) {
        // Aplicar clases CSS a componentes específicos
        scene.lookupAll(".button").forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                String text = button.getText();
                
                if (text.contains("Completar") || text.contains("Guardar") || text.contains("Generar")) {
                    button.getStyleClass().add("button-primary");
                } else if (text.contains("Cancelar") || text.contains("Eliminar")) {
                    button.getStyleClass().add("button-danger");
                } else {
                    button.getStyleClass().add("button-secondary");
                }
            }
        });
        
        scene.lookupAll(".label").forEach(node -> {
            if (node instanceof Label) {
                Label label = (Label) node;
                String text = label.getText();
                
                if (text.contains("Reporte") || text.contains("Estadísticas") || text.contains("Historial")) {
                    label.getStyleClass().add("label-title");
                } else if (text.contains("Información") || text.contains("Detalles")) {
                    label.getStyleClass().add("label-subtitle");
                }
            }
        });
        
        scene.lookupAll(".text-field").forEach(node -> {
            if (node instanceof TextField) {
                node.getStyleClass().add("text-field");
            }
        });
    }
}
