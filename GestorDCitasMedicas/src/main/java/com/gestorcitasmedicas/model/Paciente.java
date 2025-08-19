package com.gestorcitasmedicas.model;

import com.gestorcitasmedicas.utils.OracleDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id;
    private String nombre;
    private String curp;
    private String telefono;
    private String matricula;
    private String correo;
    private String contrasena;
    private String rol;

    public Paciente() {
        this.rol = "paciente";
    }

    public Paciente(String nombre, String curp, String telefono, String matricula, 
                   String correo, String contrasena) {
        this.nombre = nombre;
        this.curp = curp;
        this.telefono = telefono;
        this.matricula = matricula;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = "paciente";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Método para guardar paciente en Oracle
    public boolean guardar() {
        String sql = "INSERT INTO pacientes (nombre, curp, telefono, matricula, correo, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, this.nombre);
            pstmt.setString(2, this.curp);
            pstmt.setString(3, this.telefono);
            pstmt.setString(4, this.matricula);
            pstmt.setString(5, this.correo);
            pstmt.setString(6, this.contrasena);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
                System.out.println("Paciente guardado en Oracle: " + this.nombre);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar paciente en Oracle: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para verificar si el correo ya existe
    public static boolean correoExiste(String correo) {
        String sql = "SELECT COUNT(*) FROM pacientes WHERE correo = ?";
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

    // Método para verificar si el CURP ya existe
    public static boolean curpExiste(String curp) {
        String sql = "SELECT COUNT(*) FROM pacientes WHERE curp = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, curp);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error verificando CURP: " + e.getMessage());
        }
        return false;
    }

    // Método para verificar si la matrícula ya existe
    public static boolean matriculaExiste(String matricula) {
        String sql = "SELECT COUNT(*) FROM pacientes WHERE matricula = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matricula);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error verificando matrícula: " + e.getMessage());
        }
        return false;
    }

    // Método para autenticar paciente
    public static Paciente autenticar(String correo, String contrasena) {
        String sql = "SELECT * FROM pacientes WHERE correo = ? AND contrasena = ?";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setCurp(rs.getString("curp"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setMatricula(rs.getString("matricula"));
                paciente.setCorreo(rs.getString("correo"));
                paciente.setContrasena(rs.getString("contrasena"));
                paciente.setRol(rs.getString("rol"));
                return paciente;
            }
        } catch (SQLException e) {
            System.err.println("Error autenticando paciente: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener todos los pacientes
    public static List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY nombre";
        try (Connection conn = OracleDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setCurp(rs.getString("curp"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setMatricula(rs.getString("matricula"));
                paciente.setCorreo(rs.getString("correo"));
                paciente.setContrasena(rs.getString("contrasena"));
                paciente.setRol(rs.getString("rol"));
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo pacientes: " + e.getMessage());
        }
        return pacientes;
    }


}
