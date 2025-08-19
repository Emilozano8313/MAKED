package com.gestorcitasmedicas.utils;

import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Clase utilitaria para exportar reportes a PDF
 */
public class PDFExporter {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    
    /**
     * Exporta un reporte de citas a PDF
     */
    public static boolean exportarReporteCitas(List<Consulta> consultas, String rutaArchivo, 
                                             LocalDate fechaDesde, LocalDate fechaHasta) {
        try {
            // Crear documento PDF simple usando texto plano
            StringBuilder contenido = new StringBuilder();
            
            // Encabezado
            contenido.append("REPORTE DE CITAS MÉDICAS\n");
            contenido.append("========================\n\n");
            contenido.append("Período: ").append(fechaDesde.format(DATE_FORMAT))
                    .append(" - ").append(fechaHasta.format(DATE_FORMAT)).append("\n");
            contenido.append("Fecha de generación: ").append(LocalDate.now().format(DATE_FORMAT)).append("\n");
            contenido.append("Total de citas: ").append(consultas.size()).append("\n\n");
            
            // Tabla de citas
            contenido.append("DETALLE DE CITAS\n");
            contenido.append("================\n");
            contenido.append(String.format("%-5s %-15s %-15s %-12s %-8s %-20s %-12s\n", 
                "ID", "PACIENTE", "MÉDICO", "FECHA", "HORA", "MOTIVO", "ESTADO"));
            contenido.append("--------------------------------------------------------------------------------\n");
            
            for (Consulta consulta : consultas) {
                Paciente paciente = obtenerPacientePorId(consulta.getIdPaciente());
                Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
                
                String nombrePaciente = paciente != null ? paciente.getNombre() : "N/A";
                String nombreMedico = medico != null ? medico.getNombre() : "N/A";
                
                contenido.append(String.format("%-5d %-15s %-15s %-12s %-8s %-20s %-12s\n",
                    consulta.getId(),
                    truncarTexto(nombrePaciente, 15),
                    truncarTexto(nombreMedico, 15),
                    consulta.getFecha().format(DATE_FORMAT),
                    consulta.getHora().format(TIME_FORMAT),
                    truncarTexto(consulta.getMotivo(), 20),
                    consulta.getEstado()
                ));
            }
            
            // Estadísticas
            contenido.append("\n\nESTADÍSTICAS\n");
            contenido.append("============\n");
            Map<String, Long> estadisticas = consultas.stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                        Consulta::getEstado, 
                        java.util.stream.Collectors.counting()
                    ));
            
            for (Map.Entry<String, Long> entry : estadisticas.entrySet()) {
                contenido.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            
            // Guardar archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                fos.write(contenido.toString().getBytes("UTF-8"));
            }
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al exportar PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Exporta el historial médico de un paciente a PDF
     */
    public static boolean exportarHistorialPaciente(Paciente paciente, List<Consulta> consultas, String rutaArchivo) {
        try {
            StringBuilder contenido = new StringBuilder();
            
            // Encabezado
            contenido.append("HISTORIAL MÉDICO\n");
            contenido.append("================\n\n");
            contenido.append("PACIENTE\n");
            contenido.append("--------\n");
            contenido.append("Nombre: ").append(paciente.getNombre()).append("\n");
            contenido.append("Matrícula: ").append(paciente.getMatricula()).append("\n");
            contenido.append("Teléfono: ").append(paciente.getTelefono()).append("\n");
            contenido.append("CURP: ").append(paciente.getCurp()).append("\n\n");
            
            // Consultas
            contenido.append("CONSULTAS REALIZADAS\n");
            contenido.append("====================\n");
            contenido.append("Total de consultas: ").append(consultas.size()).append("\n\n");
            
            for (int i = 0; i < consultas.size(); i++) {
                Consulta consulta = consultas.get(i);
                Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
                
                contenido.append("CONSULTA #").append(i + 1).append("\n");
                contenido.append("----------\n");
                contenido.append("Fecha: ").append(consulta.getFecha().format(DATE_FORMAT)).append("\n");
                contenido.append("Hora: ").append(consulta.getHora().format(TIME_FORMAT)).append("\n");
                contenido.append("Médico: ").append(medico != null ? medico.getNombre() : "N/A").append("\n");
                contenido.append("Especialidad: ").append(medico != null ? medico.getEspecialidad() : "N/A").append("\n");
                contenido.append("Motivo: ").append(consulta.getMotivo()).append("\n");
                contenido.append("Estado: ").append(consulta.getEstado()).append("\n");
                
                if (consulta.getDiagnostico() != null && !consulta.getDiagnostico().isEmpty()) {
                    contenido.append("Diagnóstico: ").append(consulta.getDiagnostico()).append("\n");
                }
                
                if (consulta.getTratamiento() != null && !consulta.getTratamiento().isEmpty()) {
                    contenido.append("Tratamiento: ").append(consulta.getTratamiento()).append("\n");
                }
                
                if (consulta.getObservaciones() != null && !consulta.getObservaciones().isEmpty()) {
                    contenido.append("Observaciones: ").append(consulta.getObservaciones()).append("\n");
                }
                
                contenido.append("\n");
            }
            
            // Guardar archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                fos.write(contenido.toString().getBytes("UTF-8"));
            }
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al exportar historial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Exporta estadísticas generales a PDF
     */
    public static boolean exportarEstadisticasGenerales(String rutaArchivo) {
        try {
            List<Consulta> todasLasConsultas = Consulta.obtenerTodas();
            List<Paciente> todosLosPacientes = Paciente.obtenerTodos();
            List<Medico> todosLosMedicos = Medico.obtenerTodos();
            
            StringBuilder contenido = new StringBuilder();
            
            // Encabezado
            contenido.append("ESTADÍSTICAS GENERALES DEL SISTEMA\n");
            contenido.append("==================================\n\n");
            contenido.append("Fecha de generación: ").append(LocalDate.now().format(DATE_FORMAT)).append("\n\n");
            
            // Resumen general
            contenido.append("RESUMEN GENERAL\n");
            contenido.append("---------------\n");
            contenido.append("Total de médicos: ").append(todosLosMedicos.size()).append("\n");
            contenido.append("Total de pacientes: ").append(todosLosPacientes.size()).append("\n");
            contenido.append("Total de citas: ").append(todasLasConsultas.size()).append("\n\n");
            
            // Estadísticas de citas
            contenido.append("ESTADÍSTICAS DE CITAS\n");
            contenido.append("----------------------\n");
            Map<String, Long> estadisticas = todasLasConsultas.stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                        Consulta::getEstado, 
                        java.util.stream.Collectors.counting()
                    ));
            
            for (Map.Entry<String, Long> entry : estadisticas.entrySet()) {
                double porcentaje = todasLasConsultas.size() > 0 ? 
                    (double) entry.getValue() / todasLasConsultas.size() * 100 : 0;
                contenido.append(entry.getKey()).append(": ").append(entry.getValue())
                        .append(" (").append(String.format("%.1f", porcentaje)).append("%)\n");
            }
            
            // Lista de médicos
            contenido.append("\nMÉDICOS REGISTRADOS\n");
            contenido.append("-------------------\n");
            for (Medico medico : todosLosMedicos) {
                contenido.append("- ").append(medico.getNombre())
                        .append(" (").append(medico.getEspecialidad()).append(")\n");
            }
            
            // Guardar archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                fos.write(contenido.toString().getBytes("UTF-8"));
            }
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al exportar estadísticas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Métodos auxiliares
    private static String truncarTexto(String texto, int longitud) {
        if (texto == null) return "N/A";
        return texto.length() > longitud ? texto.substring(0, longitud - 3) + "..." : texto;
    }
    
    private static Paciente obtenerPacientePorId(int id) {
        return Paciente.obtenerTodos().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private static Medico obtenerMedicoPorId(int id) {
        return Medico.obtenerTodos().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
