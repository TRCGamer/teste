# 🚀 Como Usar o GitHub para Compilar o APK

## Passo a Passo Completo

### 1️⃣ Criar Conta no GitHub (se não tiver)

1. Acesse: https://github.com
2. Clique em "Sign up"
3. Crie sua conta (é grátis!)

### 2️⃣ Criar Novo Repositório

1. Após fazer login, clique no **+** (canto superior direito)
2. Selecione **"New repository"**
3. Preencha:
   - **Repository name**: `registro-ponto-app` (ou o nome que preferir)
   - **Description**: "App Android de Registro de Ponto com GPS"
   - Marque **"Public"** (ou Private, se preferir)
   - **NÃO** marque "Add a README file"
4. Clique em **"Create repository"**

### 3️⃣ Fazer Upload dos Arquivos

O GitHub vai mostrar uma página com instruções. **Siga a opção 2:**

**"…or create a new repository on the command line"**

Copie os comandos que aparecem (vou criar um script para você também).

### 4️⃣ Executar os Comandos

Abra o **Prompt de Comando** nesta pasta e execute:

```bash
git init
git add .
git commit -m "Initial commit - Registro de Ponto App"
git branch -M main
git remote add origin https://github.com/SEU-USUARIO/registro-ponto-app.git
git push -u origin main
```

**IMPORTANTE:** Substitua `SEU-USUARIO` pelo seu nome de usuário do GitHub!

### 5️⃣ GitHub Vai Compilar Automaticamente!

Após fazer o push:

1. Vá para o repositório no GitHub
2. Clique na aba **"Actions"**
3. Você verá o build rodando (bolinha amarela 🟡)
4. Aguarde ~5-10 minutos até ficar verde ✅

### 6️⃣ Baixar o APK

Quando o build terminar:

1. Clique no build (na aba Actions)
2. Role até o final da página
3. Procure por **"Artifacts"**
4. Clique em **"app-debug"**
5. Baixe o arquivo ZIP
6. Descompacte e pegue o `app-debug.apk`

### 7️⃣ Instalar no Celular

1. Copie o `app-debug.apk` para o celular
2. Abra o arquivo
3. Permita instalação de fontes desconhecidas
4. Instale!

---

## 🔄 Para Fazer Mudanças Futuras

Se você fizer alterações no código:

```bash
git add .
git commit -m "Descrição da mudança"
git push
```

O GitHub vai compilar automaticamente de novo!

---

## ⚠️ Problemas Comuns

### "Git não é reconhecido como comando"

**Solução:** Instale o Git primeiro

1. Baixe: https://git-scm.com/download/win
2. Instale com as opções padrão
3. Reinicie o Prompt de Comando

### "Permission denied" ou erro de autenticação

**Solução:** Use um Personal Access Token

1. GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token (classic)
3. Marque **"repo"**
4. Copie o token gerado
5. Use o token como senha quando o Git pedir

### Build falhou no GitHub

1. Vá em Actions
2. Clique no build que falhou
3. Veja o log de erro
4. Me mostre o erro que eu te ajudo!

---

## 📝 Scripts Prontos

Criei um script para facilitar. Execute: `enviar-para-github.bat`

---

## ✅ Resumo Rápido

1. Crie conta no GitHub
2. Crie repositório
3. Execute `enviar-para-github.bat` (ou os comandos manuais)
4. Aguarde compilação (5-10 min)
5. Baixe APK na aba "Actions" → "Artifacts"
6. Instale no celular

**Dúvidas?** Me chame!
