@echo off
echo ========================================
echo   Compilando Registro de Ponto APK
echo ========================================
echo.

REM Verifica se o Gradle Wrapper existe
if not exist "gradlew.bat" (
    echo ERRO: gradlew.bat nao encontrado!
    echo Executando setup primeiro...
    call setup.bat
)

echo Compilando APK...
echo.
call gradlew.bat assembleDebug

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   SUCESSO! APK compilado!
    echo ========================================
    echo.
    echo O arquivo APK esta em:
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Para instalar no celular:
    echo 1. Copie o arquivo app-debug.apk para seu celular
    echo 2. Abra o arquivo no celular
    echo 3. Permita instalacao de fontes desconhecidas se solicitado
    echo.
) else (
    echo.
    echo ========================================
    echo   ERRO na compilacao!
    echo ========================================
    echo.
    echo Verifique os erros acima.
)

pause
