@echo off
echo === EJECUTANDO GESTOR DE CITAS MEDICAS ===

REM Verificar que JavaFX esté disponible
if not exist "javafx-sdk-21" (
    echo ERROR: JavaFX SDK no encontrado en javafx-sdk-21
    echo Ejecute download-javafx.ps1 primero
    pause
    exit /b 1
)

REM Verificar que las clases estén compiladas
if not exist "target\classes\com\gestorcitasmedicas\App.class" (
    echo Compilando clases...
    javac -cp "javafx-sdk-21/lib/*;lib/*" -d target/classes src/main/java/com/gestorcitasmedicas/App.java src/main/java/com/gestorcitasmedicas/model/*.java src/main/java/com/gestorcitasmedicas/utils/*.java src/main/java/com/gestorcitasmedicas/controller/*.java
    if errorlevel 1 (
        echo ERROR: Fallo en la compilacion
        pause
        exit /b 1
    )
)

echo Iniciando aplicacion...
java --module-path "javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED -cp "target/classes;lib/*" com.gestorcitasmedicas.App

if errorlevel 1 (
    echo ERROR al ejecutar la aplicacion
    pause
    exit /b 1
)

echo Aplicacion terminada.
pause
