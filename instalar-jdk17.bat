@echo off
echo ========================================
echo   Instalando JDK 17
echo ========================================
echo.
echo O instalador do JDK 17 esta em: /tmp/jdk17.msi
echo.
echo Executando instalador...
echo.

msiexec /i "/tmp/jdk17.msi" ADDLOCAL=FeatureMain,FeatureEnvironment,FeatureJarFileRunWith,FeatureJavaHome

echo.
echo Instalacao concluida!
echo.
echo Agora execute: compilar.bat
echo.
pause
