# Script para ejecutar la aplicación directamente desde App.java
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "EJECUTANDO DESDE App.java" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Obtener el directorio del script
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptDir

Write-Host "Directorio actual: $(Get-Location)" -ForegroundColor Yellow

# Verificar que App.java existe
if (-not (Test-Path "src\main\java\com\gestorcitasmedicas\App.java")) {
    Write-Host "ERROR: No se encuentra App.java" -ForegroundColor Red
    Write-Host "Asegúrate de estar en el directorio correcto" -ForegroundColor Red
    Read-Host "Presiona Enter para continuar"
    exit 1
}

# Verificar que JavaFX esté disponible
if (-not (Test-Path "javafx-sdk-21")) {
    Write-Host "ERROR: JavaFX SDK no encontrado" -ForegroundColor Red
    Write-Host "Ejecuta download-javafx.ps1 primero" -ForegroundColor Red
    Read-Host "Presiona Enter para continuar"
    exit 1
}

Write-Host "Compilando App.java y todas las clases..." -ForegroundColor Green

# Crear directorio de clases si no existe
if (-not (Test-Path "target\classes")) {
    New-Item -ItemType Directory -Path "target\classes" -Force | Out-Null
}

# Compilar todas las clases
$compileResult = javac -cp "javafx-sdk-21/lib/*;lib/*" -d target/classes `
    src/main/java/com/gestorcitasmedicas/App.java `
    src/main/java/com/gestorcitasmedicas/model/*.java `
    src/main/java/com/gestorcitasmedicas/utils/*.java `
    src/main/java/com/gestorcitasmedicas/controller/*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Fallo en la compilación" -ForegroundColor Red
    Write-Host $compileResult -ForegroundColor Red
    Read-Host "Presiona Enter para continuar"
    exit 1
}

Write-Host "Compilación exitosa!" -ForegroundColor Green
Write-Host "Ejecutando la aplicación..." -ForegroundColor Green

# Ejecutar la aplicación
try {
    java --module-path "javafx-sdk-21/lib" `
         --add-modules javafx.controls,javafx.fxml `
         --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED `
         -cp "target/classes;lib/*" `
         com.gestorcitasmedicas.App
} catch {
    Write-Host "ERROR: Fallo al ejecutar la aplicación" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
}

Write-Host "Aplicación terminada." -ForegroundColor Green
Read-Host "Presiona Enter para continuar"
