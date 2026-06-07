@echo off
setlocal

:: ─── RUTAS ───────────────────────────────────────────────────
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot
set JLINK=%JAVA_HOME%\bin\jlink.exe
set LIBS=C:\Joshua\Proyecto\target\libs
set OUTPUT=C:\Joshua\Proyecto\target\proyecto-una-jre

:: ─── BORRAR OUTPUT ANTERIOR ──────────────────────────────────
if exist "%OUTPUT%" (
    echo Borrando runtime anterior...
    rmdir /S /Q "%OUTPUT%"
)

:: ─── JLINK CON TODOS LOS MÓDULOS DEL JDK ────────────────────
echo Generando runtime...

"%JLINK%" ^
  --module-path "%LIBS%;%JAVA_HOME%\jmods" ^
  --add-modules java.base,java.sql,java.naming,java.instrument,java.desktop,java.logging,java.xml,java.management,java.security.jgss,java.transaction.xa,java.rmi,java.compiler,java.net.http,jdk.unsupported,jdk.attach,jdk.jdi,javafx.controls,javafx.fxml,javafx.media,javafx.graphics,javafx.base,Proyecto ^
  --output "%OUTPUT%" ^
  --compress=2 ^
  --strip-debug ^
  --no-header-files ^
  --no-man-pages

if errorlevel 1 (
    echo.
    echo ERROR: jlink fallo. Revisa los mensajes arriba.
    pause
    exit /b 1
)

:: ─── COPIAR TODOS LOS JARS ───────────────────────────────────
echo Copiando dependencias...
if not exist "%OUTPUT%\libs" mkdir "%OUTPUT%\libs"
xcopy /Y "%LIBS%\*.jar" "%OUTPUT%\libs\" >nul

:: ─── CREAR LANZADOR FINAL ────────────────────────────────────
echo Creando lanzador...
(
    echo @echo off
    echo cd /d "%%~dp0"
    echo bin\java ^
        --module-path libs ^
        --add-modules Proyecto ^
        -cp "libs\*" ^
        cr.ac.una.proyecto.App
    echo pause
) > "%OUTPUT%\ejecutable-una.bat"

echo.
echo ================================================
echo  Runtime listo en: %OUTPUT%
echo  Ejecuta: %OUTPUT%\ejecutable-una.bat
echo ================================================
pause