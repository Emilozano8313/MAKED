import com.gestorcitasmedicas.utils.OracleDatabase;
import java.sql.*;

public class TestOracleConnection {
    public static void main(String[] args) {
        System.out.println("=== Test de Conexión a Oracle Cloud ===");
        
        try {
            // Probar conexión
            try (Connection conn = OracleDatabase.getConnection()) {
                System.out.println("✅ Conexión exitosa a Oracle Cloud!");
                System.out.println("URL: " + conn.getMetaData().getURL());
                System.out.println("Usuario: " + conn.getMetaData().getUserName());
                System.out.println("Driver: " + conn.getMetaData().getDriverName());
                
                // Verificar si las tablas existen
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "MEDICOS", new String[]{"TABLE"});
                
                if (tables.next()) {
                    System.out.println("✅ Tabla MEDICOS encontrada");
                } else {
                    System.out.println("❌ Tabla MEDICOS no encontrada - Necesita ejecutar el script SQL");
                }
                
                tables = metaData.getTables(null, null, "PACIENTES", new String[]{"TABLE"});
                if (tables.next()) {
                    System.out.println("✅ Tabla PACIENTES encontrada");
                } else {
                    System.out.println("❌ Tabla PACIENTES no encontrada - Necesita ejecutar el script SQL");
                }
                
                tables = metaData.getTables(null, null, "CONSULTAS", new String[]{"TABLE"});
                if (tables.next()) {
                    System.out.println("✅ Tabla CONSULTAS encontrada");
                } else {
                    System.out.println("❌ Tabla CONSULTAS no encontrada - Necesita ejecutar el script SQL");
                }
                
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Fin del Test ===");
    }
}
