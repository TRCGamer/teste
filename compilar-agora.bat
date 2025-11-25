@echo off
echo ========================================
echo   Compilando APK - Tentativa Final
echo ========================================
echo.

echo Parando todos os processos Gradle...
taskkill /F /IM java.exe 2>nul

echo.
echo Aguardando 3 segundos...
timeout /t 3 >nul

echo.
echo Iniciando compilacao...
echo Isso pode demorar 5-10 minutos na primeira vez
echo.

cd /d "%~dp0"
call gradlew.bat clean assembleDebug --no-daemon --stacktrace

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   SUCESSO! APK compilado!
    echo ========================================
    echo.
    echo O arquivo esta em:
    echo %~dp0app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Copie este arquivo para o celular e instale!
    echo.
) else (
    echo.
    echo ========================================
    echo   ERRO na compilacao
    echo ========================================
    echo.
)

pause
