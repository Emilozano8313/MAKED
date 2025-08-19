package com.gestorcitasmedicas.controller;

import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;
import com.gestorcitasmedicas.utils.PDFExporter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesEstadisticasController {

    @FXML private DatePicker datePickerDesde;
    @FXML private DatePicker datePickerHasta;
    @FXML private Button btnGenerarReporte;
    @FXML private Button btnExportarPDF;
    @FXML private Button btnRegresar;
    
    @FXML private Label lblTotalCitas;
    @FXML private Label lblCitasCompletadas;
    @FXML private Label lblCitasCanceladas;
    @FXML private Label lblCitasPendientes;
    @FXML private Label lblTotalPacientes;
    @FXML private Label lblTotalMedicos;
    @FXML private Label lblTasaAsistencia;
    @FXML private Label lblPromedioDiario;
    
    @FXML private TableView<Consulta> tablaReporte;
    @FXML private TableColumn<Consulta, Integer> colId;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private TableColumn<Consulta, String> colEspecialidad;
    @FXML private TableColumn<Consulta, String> colMotivo;
    @FXML private TableColumn<Consulta, String> colEstado;
    
    @FXML private Label lblContadorReporte;
    
    private ObservableList<Consulta> consultasFiltradas = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        System.out.println("ReportesEstadisticasController inicializando...");
        
        configurarTabla();
        configurarDatePickers();
        cargarEstadisticasGenerales();
        
        System.out.println("ReportesEstadisticasController inicializado correctamente");
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFecha.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            return new SimpleStringProperty(consulta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        colHora.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            return new SimpleStringProperty(consulta.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        });
        colPaciente.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Paciente paciente = obtenerPacientePorId(consulta.getIdPaciente());
            return new SimpleStringProperty(paciente != null ? paciente.getNombre() : "N/A");
        });
        colMedico.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
            return new SimpleStringProperty(medico != null ? medico.getNombre() : "N/A");
        });
        colEspecialidad.setCellValueFactory(cellData -> {
            Consulta consulta = cellData.getValue();
            Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
            return new SimpleStringProperty(medico != null ? medico.getEspecialidad() : "N/A");
        });
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        // Configurar tabla
        tablaReporte.setItems(consultasFiltradas);
    }
    
    private void configurarDatePickers() {
        // Configurar DatePickers para permitir todas las fechas
        datePickerDesde.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && empty) {
                    setDisable(false);
                }
            }
        });
        
        datePickerHasta.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && empty) {
                    setDisable(false);
                }
            }
        });
    }
    
    private void cargarEstadisticasGenerales() {
        try {
            // Usar métodos optimizados
            java.util.Map<String, Long> estadisticas = Consulta.obtenerEstadisticas();
            List<Paciente> todosLosPacientes = Paciente.obtenerTodos();
            List<Medico> todosLosMedicos = Medico.obtenerTodos();
            
            // Calcular estadísticas usando el mapa optimizado
            long totalCitas = estadisticas.values().stream().mapToLong(Long::longValue).sum();
            long citasCompletadas = estadisticas.getOrDefault("completada", 0L);
            long citasCanceladas = estadisticas.getOrDefault("cancelada", 0L);
            long citasPendientes = estadisticas.getOrDefault("programada", 0L);
            
            double tasaAsistencia = totalCitas > 0 ? (double) citasCompletadas / totalCitas * 100 : 0;
            
            // Actualizar labels
            lblTotalCitas.setText(String.valueOf(totalCitas));
            lblCitasCompletadas.setText(String.valueOf(citasCompletadas));
            lblCitasCanceladas.setText(String.valueOf(citasCanceladas));
            lblCitasPendientes.setText(String.valueOf(citasPendientes));
            lblTotalPacientes.setText(String.valueOf(todosLosPacientes.size()));
            lblTotalMedicos.setText(String.valueOf(todosLosMedicos.size()));
            lblTasaAsistencia.setText(String.format("%.1f%%", tasaAsistencia));
            
        } catch (Exception e) {
            System.err.println("Error al cargar estadísticas generales: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void generarReporte() {
        if (validarFechas()) {
            try {
                LocalDate fechaDesde = datePickerDesde.getValue();
                LocalDate fechaHasta = datePickerHasta.getValue();
                
                // Usar método optimizado para obtener consultas por rango de fechas
                List<Consulta> consultasEnRango = Consulta.obtenerPorRangoFechas(fechaDesde, fechaHasta);
                
                // Actualizar tabla
                consultasFiltradas.clear();
                consultasFiltradas.addAll(consultasEnRango);
                
                // Calcular estadísticas del período
                calcularEstadisticasPeriodo(consultasEnRango, fechaDesde, fechaHasta);
                
                // Actualizar contador
                lblContadorReporte.setText("(" + consultasEnRango.size() + " citas en el período)");
                
                mostrarAlerta("Éxito", 
                    "Reporte generado correctamente\n\n" +
                    "Período: " + fechaDesde.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                    " - " + fechaHasta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                    "Total de citas: " + consultasEnRango.size(), 
                    Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                System.err.println("Error al generar reporte: " + e.getMessage());
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo generar el reporte", Alert.AlertType.ERROR);
            }
        }
    }
    
    private void calcularEstadisticasPeriodo(List<Consulta> consultas, LocalDate fechaDesde, LocalDate fechaHasta) {
        int totalCitas = consultas.size();
        int citasCompletadas = (int) consultas.stream()
                .filter(c -> "completada".equals(c.getEstado()))
                .count();
        int citasCanceladas = (int) consultas.stream()
                .filter(c -> "cancelada".equals(c.getEstado()))
                .count();
        int citasPendientes = (int) consultas.stream()
                .filter(c -> "programada".equals(c.getEstado()))
                .count();
        
        double tasaAsistencia = totalCitas > 0 ? (double) citasCompletadas / totalCitas * 100 : 0;
        
        // Calcular promedio diario
        long diasPeriodo = ChronoUnit.DAYS.between(fechaDesde, fechaHasta) + 1;
        double promedioDiario = diasPeriodo > 0 ? (double) totalCitas / diasPeriodo : 0;
        
        // Actualizar labels con estadísticas del período
        lblTotalCitas.setText(String.valueOf(totalCitas));
        lblCitasCompletadas.setText(String.valueOf(citasCompletadas));
        lblCitasCanceladas.setText(String.valueOf(citasCanceladas));
        lblCitasPendientes.setText(String.valueOf(citasPendientes));
        lblTasaAsistencia.setText(String.format("%.1f%%", tasaAsistencia));
        lblPromedioDiario.setText(String.format("%.1f", promedioDiario));
    }
    
    private boolean validarFechas() {
        if (datePickerDesde.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha de inicio", Alert.AlertType.ERROR);
            datePickerDesde.requestFocus();
            return false;
        }
        
        if (datePickerHasta.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha de fin", Alert.AlertType.ERROR);
            datePickerHasta.requestFocus();
            return false;
        }
        
        if (datePickerDesde.getValue().isAfter(datePickerHasta.getValue())) {
            mostrarAlerta("Error", "La fecha de inicio no puede ser posterior a la fecha de fin", Alert.AlertType.ERROR);
            datePickerDesde.requestFocus();
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void exportarPDF() {
        if (consultasFiltradas.isEmpty()) {
            mostrarAlerta("Error", "No hay datos para exportar. Primero genere un reporte.", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            // Crear nombre de archivo con timestamp
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = "reporte_citas_" + timestamp + ".txt";
            
            // Obtener fechas del reporte actual
            LocalDate fechaDesde = datePickerDesde.getValue();
            LocalDate fechaHasta = datePickerHasta.getValue();
            
            if (fechaDesde == null || fechaHasta == null) {
                // Si no hay fechas específicas, usar estadísticas generales
                boolean exportado = PDFExporter.exportarEstadisticasGenerales(nombreArchivo);
                if (exportado) {
                    mostrarAlerta("Éxito", 
                        "Estadísticas generales exportadas correctamente\n\n" +
                        "Archivo: " + nombreArchivo + "\n" +
                        "Ubicación: " + System.getProperty("user.dir"),
                        Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No se pudo exportar el archivo", Alert.AlertType.ERROR);
                }
            } else {
                // Exportar reporte con fechas específicas
                boolean exportado = PDFExporter.exportarReporteCitas(
                    consultasFiltradas, nombreArchivo, fechaDesde, fechaHasta);
                
                if (exportado) {
                    mostrarAlerta("Éxito", 
                        "Reporte exportado correctamente\n\n" +
                        "Archivo: " + nombreArchivo + "\n" +
                        "Período: " + fechaDesde.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        " - " + fechaHasta.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                        "Ubicación: " + System.getProperty("user.dir"),
                        Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No se pudo exportar el archivo", Alert.AlertType.ERROR);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al exportar PDF: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error durante la exportación", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestorcitasmedicas/AgendaCitas.fxml"));
            Parent agendaCitasRoot = loader.load();
            
            Scene nuevaEscena = new Scene(agendaCitasRoot);
            Stage currentStage = (Stage) btnRegresar.getScene().getWindow();
            currentStage.setScene(nuevaEscena);
            currentStage.setTitle("Mi Agenda de Citas - Doctor");
            currentStage.setMaximized(true);
            currentStage.show();
            currentStage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la agenda de citas", Alert.AlertType.ERROR);
        }
    }
    
    private Paciente obtenerPacientePorId(int idPaciente) {
        List<Paciente> pacientes = Paciente.obtenerTodos();
        return pacientes.stream()
                .filter(p -> p.getId() == idPaciente)
                .findFirst()
                .orElse(null);
    }
    
    private Medico obtenerMedicoPorId(int idMedico) {
        List<Medico> medicos = Medico.obtenerTodos();
        return medicos.stream()
                .filter(m -> m.getId() == idMedico)
                .findFirst()
                .orElse(null);
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
