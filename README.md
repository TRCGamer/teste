# Registro de Ponto - Aplicativo Android

Aplicativo Android para registro de ponto com localização GPS e exportação para planilha XLSX.

## Funcionalidades

- **Registro de Entrada**: Ao clicar no botão "ENTRADA", o app registra:
  - Horário exato da entrada
  - Localização GPS (latitude e longitude)

- **Registro de Saída**: Ao clicar no botão "SAÍDA", o app registra:
  - Horário exato da saída
  - Localização GPS (latitude e longitude)

- **Visualização de Registros**: Lista todos os registros salvos com:
  - Tipo (Entrada/Saída)
  - Data e hora
  - Coordenadas de localização

- **Exportação XLSX**: Exporta todos os registros para uma planilha Excel (.xlsx) com:
  - Tipo de registro
  - Data
  - Hora
  - Latitude
  - Longitude
  - Localização formatada

## Requisitos

- Android 7.0 (API 24) ou superior
- Permissões de localização
- GPS habilitado

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação
- **Android Jetpack**:
  - Room Database: Armazenamento local
  - ViewBinding: Acesso a views
  - Lifecycle: Gerenciamento de ciclo de vida
  - RecyclerView: Lista de registros
- **Material Design 3**: Interface do usuário
- **Google Location Services**: Captura de localização GPS
- **Apache POI**: Geração de arquivos XLSX
- **Coroutines**: Programação assíncrona

## Estrutura do Projeto

```
app/
├── src/main/
│   ├── java/com/example/registroponto/
│   │   ├── MainActivity.kt                 # Activity principal
│   │   ├── adapter/
│   │   │   └── RegistroAdapter.kt         # Adapter do RecyclerView
│   │   ├── data/
│   │   │   ├── Registro.kt                # Modelo de dados
│   │   │   ├── RegistroDao.kt             # DAO do Room
│   │   │   ├── AppDatabase.kt             # Database do Room
│   │   │   └── Converters.kt              # Conversores do Room
│   │   ├── export/
│   │   │   └── ExcelExporter.kt           # Exportador XLSX
│   │   └── location/
│   │       └── LocationService.kt         # Serviço de localização
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml          # Layout principal
│   │   │   └── item_registro.xml          # Layout do item da lista
│   │   └── values/
│   │       ├── strings.xml
│   │       ├── colors.xml
│   │       └── themes.xml
│   └── AndroidManifest.xml
└── build.gradle
```

## Como Usar

1. **Primeiro Uso**:
   - Ao abrir o app, conceda permissão de localização
   - Aguarde o GPS capturar sua localização

2. **Registrar Entrada**:
   - Clique no botão "ENTRADA"
   - O app salvará automaticamente o horário e sua localização

3. **Registrar Saída**:
   - Clique no botão "SAÍDA"
   - O app salvará automaticamente o horário e sua localização

4. **Visualizar Registros**:
   - Role a lista abaixo dos botões para ver todos os registros

5. **Exportar para XLSX**:
   - Clique em "Exportar para XLSX"
   - Escolha compartilhar o arquivo ou apenas salvá-lo

## Permissões

O aplicativo solicita as seguintes permissões:

- `ACCESS_FINE_LOCATION`: Localização precisa via GPS
- `ACCESS_COARSE_LOCATION`: Localização aproximada
- `WRITE_EXTERNAL_STORAGE`: Para salvar arquivos XLSX (Android < 13)
- `READ_EXTERNAL_STORAGE`: Para ler arquivos (Android < 13)

## Build e Instalação

### Usando Android Studio

1. Abra o projeto no Android Studio
2. Sincronize o Gradle (Sync Project with Gradle Files)
3. Conecte um dispositivo Android ou inicie um emulador
4. Clique em "Run" ou pressione Shift+F10

### Via Linha de Comando

```bash
# Build do APK
./gradlew assembleDebug

# Instalar no dispositivo conectado
./gradlew installDebug
```

## Formato do Arquivo XLSX

A planilha exportada contém as seguintes colunas:

| Tipo | Data | Hora | Latitude | Longitude | Localização |
|------|------|------|----------|-----------|-------------|
| ENTRADA | 25/11/2025 | 08:30:00 | -23.550520 | -46.633308 | Lat: -23.550520, Lng: -46.633308 |
| SAIDA | 25/11/2025 | 17:30:00 | -23.550520 | -46.633308 | Lat: -23.550520, Lng: -46.633308 |

## Observações

- Os registros são salvos localmente no dispositivo usando Room Database
- Os arquivos XLSX são salvos na pasta de arquivos do aplicativo
- A localização é atualizada automaticamente enquanto o app está aberto
- Certifique-se de ter o GPS habilitado para obter localização precisa
