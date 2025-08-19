# Script simple para ejecutar la aplicación JavaFX
Write-Host "=== EJECUTANDO GESTOR DE CITAS MÉDICAS ===" -ForegroundColor Green

# Verificar que estamos en el directorio correcto
$currentDir = Get-Location
Write-Host "Directorio actual: $currentDir" -ForegroundColor Yellow

# Verificar que JavaFX esté disponible
if (-not (Test-Path "javafx-sdk-21")) {
    Write-Host "ERROR: JavaFX SDK no encontrado en javafx-sdk-21" -ForegroundColor Red
    Write-Host "Ejecute download-javafx.ps1 primero" -ForegroundColor Red
    exit 1
}

# Verificar que las clases estén compiladas
if (-not (Test-Path "target/classes/com/gestorcitasmedicas/App.class")) {
    Write-Host "Compilando clases..." -ForegroundColor Yellow
    javac -cp "javafx-sdk-21/lib/*;lib/*" -d target/classes src/main/java/com/gestorcitasmedicas/App.java src/main/java/com/gestorcitasmedicas/model/*.java src/main/java/com/gestorcitasmedicas/utils/*.java src/main/java/com/gestorcitasmedicas/controller/*.java
    if ($LASTEXITCODE -ne 0) {
        Write-Host "ERROR: Fallo en la compilación" -ForegroundColor Red
        exit 1
    }
}

# Verificar que la clase principal existe
if (-not (Test-Path "target/classes/com/gestorcitasmedicas/App.class")) {
    Write-Host "ERROR: No se encontró la clase principal App.class" -ForegroundColor Red
    exit 1
}

Write-Host "Iniciando aplicación..." -ForegroundColor Green

# Ejecutar la aplicación con manejo de errores
try {
    java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED -cp "target/classes;lib/*" com.gestorcitasmedicas.App
} catch {
    Write-Host "ERROR al ejecutar la aplicación: $_" -ForegroundColor Red
    exit 1
}

Write-Host "Aplicación terminada." -ForegroundColor Green
