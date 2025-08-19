package com.gestorcitasmedicas.model;

import com.gestorcitasmedicas.utils.OracleDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Medico {
    private int id;
    private String nombre;
    private String especialidad;
    private String cedula;
    private String correo;
    private String telefono;
    private String horario;
    private String consultorio;
    private String contrasena;
    private String rol;

    public Medico() {
        this.rol = "medico";
    }

    public Medico(String nombre, String especialidad, String cedula, String correo, 
                  String telefono, String horario, String consultorio, String contrasena) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.horario = horario;
        this.consultorio = consultorio;
        this.contrasena = contrasena;
        this.rol = "medico";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Método para guardar médico en Oracle
    public boolean guardar() {
        String sql = "INSERT INTO medicos (nombre, especialidad, cedula, correo, telefono, horario, consultorio, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, this.nombre);
            pstmt.setString(2, this.especialidad);
            pstmt.setString(3, this.cedula);
            pstmt.setString(4, this.correo);
            pstmt.setString(5, this.telefono);
            pstmt.setString(6, this.horario);
            pstmt.setString(7, this.consultorio);
            pstmt.setString(8, this.contrasena);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
                System.out.println("Médico guardado en Oracle: " + this.nombre);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar médico en Oracle: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para verificar si el correo ya existe
    public static boolean correoExiste(String correo) {
        String sql = "SELECT COUNT(*) FROM medicos WHERE correo = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error verificando correo: " + e.getMessage());
        }
        return false;
    }

    // Método para verificar si la cédula ya existe
    public static boolean cedulaExiste(String cedula) {
        String sql = "SELECT COUNT(*) FROM medicos WHERE cedula = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error verificando cédula: " + e.getMessage());
        }
        return false;
    }

    // Método para autenticar médico
    public static Medico autenticar(String correo, String contrasena) {
        String sql = "SELECT * FROM medicos WHERE correo = ? AND contrasena = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidad(rs.getString("especialidad"));
                medico.setCedula(rs.getString("cedula"));
                medico.setCorreo(rs.getString("correo"));
                medico.setTelefono(rs.getString("telefono"));
                medico.setHorario(rs.getString("horario"));
                medico.setConsultorio(rs.getString("consultorio"));
                medico.setContrasena(rs.getString("contrasena"));
                medico.setRol(rs.getString("rol"));
                return medico;
            }
        } catch (SQLException e) {
            System.err.println("Error autenticando médico: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener todos los médicos
    public static List<Medico> obtenerTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medicos ORDER BY nombre";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidad(rs.getString("especialidad"));
                medico.setCedula(rs.getString("cedula"));
                medico.setCorreo(rs.getString("correo"));
                medico.setTelefono(rs.getString("telefono"));
                medico.setHorario(rs.getString("horario"));
                medico.setConsultorio(rs.getString("consultorio"));
                medico.setContrasena(rs.getString("contrasena"));
                medico.setRol(rs.getString("rol"));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo médicos: " + e.getMessage());
        }
        return medicos;
    }

    // Método para limpiar la lista (útil para testing)
    public static void limpiarLista() {
        // Este método no es necesario para Oracle ya que los datos se almacenan en la base de datos
        // Se mantiene por compatibilidad con el código de prueba
        System.out.println("Método limpiarLista() llamado en Medico - no es necesario para Oracle");
    }
}
