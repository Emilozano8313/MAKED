# Configuración de Base de Datos Oracle

## Conexión a Oracle Cloud

### Credenciales de Conexión
- **URL**: `jdbc:oracle:thin:@ge3645798609b17_maked_high.adb.oraclecloud.com`
- **Usuario**: `ADMIN`
- **Contraseña**: `MAKEDproject123_`

### Pasos para Configurar la Base de Datos

1. **Conectar a Oracle Cloud**
   ```sql
   -- Conectar como ADMIN
   sqlplus ADMIN/MAKEDproject123_@ge3645798609b17_maked_high.adb.oraclecloud.com
   ```

2. **Ejecutar el Script de Creación**
   ```sql
   -- Ejecutar el archivo scripts_crear_tablas.sql
   @scripts_crear_tablas.sql
   ```

3. **Verificar las Tablas Creadas**
   ```sql
   SELECT table_name FROM user_tables;
   ```

## Estructura de Tablas

### Tabla: MEDICOS
- `id` - ID único (auto-incremento)
- `nombre` - Nombre completo del médico
- `especialidad` - Especialidad médica
- `cedula` - Número de cédula profesional (único)
- `correo` - Correo electrónico (único)
- `telefono` - Número telefónico
- `horario` - Horario de trabajo
- `consultorio` - Número de consultorio
- `contrasena` - Contraseña de acceso
- `rol` - Rol del usuario (default: 'medico')

### Tabla: PACIENTES
- `id` - ID único (auto-incremento)
- `nombre` - Nombre completo del paciente
- `curp` - CURP del paciente (único)
- `telefono` - Número telefónico
- `matricula` - Número de matrícula (único)
- `correo` - Correo electrónico (único)
- `contrasena` - Contraseña de acceso
- `rol` - Rol del usuario (default: 'paciente')

### Tabla: CONSULTAS
- `id` - ID único (auto-incremento)
- `id_paciente` - Referencia al paciente (FK)
- `id_medico` - Referencia al médico (FK)
- `fecha` - Fecha de la consulta
- `hora` - Hora de la consulta
- `motivo` - Motivo de la consulta
- `estado` - Estado (programada, completada, cancelada)
- `diagnostico` - Diagnóstico médico (CLOB)
- `tratamiento` - Tratamiento prescrito (CLOB)
- `observaciones` - Observaciones adicionales (CLOB)
- `fecha_creacion` - Fecha de creación del registro

## Datos de Prueba Incluidos

### Médicos
1. Dr. Juan Pérez - Cardiología
2. Dra. María González - Dermatología
3. Dr. Carlos Rodríguez - Pediatría

### Pacientes
1. María González
2. Juan López
3. Ana Martínez

### Consultas
- 5 consultas programadas con diferentes fechas y médicos

## Verificación de Conexión

Para verificar que la conexión funciona correctamente:

```java
// Test de conexión
try (Connection conn = OracleDatabase.getConnection()) {
    System.out.println("¡Conexión exitosa a Oracle!");
} catch (SQLException e) {
    System.err.println("Error de conexión: " + e.getMessage());
}
```

## Notas Importantes

- **Driver Oracle**: Se requiere el driver `ojdbc8.jar` en el classpath
- **Dependencias Maven**: Ya incluidas en `pom.xml`
- **Pool de Conexiones**: Implementación simplificada sin pool para evitar dependencias adicionales
- **Transacciones**: Todas las operaciones usan transacciones automáticas
- **Validaciones**: Se mantienen las validaciones de duplicados y formato
