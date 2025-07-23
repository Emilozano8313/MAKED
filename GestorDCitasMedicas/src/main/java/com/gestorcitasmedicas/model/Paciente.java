package com.gestorcitasmedicas.model;
package com.gestorcitasmedicas.model;

import java.time.LocalDate;

public class Paciente extends Usuario {
    private LocalDate fechaNacimiento;
    private String genero;
    private String telefono;
    private String direccion;
    private String grupoSanguineo;
    private String alergias;

    public Paciente() {
    }

    public Paciente(Long id, String nombre, String apellidos, String email, String username, String password, 
                   String rol, LocalDate fechaNacimiento, String genero, String telefono, 
                   String direccion, String grupoSanguineo, String alergias) {
        super(id, nombre, apellidos, email, username, password, rol);
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.telefono = telefono;
        this.direccion = direccion;
        this.grupoSanguineo = grupoSanguineo;
        this.alergias = alergias;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
}
import java.time.LocalDate;

public class Paciente extends Usuario {
    private String direccion;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String genero;
    private String grupoSanguineo;
    private String alergias;

    public Paciente() {
    }

    public Paciente(int id, String nombre, String apellido, String email, String password, 
                   String direccion, String telefono, LocalDate fechaNacimiento, 
                   String genero, String grupoSanguineo, String alergias) {
        super(id, nombre, apellido, email, password, "Paciente");
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.grupoSanguineo = grupoSanguineo;
        this.alergias = alergias;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", grupoSanguineo='" + grupoSanguineo + '\'' +
                ", alergias='" + alergias + '\'' +
                '}';
    }
}
