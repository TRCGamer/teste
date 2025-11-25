@echo off
echo ========================================
echo   Setup Inicial do Projeto
echo ========================================
echo.

REM Cria o diretório gradle/wrapper se não existir
if not exist "gradle\wrapper" mkdir gradle\wrapper

echo Baixando Gradle Wrapper...
echo.

REM Cria o arquivo gradle-wrapper.properties
(
echo distributionBase=GRADLE_USER_HOME
echo distributionPath=wrapper/dists
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.0-bin.zip
echo zipStoreBase=GRADLE_USER_HOME
echo zipStorePath=wrapper/dists
) > gradle\wrapper\gradle-wrapper.properties

echo Gradle Wrapper configurado!
echo.
echo Agora execute: compilar.bat
echo.
pause
