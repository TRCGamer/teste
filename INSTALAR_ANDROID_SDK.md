# 📱 Como Instalar o Android SDK (Sem Android Studio)

Você precisa do Android SDK para compilar apps Android. Existem 2 formas:

---

## ✅ OPÇÃO 1: MAIS FÁCIL - Usar GitHub Actions (RECOMENDADO)

Já que você não tem Android SDK e não quer instalar o Android Studio completo, a forma **MUITO MAIS FÁCIL** é usar o GitHub Actions para compilar na nuvem.

**Vantagens:**
- Não precisa instalar NADA no seu PC
- Compila automaticamente na nuvem
- Você só baixa o APK pronto

**Quer que eu configure isso?** É só falar!

---

## ⚙️ OPÇÃO 2: Instalar Android Command Line Tools

Se preferir compilar no PC, siga os passos:

### Passo 1: Baixar Android Command Line Tools

1. Acesse: https://developer.android.com/studio#command-line-tools-only
2. Baixe "Command line tools only" para Windows
3. Descompacte em: `C:\Android\cmdline-tools\latest\`

### Passo 2: Instalar Android SDK

Abra o Prompt de Comando como Administrador e execute:

```batch
cd C:\Android\cmdline-tools\latest\bin

sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

sdkmanager --licenses
```

Aceite todas as licenças digitando `y`.

### Passo 3: Configurar Variáveis de Ambiente

1. Painel de Controle → Sistema → Configurações Avançadas
2. Variáveis de Ambiente
3. Adicione:
   - `ANDROID_HOME` = `C:\Android`
   - Adicione ao PATH: `C:\Android\platform-tools`

### Passo 4: Compilar

Execute: `compilar-agora.bat`

---

## 📊 Comparação

| Característica | GitHub Actions | Instalar SDK Local |
|----------------|----------------|-------------------|
| Instalação | Nenhuma | ~3 GB de download |
| Tempo setup | 5 minutos | 30-60 minutos |
| Compilação | 5-10 min | 5-10 min |
| Uso de disco | 0 MB | ~4 GB |
| Requer internet | Sim (1x) | Sim (setup) |

---

## 🤔 Qual Escolher?

### Use GitHub Actions se:
- Não quer instalar nada no PC
- Quer o caminho mais rápido
- Não vai compilar frequentemente

### Instale o SDK Local se:
- Vai desenvolver/compilar frequentemente
- Quer ter controle total
- Tem espaço em disco (~4 GB)

---

## 💡 Minha Recomendação

Para você que só quer testar o app agora, **use GitHub Actions!**

É só eu configurar um repositório e em 10 minutos você tem o APK pronto para download.

**Quer que eu configure?** Responda:
- **SIM** - Configura o GitHub Actions para mim
- **NÃO** - Vou instalar o Android SDK local

Me avise! 😊
