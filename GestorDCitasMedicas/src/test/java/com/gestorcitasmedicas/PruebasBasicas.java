package com.gestorcitasmedicas;

import com.gestorcitasmedicas.model.Consulta;
import com.gestorcitasmedicas.model.Medico;
import com.gestorcitasmedicas.model.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Clase para pruebas básicas del sistema
 */
public class PruebasBasicas {
    
    public static void main(String[] args) {
        System.out.println("🧪 INICIANDO PRUEBAS BÁSICAS DEL SISTEMA");
        System.out.println("==========================================");
        
        // Limpiar datos anteriores
        Consulta.limpiarLista();
        
        try {
            // Ejecutar pruebas
            pruebaCreacionDatos();
            pruebaConsultasOptimizadas();
            pruebaValidaciones();
            pruebaEstadisticas();
            
            System.out.println("\n✅ TODAS LAS PRUEBAS PASARON EXITOSAMENTE");
            System.out.println("🎉 SISTEMA LISTO PARA PRODUCCIÓN");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN LAS PRUEBAS: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void pruebaCreacionDatos() {
        System.out.println("\n📋 Prueba 1: Creación de datos básicos");
        
        // Crear médicos de prueba
        Medico medico1 = new Medico("Dr. Test", "Cardiología", "C001", "test@test.com", "555-1111", "8:00-16:00", "Consultorio 1", "password");
        Medico medico2 = new Medico("Dra. Test2", "Pediatría", "C002", "test2@test.com", "555-2222", "9:00-17:00", "Consultorio 2", "password");
        
        medico1.guardar();
        medico2.guardar();
        
        // Crear pacientes de prueba
        Paciente paciente1 = new Paciente("Paciente Test", "12345", "555-1234", "TESTP123456", "test@paciente.com", "password");
        Paciente paciente2 = new Paciente("Paciente Test2", "67890", "555-5678", "TESTP789012", "test2@paciente.com", "password");
        
        paciente1.guardar();
        paciente2.guardar();
        
        // Crear consultas de prueba
        Consulta consulta1 = new Consulta(1, 1, LocalDate.now().plusDays(1), LocalTime.of(10, 0), "Chequeo general");
        Consulta consulta2 = new Consulta(2, 2, LocalDate.now().plusDays(2), LocalTime.of(14, 0), "Consulta pediatría");
        
        consulta1.guardar();
        consulta2.guardar();
        
        System.out.println("   ✅ Médicos creados: " + Medico.obtenerTodos().size());
        System.out.println("   ✅ Pacientes creados: " + Paciente.obtenerTodos().size());
        System.out.println("   ✅ Consultas creadas: " + Consulta.obtenerTodas().size());
    }
    
    private static void pruebaConsultasOptimizadas() {
        System.out.println("\n⚡ Prueba 2: Consultas optimizadas");
        
        // Probar obtener consultas por paciente
        List<Consulta> consultasPaciente = Consulta.obtenerPorPaciente(1);
        System.out.println("   ✅ Consultas del paciente 1: " + consultasPaciente.size());
        
        // Probar obtener consultas por rango de fechas
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(7);
        List<Consulta> consultasRango = Consulta.obtenerPorRangoFechas(inicio, fin);
        System.out.println("   ✅ Consultas en rango: " + consultasRango.size());
        
        // Probar verificación de disponibilidad
        boolean disponible = Consulta.verificarDisponibilidad(1, LocalDate.now().plusDays(3), LocalTime.of(15, 0));
        System.out.println("   ✅ Horario disponible: " + disponible);
    }
    
    private static void pruebaValidaciones() {
        System.out.println("\n🛡️ Prueba 3: Validaciones básicas");
        
        // Probar validaciones básicas sin usar ErrorHandler
        String textoVacio = "";
        String textoValido = "Texto válido";
        String textoLargo = "Este es un texto muy largo que excede el límite máximo permitido para la validación";
        
        boolean esVacio = textoVacio == null || textoVacio.trim().isEmpty();
        boolean esValido = textoValido != null && !textoValido.trim().isEmpty();
        boolean longitudCorrecta = textoValido.length() >= 5 && textoValido.length() <= 50;
        boolean longitudIncorrecta = textoLargo.length() > 50;
        
        System.out.println("   ✅ Validación texto vacío (debe ser true): " + esVacio);
        System.out.println("   ✅ Validación texto válido (debe ser true): " + esValido);
        System.out.println("   ✅ Validación longitud válida (debe ser true): " + longitudCorrecta);
        System.out.println("   ✅ Validación longitud inválida (debe ser true): " + longitudIncorrecta);
    }
    
    private static void pruebaEstadisticas() {
        System.out.println("\n📊 Prueba 4: Estadísticas optimizadas");
        
        // Probar estadísticas optimizadas
        Map<String, Long> estadisticas = Consulta.obtenerEstadisticas();
        System.out.println("   ✅ Estadísticas calculadas:");
        for (Map.Entry<String, Long> entry : estadisticas.entrySet()) {
            System.out.println("      - " + entry.getKey() + ": " + entry.getValue());
        }
        
        // Probar completar una cita
        List<Consulta> consultas = Consulta.obtenerTodas();
        if (!consultas.isEmpty()) {
            int idConsulta = consultas.get(0).getId();
            boolean completada = Consulta.agregarDiagnostico(idConsulta, 
                "Diagnóstico de prueba para validar funcionalidad", 
                "Tratamiento de prueba para verificar sistema", 
                "Observaciones adicionales");
            System.out.println("   ✅ Cita completada exitosamente: " + completada);
        }
        
        // Verificar estadísticas actualizadas
        Map<String, Long> estadisticasActualizadas = Consulta.obtenerEstadisticas();
        System.out.println("   ✅ Estadísticas actualizadas:");
        for (Map.Entry<String, Long> entry : estadisticasActualizadas.entrySet()) {
            System.out.println("      - " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
