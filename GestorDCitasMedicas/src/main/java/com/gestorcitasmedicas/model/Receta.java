package com.gestorcitasmedicas.model;
package com.gestorcitasmedicas.model;

import java.time.LocalDate;
import java.util.List;

public class Receta {
    private Long id;
    private Consulta consulta;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String indicaciones;
    private boolean activa;

    public Receta() {
    }

    public Receta(Long id, Consulta consulta, LocalDate fechaEmision, LocalDate fechaVencimiento, String indicaciones, boolean activa) {
        this.id = id;
        this.consulta = consulta;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.indicaciones = indicaciones;
        this.activa = activa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
import java.time.LocalDate;

public class Receta {
    private int id;
    private Consulta consulta;
    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String indicaciones;
    private LocalDate fechaEmision;

    public Receta() {
    }

    public Receta(int id, Consulta consulta, String medicamento, String dosis, String frecuencia, String duracion, String indicaciones, LocalDate fechaEmision) {
        this.id = id;
        this.consulta = consulta;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.indicaciones = indicaciones;
        this.fechaEmision = fechaEmision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", consulta=" + consulta.getId() +
                ", medicamento='" + medicamento + '\'' +
                ", dosis='" + dosis + '\'' +
                ", frecuencia='" + frecuencia + '\'' +
                ", duracion='" + duracion + '\'' +
                ", indicaciones='" + indicaciones + '\'' +
                ", fechaEmision=" + fechaEmision +
                '}';
    }
}
