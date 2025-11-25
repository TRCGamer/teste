# Como Compilar e Instalar no Celular (SEM Android Studio)

## Pré-requisitos

1. **Java JDK 17** instalado no Windows
   - Baixe em: https://www.oracle.com/java/technologies/downloads/#java17
   - Ou use: https://adoptium.net/temurin/releases/?version=17

2. Verifique se o Java está instalado:
   ```
   java -version
   ```
   Deve mostrar Java 17 ou superior.

---

## Método 1: Compilar no Windows e Transferir para o Celular

### Passo 1: Setup Inicial (apenas na primeira vez)

1. Abra o prompt de comando nesta pasta
2. Execute:
   ```
   setup.bat
   ```

### Passo 2: Compilar o APK

1. Execute:
   ```
   compilar.bat
   ```

2. Aguarde a compilação (pode demorar alguns minutos na primeira vez)

3. O APK será criado em:
   ```
   app\build\outputs\apk\debug\app-debug.apk
   ```

### Passo 3: Instalar no Celular

1. **Copie o arquivo `app-debug.apk` para seu celular**
   - Via cabo USB
   - Via WhatsApp (envie para você mesmo)
   - Via Google Drive/Dropbox
   - Via email

2. **No celular, abra o arquivo APK**
   - Vá até a pasta de Downloads
   - Toque no arquivo `app-debug.apk`

3. **Permita a instalação**
   - O Android vai perguntar se você permite instalar de fontes desconhecidas
   - Toque em "Configurações" → Ative "Permitir desta fonte"
   - Volte e toque em "Instalar"

4. **Pronto!** O app estará instalado

---

## Método 2: Usando GitHub Actions (Compilar na Nuvem)

Se você tiver problemas para compilar no Windows:

1. Crie um repositório no GitHub
2. Faça upload de todos os arquivos
3. Use o GitHub Actions para compilar automaticamente
4. Baixe o APK gerado

---

## Método 3: Usar um Serviço Online

### AppGyver / App Inventor
- Alguns serviços online permitem compilar apps Android
- Você precisa fazer upload do código

---

## Solução Mais Simples: Compilar Online

Se você não quiser instalar Java/ferramentas:

### Usando Replit ou CodeSandbox:
1. Crie uma conta em https://replit.com
2. Crie um novo projeto Android
3. Cole os arquivos do projeto
4. Use o comando: `./gradlew assembleDebug`

---

## Problemas Comuns

### "Java não encontrado"
- Instale o Java JDK 17
- Adicione o Java ao PATH do Windows

### "gradlew não encontrado"
- Execute `setup.bat` primeiro

### "Compilação falhou"
- Verifique se tem conexão com internet (precisa baixar dependências)
- Execute novamente, pode ser problema temporário

### "Não consigo instalar no celular"
**Android 13+:**
- Configurações → Apps → Acesso especial → Instalar apps desconhecidos
- Selecione o app que você está usando (Chrome, Arquivos, etc.)
- Ative "Permitir desta fonte"

**Android 8-12:**
- Configurações → Segurança → Fontes desconhecidas
- Ative a opção

---

## Alternativa MAIS FÁCIL: Usar o APK Online Builder

Se nada disso funcionar, posso:

1. Criar um repositório GitHub com o código
2. Configurar GitHub Actions para compilar automaticamente
3. Você baixa o APK pronto direto do GitHub

**Quer que eu configure isso para você?**

---

## Testando o App

Após instalar:

1. Abra o app "Registro de Ponto"
2. Permita acesso à localização
3. Aguarde o GPS capturar sua localização
4. Clique em "ENTRADA" para registrar
5. Clique em "SAÍDA" para registrar saída
6. Use "Exportar para XLSX" para gerar a planilha

O arquivo XLSX ficará salvo em:
```
/Android/data/com.example.registroponto/files/
```

Você pode compartilhar direto do app!
