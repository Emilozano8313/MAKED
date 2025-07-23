package com.gestorcitasmedicas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteClinico {
    private int id;
    private Paciente paciente;
    private LocalDate fechaCreacion;
    private String antecedentesMedicos;
    private String antecedentesQuirurgicos;
    private String antecedentesAlergicos;
    private String antecedentesHereditarios;
    private List<Consulta> consultas;

    public ExpedienteClinico() {
        this.consultas = new ArrayList<>();
    }

    public ExpedienteClinico(int id, Paciente paciente, LocalDate fechaCreacion, String antecedentesMedicos, 
                            String antecedentesQuirurgicos, String antecedentesAlergicos, String antecedentesHereditarios) {
        this.id = id;
        this.paciente = paciente;
        this.fechaCreacion = fechaCreacion;
        this.antecedentesMedicos = antecedentesMedicos;
        this.antecedentesQuirurgicos = antecedentesQuirurgicos;
        this.antecedentesAlergicos = antecedentesAlergicos;
        this.antecedentesHereditarios = antecedentesHereditarios;
        this.consultas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAntecedentesMedicos() {
        return antecedentesMedicos;
    }

    public void setAntecedentesMedicos(String antecedentesMedicos) {
        this.antecedentesMedicos = antecedentesMedicos;
    }

    public String getAntecedentesQuirurgicos() {
        return antecedentesQuirurgicos;
    }

    public void setAntecedentesQuirurgicos(String antecedentesQuirurgicos) {
        this.antecedentesQuirurgicos = antecedentesQuirurgicos;
    }

    public String getAntecedentesAlergicos() {
        return antecedentesAlergicos;
    }

    public void setAntecedentesAlergicos(String antecedentesAlergicos) {
        this.antecedentesAlergicos = antecedentesAlergicos;
    }

    public String getAntecedentesHereditarios() {
        return antecedentesHereditarios;
    }

    public void setAntecedentesHereditarios(String antecedentesHereditarios) {
        this.antecedentesHereditarios = antecedentesHereditarios;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void agregarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    @Override
    public String toString() {
        return "ExpedienteClinico{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", fechaCreacion=" + fechaCreacion +
                ", antecedentesMedicos='" + antecedentesMedicos + '\'' +
                ", antecedentesQuirurgicos='" + antecedentesQuirurgicos + '\'' +
                ", antecedentesAlergicos='" + antecedentesAlergicos + '\'' +
                ", antecedentesHereditarios='" + antecedentesHereditarios + '\'' +
                '}';
    }
}
