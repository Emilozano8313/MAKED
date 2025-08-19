# Script para descargar el driver de Oracle JDBC
Write-Host "Descargando Oracle JDBC Driver..." -ForegroundColor Green

# URL del driver de Oracle (versión 19.6.0.0)
$url = "https://download.oracle.com/otn-pub/otn_software/jdbc/1916/ojdbc8.jar"
$output = "lib/ojdbc8.jar"

# Crear directorio lib si no existe
if (!(Test-Path "lib")) {
    New-Item -ItemType Directory -Path "lib"
}

# Descargar el driver
try {
    Invoke-WebRequest -Uri $url -OutFile $output
    Write-Host "✅ Driver de Oracle descargado exitosamente en: $output" -ForegroundColor Green
} catch {
    Write-Host "❌ Error descargando el driver: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Por favor, descarga manualmente el driver desde: $url" -ForegroundColor Yellow
}
