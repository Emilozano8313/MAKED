package com.gestorcitasmedicas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión a la base de datos
 * Por ahora es una simulación, pero está preparada para conexión real
 */
public class DatabaseConnection {
    
    // Configuración de la base de datos (simulada)
    private static final String URL = "jdbc:oracle:thin:@MAKED_HIGH?TNS_ADMIN=C:/Users/Daniel/Downloads/Wallet_MAKED";
    private static final String USER = "ADMIN";
    private static final String PASSWORD = "MAKEDproject123_";
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de Oracle
            Class.forName("oracle.jdbc.OracleDriver");

            // Crear y retornar la conexión
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de Oracle no encontrado", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar la conexión: " + e.getMessage());
            return false;
        }
    }
}