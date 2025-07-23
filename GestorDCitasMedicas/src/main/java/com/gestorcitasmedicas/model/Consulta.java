package com.gestorcitasmedicas.model;
package com.gestorcitasmedicas.model;

import java.time.LocalDateTime;

public class Consulta {
    private Long id;
    private CitaMedica citaMedica;
    private String diagnostico;
    private String observaciones;
    private LocalDateTime fechaHora;
    private double precio;

    public Consulta() {
    }

    public Consulta(Long id, CitaMedica citaMedica, String diagnostico, String observaciones, LocalDateTime fechaHora, double precio) {
        this.id = id;
        this.citaMedica = citaMedica;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CitaMedica getCitaMedica() {
        return citaMedica;
    }

    public void setCitaMedica(CitaMedica citaMedica) {
        this.citaMedica = citaMedica;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private int id;
    private CitaMedica cita;
    private String diagnostico;
    private String observaciones;
    private LocalDateTime fechaHora;
    private List<Receta> recetas;

    public Consulta() {
        this.recetas = new ArrayList<>();
    }

    public Consulta(int id, CitaMedica cita, String diagnostico, String observaciones, LocalDateTime fechaHora) {
        this.id = id;
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.fechaHora = fechaHora;
        this.recetas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CitaMedica getCita() {
        return cita;
    }

    public void setCita(CitaMedica cita) {
        this.cita = cita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public void agregarReceta(Receta receta) {
        this.recetas.add(receta);
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", cita=" + cita +
                ", diagnostico='" + diagnostico + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", fechaHora=" + fechaHora +
                ", recetas=" + recetas +
                '}';
    }
}
