package com.gestorcitasmedicas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class AgendarCitaController {

    @FXML
    private void initialize() {
        // Método de inicialización vacío para pruebas
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
    
    // Método para cambiar a modo edición (llamado desde el panel principal)
    public void cargarCitaParaEditar(Cita cita) {
        // Implementación temporal
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modo Edición");
        alert.setHeaderText(null);
        alert.setContentText("Modo edición activado para: " + cita.getPaciente());
        alert.showAndWait();
    }
}
