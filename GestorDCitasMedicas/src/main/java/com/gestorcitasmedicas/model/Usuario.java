package com.gestorcitasmedicas.model;
import com.gestorcitasmedicas.utils.OracleDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int id;
    private String nombre;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean usuarioValido(String nombre, String contrasena) {
        try(Connection con=  OracleDatabase.getConnection()){
            String sql = "SELECT * FROM paciente WHERE correo_electronico = ? AND contrasena = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
