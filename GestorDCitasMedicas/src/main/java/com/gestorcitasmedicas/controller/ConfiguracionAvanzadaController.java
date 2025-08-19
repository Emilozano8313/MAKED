package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.utils.BusquedaAvanzada;
import com.gestorcitasmedicas.utils.TemaManager;
import com.gestorcitasmedicas.utils.ErrorHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la pantalla de configuración avanzada
 */
public class ConfiguracionAvanzadaController {
    
    @FXML private TextField txtBuscarPacientes;
    @FXML private TextField txtBuscarMedicos;
    @FXML private TextField txtBuscarConsultas;
    @FXML private VBox vboxResultadosBusqueda;
    @FXML private TextArea txtResultadosBusqueda;
    @FXML private Label lblTemaActual;
    @FXML private Label lblTotalPacientes;
    @FXML private Label lblTotalMedicos;
    @FXML private Label lblTotalConsultas;
    @FXML private Label lblConsultasCompletadas;
    
    private Stage stage;
    private Scene scene;
    
    @FXML
    public void initialize() {
        // Cargar estadísticas iniciales
        actualizarEstadisticas();
        
        // Aplicar tema actual
        TemaManager.Tema temaActual = TemaManager.getTemaActual();
        lblTemaActual.setText("Tema actual: " + temaActual.toString());
        
        // Configurar listeners para búsqueda en tiempo real
        configurarBusquedaEnTiempoReal();
    }
    
    /**
     * Configura la búsqueda en tiempo real
     */
    private void configurarBusquedaEnTiempoReal() {
        txtBuscarPacientes.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() >= 2) {
                buscarPacientes();
            } else if (newValue == null || newValue.isEmpty()) {
                ocultarResultados();
            }
        });
        
        txtBuscarMedicos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() >= 2) {
                buscarMedicos();
            } else if (newValue == null || newValue.isEmpty()) {
                ocultarResultados();
            }
        });
        
        txtBuscarConsultas.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() >= 2) {
                buscarConsultas();
            } else if (newValue == null || newValue.isEmpty()) {
                ocultarResultados();
            }
        });
    }
    
    @FXML
    private void buscarPacientes() {
        String criterio = txtBuscarPacientes.getText().trim();
        if (criterio.isEmpty()) {
            ocultarResultados();
            return;
        }
        
        try {
            List<com.gestorcitasmedicas.model.Paciente> pacientes = BusquedaAvanzada.buscarPacientes(criterio);
            mostrarResultadosPacientes(pacientes);
        } catch (Exception e) {
            ErrorHandler.manejarExcepcion("búsqueda de pacientes", e);
        }
    }
    
    @FXML
    private void buscarMedicos() {
        String criterio = txtBuscarMedicos.getText().trim();
        if (criterio.isEmpty()) {
            ocultarResultados();
            return;
        }
        
        try {
            List<com.gestorcitasmedicas.model.Medico> medicos = BusquedaAvanzada.buscarMedicos(criterio);
            mostrarResultadosMedicos(medicos);
        } catch (Exception e) {
            ErrorHandler.manejarExcepcion("búsqueda de médicos", e);
        }
    }
    
    @FXML
    private void buscarConsultas() {
        String criterio = txtBuscarConsultas.getText().trim();
        if (criterio.isEmpty()) {
            ocultarResultados();
            return;
        }
        
        try {
            List<com.gestorcitasmedicas.model.Consulta> consultas = BusquedaAvanzada.buscarConsultas(criterio, null, null, null);
            mostrarResultadosConsultas(consultas);
        } catch (Exception e) {
            ErrorHandler.manejarExcepcion("búsqueda de consultas", e);
        }
    }
    
    /**
     * Muestra los resultados de búsqueda de pacientes
     */
    private void mostrarResultadosPacientes(List<com.gestorcitasmedicas.model.Paciente> pacientes) {
        if (pacientes.isEmpty()) {
            txtResultadosBusqueda.setText("No se encontraron pacientes que coincidan con el criterio de búsqueda.");
        } else {
            StringBuilder resultado = new StringBuilder();
            resultado.append("PACIENTES ENCONTRADOS (").append(pacientes.size()).append("):\n");
            resultado.append("=".repeat(50)).append("\n\n");
            
            for (com.gestorcitasmedicas.model.Paciente paciente : pacientes) {
                resultado.append("ID: ").append(paciente.getId()).append("\n");
                resultado.append("Nombre: ").append(paciente.getNombre()).append("\n");
                resultado.append("Matrícula: ").append(paciente.getMatricula()).append("\n");
                resultado.append("Teléfono: ").append(paciente.getTelefono()).append("\n");
                resultado.append("CURP: ").append(paciente.getCurp()).append("\n");
                resultado.append("-".repeat(30)).append("\n");
            }
            
            txtResultadosBusqueda.setText(resultado.toString());
        }
        vboxResultadosBusqueda.setVisible(true);
    }
    
    /**
     * Muestra los resultados de búsqueda de médicos
     */
    private void mostrarResultadosMedicos(List<com.gestorcitasmedicas.model.Medico> medicos) {
        if (medicos.isEmpty()) {
            txtResultadosBusqueda.setText("No se encontraron médicos que coincidan con el criterio de búsqueda.");
        } else {
            StringBuilder resultado = new StringBuilder();
            resultado.append("MÉDICOS ENCONTRADOS (").append(medicos.size()).append("):\n");
            resultado.append("=".repeat(50)).append("\n\n");
            
            for (com.gestorcitasmedicas.model.Medico medico : medicos) {
                resultado.append("ID: ").append(medico.getId()).append("\n");
                resultado.append("Nombre: ").append(medico.getNombre()).append("\n");
                resultado.append("Especialidad: ").append(medico.getEspecialidad()).append("\n");
                resultado.append("Cédula: ").append(medico.getCedula()).append("\n");
                resultado.append("Teléfono: ").append(medico.getTelefono()).append("\n");
                resultado.append("-".repeat(30)).append("\n");
            }
            
            txtResultadosBusqueda.setText(resultado.toString());
        }
        vboxResultadosBusqueda.setVisible(true);
    }
    
    /**
     * Muestra los resultados de búsqueda de consultas
     */
    private void mostrarResultadosConsultas(List<com.gestorcitasmedicas.model.Consulta> consultas) {
        if (consultas.isEmpty()) {
            txtResultadosBusqueda.setText("No se encontraron consultas que coincidan con el criterio de búsqueda.");
        } else {
            StringBuilder resultado = new StringBuilder();
            resultado.append("CONSULTAS ENCONTRADAS (").append(consultas.size()).append("):\n");
            resultado.append("=".repeat(50)).append("\n\n");
            
            for (com.gestorcitasmedicas.model.Consulta consulta : consultas) {
                resultado.append("ID: ").append(consulta.getId()).append("\n");
                resultado.append("Fecha: ").append(consulta.getFecha()).append("\n");
                resultado.append("Hora: ").append(consulta.getHora()).append("\n");
                resultado.append("Motivo: ").append(consulta.getMotivo()).append("\n");
                resultado.append("Estado: ").append(consulta.getEstado()).append("\n");
                if (consulta.getDiagnostico() != null && !consulta.getDiagnostico().isEmpty()) {
                    resultado.append("Diagnóstico: ").append(consulta.getDiagnostico()).append("\n");
                }
                resultado.append("-".repeat(30)).append("\n");
            }
            
            txtResultadosBusqueda.setText(resultado.toString());
        }
        vboxResultadosBusqueda.setVisible(true);
    }
    
    /**
     * Oculta los resultados de búsqueda
     */
    private void ocultarResultados() {
        vboxResultadosBusqueda.setVisible(false);
        txtResultadosBusqueda.clear();
    }
    
    @FXML
    private void aplicarTemaClaro() {
        aplicarTema(TemaManager.Tema.CLARO);
    }
    
    @FXML
    private void aplicarTemaOscuro() {
        aplicarTema(TemaManager.Tema.OSCURO);
    }
    
    @FXML
    private void aplicarTemaAzul() {
        aplicarTema(TemaManager.Tema.AZUL);
    }
    
    @FXML
    private void aplicarTemaVerde() {
        aplicarTema(TemaManager.Tema.VERDE);
    }
    
    /**
     * Aplica el tema seleccionado
     */
    private void aplicarTema(TemaManager.Tema tema) {
        try {
            if (stage != null && scene != null) {
                TemaManager.aplicarTema(scene, tema);
                lblTemaActual.setText("Tema actual: " + tema.toString());
                
                ErrorHandler.mostrarInformacion("Éxito", 
                    "Tema " + tema.toString().toLowerCase() + " aplicado correctamente");
            }
        } catch (Exception e) {
            ErrorHandler.manejarExcepcion("aplicación de tema", e);
        }
    }
    
    @FXML
    private void actualizarEstadisticas() {
        try {
            List<com.gestorcitasmedicas.model.Paciente> pacientes = com.gestorcitasmedicas.model.Paciente.obtenerTodos();
            List<com.gestorcitasmedicas.model.Medico> medicos = com.gestorcitasmedicas.model.Medico.obtenerTodos();
            List<com.gestorcitasmedicas.model.Consulta> consultas = com.gestorcitasmedicas.model.Consulta.obtenerTodas();
            
            long consultasCompletadas = consultas.stream()
                    .filter(c -> "completada".equals(c.getEstado()))
                    .count();
            
            lblTotalPacientes.setText(String.valueOf(pacientes.size()));
            lblTotalMedicos.setText(String.valueOf(medicos.size()));
            lblTotalConsultas.setText(String.valueOf(consultas.size()));
            lblConsultasCompletadas.setText(String.valueOf(consultasCompletadas));
            
        } catch (Exception e) {
            ErrorHandler.manejarExcepcion("actualización de estadísticas", e);
        }
    }
    
    @FXML
    private void limpiarBusqueda() {
        txtBuscarPacientes.clear();
        txtBuscarMedicos.clear();
        txtBuscarConsultas.clear();
        ocultarResultados();
        actualizarEstadisticas();
    }
    
    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/MenuPrincipal.fxml"));
            Parent root = loader.load();
            
            if (stage == null) {
                stage = (Stage) txtBuscarPacientes.getScene().getWindow();
            }
            
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menú Principal - Gestor de Citas Médicas");
            stage.show();
            
        } catch (Exception e) {
            ErrorHandler.manejarExcepcion("navegación al menú principal", e);
        }
    }
    
    /**
     * Establece la referencia al stage y scene
     */
    public void setStageAndScene(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }
}
