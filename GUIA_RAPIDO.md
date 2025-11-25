# 🚀 GUIA RÁPIDO - Instalar App no Celular

## ⚡ Opção 1: MAIS FÁCIL - Sem instalar nada no PC

### Usar um serviço online para compilar:

**Recomendo: Usar o GitHub + GitHub Actions**

1. Crie uma conta no GitHub (grátis): https://github.com
2. Crie um novo repositório
3. Faça upload de todos os arquivos desta pasta
4. Eu configuro o GitHub Actions para compilar automaticamente
5. Você baixa o APK pronto!

**OU use o Replit:**
1. Acesse: https://replit.com
2. Crie uma conta
3. Importe o projeto
4. Execute: `./gradlew assembleDebug`

---

## 💻 Opção 2: Compilar no seu PC

### Requisitos:
- ✅ Java JDK 17 instalado
- ✅ Conexão com internet (para baixar dependências)

### Passos:

1. **Instale o Java JDK 17** (se ainda não tiver)
   - Baixe: https://adoptium.net/temurin/releases/?version=17
   - Escolha: Windows x64 (MSI installer)
   - Instale normalmente

2. **Verifique o Java**
   Abra o Prompt de Comando e digite:
   ```
   java -version
   ```
   Deve aparecer "17.x.x"

3. **Compile o App**
   - Abra o Prompt de Comando **NESTA PASTA**
   - Digite:
   ```
   compilar.bat
   ```
   - Aguarde (primeira vez demora ~5 minutos)

4. **Pegue o APK**
   - O arquivo está em: `app\build\outputs\apk\debug\app-debug.apk`

5. **Instale no Celular**
   - Copie o `app-debug.apk` para o celular
   - Abra o arquivo no celular
   - Permita instalar de fontes desconhecidas
   - Instale!

---

## 📱 Como Instalar APK no Celular

### Android 13+:
1. Copie o arquivo `app-debug.apk` para o celular
2. Abra o arquivo (use o app "Arquivos" ou "Downloads")
3. Android vai perguntar: "Permitir que ARQUIVOS instale apps?"
4. Toque em "Configurações"
5. Ative "Permitir desta fonte"
6. Volte e toque em "Instalar"

### Android 8-12:
1. Configurações → Segurança
2. Ative "Fontes desconhecidas"
3. Abra o arquivo APK
4. Instale

---

## ❓ Qual opção você prefere?

**Responda:**
- A) Quero que você configure o GitHub para eu baixar o APK pronto
- B) Vou compilar no meu PC (tenho Java instalado)
- C) Vou tentar compilar, mas preciso de ajuda para instalar o Java

---

## 🆘 Problemas Comuns

**"Java não encontrado"**
→ Instale o Java JDK 17 (link acima)

**"Compilação falhou"**
→ Verifique a conexão com internet
→ Execute `compilar.bat` novamente

**"Não consigo instalar no celular"**
→ Veja as instruções de instalação acima
→ Cada versão do Android é diferente

---

## ✅ Depois de Instalar

1. Abra o app "Registro de Ponto"
2. Permita acesso à localização (quando pedir)
3. Aguarde o GPS pegar sua localização
4. Teste clicando em "ENTRADA"
5. Depois clique em "SAÍDA"
6. Use "Exportar para XLSX" para gerar a planilha!

A planilha pode ser compartilhada direto pelo WhatsApp, Email, etc.
