package com.gestorcitasmedicas.model;

public class Medico extends Usuario {
    private String especialidad;
    private String cedula;
    private String telefono;

    public Medico() {
    }

    public Medico(int id, String nombre, String apellido, String email, String password, String especialidad, String cedula, String telefono) {
        super(id, nombre, apellido, email, password, "Medico");
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.telefono = telefono;
    }
package com.gestorcitasmedicas.model;

public class Medico extends Usuario {
    private String especialidad;
    private String cedula;
    private String consultorio;

    public Medico() {
    }

    public Medico(Long id, String nombre, String apellidos, String email, String username, 
                  String password, String rol, String especialidad, String cedula, String consultorio) {
        super(id, nombre, apellidos, email, username, password, rol);
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.consultorio = consultorio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }
}
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
