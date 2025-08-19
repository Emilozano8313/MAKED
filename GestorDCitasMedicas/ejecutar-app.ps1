# Script simple para ejecutar la aplicación JavaFX
Write-Host "Ejecutando Gestor de Citas Médicas..."

# Verificar que JavaFX esté disponible
if (-not (Test-Path "javafx-sdk-21")) {
    Write-Host "Error: JavaFX SDK no encontrado. Ejecute download-javafx.ps1 primero."
    exit 1
}

# Verificar que las clases estén compiladas
if (-not (Test-Path "target/classes/com/gestorcitasmedicas/App.class")) {
    Write-Host "Error: Clases no compiladas. Compilando..."
    javac -cp "javafx-sdk-21/lib/*;lib/*" -d target/classes src/main/java/com/gestorcitasmedicas/App.java
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error al compilar. Verifique que Java esté instalado."
        exit 1
    }
}

# Ejecutar la aplicación
Write-Host "Iniciando aplicación..."
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED -cp "target/classes;lib/*" com.gestorcitasmedicas.App

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error al ejecutar la aplicación."
    exit 1
}
