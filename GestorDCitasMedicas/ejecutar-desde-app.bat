@echo off
echo ========================================
echo EJECUTANDO DESDE App.java
echo ========================================

REM Cambiar al directorio del proyecto
cd /d "%~dp0"

REM Verificar que estamos en el directorio correcto
if not exist "src\main\java\com\gestorcitasmedicas\App.java" (
    echo ERROR: No se encuentra App.java
    echo Asegurate de estar en el directorio correcto
    pause
    exit /b 1
)

REM Verificar que JavaFX esté disponible
if not exist "javafx-sdk-21" (
    echo ERROR: JavaFX SDK no encontrado
    echo Ejecuta download-javafx.ps1 primero
    pause
    exit /b 1
)

echo Compilando App.java y todas las clases...

REM Crear directorio de clases si no existe
if not exist "target\classes" mkdir "target\classes"

REM Compilar todas las clases
javac -cp "javafx-sdk-21/lib/*;lib/*" -d target/classes ^
    src/main/java/com/gestorcitasmedicas/App.java ^
    src/main/java/com/gestorcitasmedicas/model/*.java ^
    src/main/java/com/gestorcitasmedicas/utils/*.java ^
    src/main/java/com/gestorcitasmedicas/controller/*.java

if errorlevel 1 (
    echo ERROR: Fallo en la compilacion
    pause
    exit /b 1
)

echo Compilacion exitosa!
echo Ejecutando la aplicacion...

REM Ejecutar la aplicación
java --module-path "javafx-sdk-21/lib" ^
     --add-modules javafx.controls,javafx.fxml ^
     --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED ^
     -cp "target/classes;lib/*" ^
     com.gestorcitasmedicas.App

if errorlevel 1 (
    echo ERROR: Fallo al ejecutar la aplicacion
    pause
    exit /b 1
)

echo Aplicacion terminada correctamente.
pause
