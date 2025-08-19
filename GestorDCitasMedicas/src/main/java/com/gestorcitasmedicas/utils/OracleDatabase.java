package com.gestorcitasmedicas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabase {
    final static String DB_URL = "jdbc:oracle:thin:@ge3645798609b17_maked_high.adb.oraclecloud.com";
    final static String DB_USER = "ADMIN";
    final static String DB_PASSWORD = "MAKEDproject123_";

    static {
        try {
            // Cargar el driver de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error cargando driver de Oracle: " + e.getMessage());
        }
    }

    // Método público para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
