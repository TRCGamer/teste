@echo off
echo ========================================
echo   Enviar Projeto para o GitHub
echo ========================================
echo.

echo ANTES DE EXECUTAR:
echo.
echo 1. Crie um repositorio no GitHub
echo 2. Copie o link do repositorio (ex: https://github.com/SEU-USUARIO/registro-ponto-app.git)
echo.

set /p repo_url="Cole o link do repositorio aqui: "

if "%repo_url%"=="" (
    echo.
    echo ERRO: Voce precisa fornecer o link do repositorio!
    pause
    exit /b 1
)

echo.
echo Adicionando arquivos...
git add .

echo.
echo Criando commit...
git commit -m "Initial commit - Registro de Ponto App Android"

echo.
echo Configurando branch main...
git branch -M main

echo.
echo Adicionando repositorio remoto...
git remote add origin %repo_url%

echo.
echo Enviando para o GitHub...
git push -u origin main

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   SUCESSO!
    echo ========================================
    echo.
    echo Projeto enviado para o GitHub!
    echo.
    echo Proximos passos:
    echo 1. Acesse seu repositorio no GitHub
    echo 2. Va na aba "Actions"
    echo 3. Aguarde a compilacao terminar (5-10 minutos)
    echo 4. Baixe o APK em "Artifacts"
    echo.
) else (
    echo.
    echo ========================================
    echo   ERRO!
    echo ========================================
    echo.
    echo Se pediu usuario/senha:
    echo - Usuario: seu nome de usuario do GitHub
    echo - Senha: use um Personal Access Token (veja COMO_USAR_GITHUB.md)
    echo.
)

pause
