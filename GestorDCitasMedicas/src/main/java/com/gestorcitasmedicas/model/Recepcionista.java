package com.gestorcitasmedicas.model;
package com.gestorcitasmedicas.model;

public class Recepcionista extends Usuario {
    private String turno;
    private String extension;

    public Recepcionista() {
    }

    public Recepcionista(Long id, String nombre, String apellidos, String email, String username, 
                         String password, String rol, String turno, String extension) {
        super(id, nombre, apellidos, email, username, password, rol);
        this.turno = turno;
        this.extension = extension;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
public class Recepcionista extends Usuario {
    private String departamento;
    private String telefono;

    public Recepcionista() {
    }

    public Recepcionista(int id, String nombre, String apellido, String email, String password, String departamento, String telefono) {
        super(id, nombre, apellido, email, password, "Recepcionista");
        this.departamento = departamento;
        this.telefono = telefono;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Recepcionista{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", departamento='" + departamento + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
