package com.gestorcitasmedicas.utils;

import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para búsquedas avanzadas
 */
public class BusquedaAvanzada {
    
    /**
     * Busca pacientes por nombre, matrícula o teléfono
     */
    public static List<Paciente> buscarPacientes(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return Paciente.obtenerTodos();
        }
        
        String terminoLower = termino.toLowerCase().trim();
        
        return Paciente.obtenerTodos().stream()
                .filter(paciente -> 
                    paciente.getNombre().toLowerCase().contains(terminoLower) ||
                    paciente.getMatricula().toLowerCase().contains(terminoLower) ||
                    paciente.getTelefono().toLowerCase().contains(terminoLower) ||
                    paciente.getCurp().toLowerCase().contains(terminoLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca médicos por nombre, especialidad o cédula
     */
    public static List<Medico> buscarMedicos(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return Medico.obtenerTodos();
        }
        
        String terminoLower = termino.toLowerCase().trim();
        
        return Medico.obtenerTodos().stream()
                .filter(medico -> 
                    medico.getNombre().toLowerCase().contains(terminoLower) ||
                    medico.getEspecialidad().toLowerCase().contains(terminoLower) ||
                    medico.getCedula().toLowerCase().contains(terminoLower) ||
                    medico.getConsultorio().toLowerCase().contains(terminoLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca consultas por múltiples criterios
     */
    public static List<Consulta> buscarConsultas(String termino, String estado, LocalDate fechaDesde, LocalDate fechaHasta) {
        List<Consulta> todasLasConsultas = Consulta.obtenerTodas();
        
        return todasLasConsultas.stream()
                .filter(consulta -> {
                    // Filtro por término de búsqueda
                    if (termino != null && !termino.trim().isEmpty()) {
                        String terminoLower = termino.toLowerCase().trim();
                        
                        // Buscar en motivo
                        if (!consulta.getMotivo().toLowerCase().contains(terminoLower)) {
                            return false;
                        }
                        
                        // Buscar en diagnóstico si existe
                        if (consulta.getDiagnostico() != null && 
                            !consulta.getDiagnostico().toLowerCase().contains(terminoLower)) {
                            return false;
                        }
                        
                        // Buscar en tratamiento si existe
                        if (consulta.getTratamiento() != null && 
                            !consulta.getTratamiento().toLowerCase().contains(terminoLower)) {
                            return false;
                        }
                    }
                    
                    // Filtro por estado
                    if (estado != null && !estado.trim().isEmpty() && !"Todos".equals(estado)) {
                        if (!consulta.getEstado().equals(estado)) {
                            return false;
                        }
                    }
                    
                    // Filtro por rango de fechas
                    if (fechaDesde != null && consulta.getFecha().isBefore(fechaDesde)) {
                        return false;
                    }
                    
                    if (fechaHasta != null && consulta.getFecha().isAfter(fechaHasta)) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Busca consultas por paciente específico
     */
    public static List<Consulta> buscarConsultasPorPaciente(String nombrePaciente) {
        if (nombrePaciente == null || nombrePaciente.trim().isEmpty()) {
            return Consulta.obtenerTodas();
        }
        
        String nombreLower = nombrePaciente.toLowerCase().trim();
        
        return Consulta.obtenerTodas().stream()
                .filter(consulta -> {
                    Paciente paciente = obtenerPacientePorId(consulta.getIdPaciente());
                    return paciente != null && 
                           paciente.getNombre().toLowerCase().contains(nombreLower);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Busca consultas por médico específico
     */
    public static List<Consulta> buscarConsultasPorMedico(String nombreMedico) {
        if (nombreMedico == null || nombreMedico.trim().isEmpty()) {
            return Consulta.obtenerTodas();
        }
        
        String nombreLower = nombreMedico.toLowerCase().trim();
        
        return Consulta.obtenerTodas().stream()
                .filter(consulta -> {
                    Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
                    return medico != null && 
                           medico.getNombre().toLowerCase().contains(nombreLower);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Busca consultas por especialidad médica
     */
    public static List<Consulta> buscarConsultasPorEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            return Consulta.obtenerTodas();
        }
        
        String especialidadLower = especialidad.toLowerCase().trim();
        
        return Consulta.obtenerTodas().stream()
                .filter(consulta -> {
                    Medico medico = obtenerMedicoPorId(consulta.getIdMedico());
                    return medico != null && 
                           medico.getEspecialidad().toLowerCase().contains(especialidadLower);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Busca consultas con diagnóstico específico
     */
    public static List<Consulta> buscarConsultasPorDiagnostico(String diagnostico) {
        if (diagnostico == null || diagnostico.trim().isEmpty()) {
            return Consulta.obtenerTodas();
        }
        
        String diagnosticoLower = diagnostico.toLowerCase().trim();
        
        return Consulta.obtenerTodas().stream()
                .filter(consulta -> 
                    consulta.getDiagnostico() != null && 
                    consulta.getDiagnostico().toLowerCase().contains(diagnosticoLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene estadísticas de búsqueda
     */
    public static String obtenerEstadisticasBusqueda(List<Consulta> consultas) {
        if (consultas.isEmpty()) {
            return "No se encontraron resultados";
        }
        
        long completadas = consultas.stream().filter(c -> "completada".equals(c.getEstado())).count();
        long programadas = consultas.stream().filter(c -> "programada".equals(c.getEstado())).count();
        long canceladas = consultas.stream().filter(c -> "cancelada".equals(c.getEstado())).count();
        
        return String.format("Se encontraron %d consultas:\n" +
                           "• Completadas: %d\n" +
                           "• Programadas: %d\n" +
                           "• Canceladas: %d", 
                           consultas.size(), completadas, programadas, canceladas);
    }
    
    // Métodos auxiliares
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
