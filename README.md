# Procesador CSV con Arquitectura MVC - Java 8

## Descripción General

Sistema completo de procesamiento de archivos CSV implementando arquitectura MVC con Java 8, diseñado para manejar grandes volúmenes de datos (82+ millones de registros) utilizando concurrencia y todos los conceptos avanzados de programación orientada a objetos.

**📁 IMPORTANTE**: Los archivos CSV deben ubicarse en la carpeta `data/` del proyecto. El sistema lee automáticamente desde esta ubicación.

## Conceptos del Curso Implementados

### ✅ Programación Orientada a Objetos
- **Herencia**: `AStatisticalProcessor` → `SequentialProcessor`, `ConcurrentProcessor`
- **Polimorfismo**: Interfaces `ICSVProcessor`, `IWorkerTask`
- **Encapsulación**: Getters/Setters en todas las entidades
- **Abstracción**: Clases abstractas y interfaces

### ✅ Conceptos Avanzados
- **Interfaces**: `ICSVProcessor`, `IWorkerTask` con métodos default
- **Clases Abstractas**: `AStatisticalProcessor` con template method pattern
- **Enums**: `ProcessingMode`, `StatisticType` con métodos abstractos
- **Excepciones**: `CSVProcessingException` personalizada
- **Genéricos**: `IWorkerTask<T>`, `List<CSVRecord>`

### ✅ Concurrencia y Hilos
- **Thread Pool**: Basado en `E5_Manager.java`
- **Worker Threads**: `StatisticsWorker` basado en `E5_Worker.java`
- **Sincronización**: `synchronized` methods como `E1_CuentaProtegidaMonitor.java`
- **Thread Safety**: Uso de `volatile` y `Object locks`

### ✅ Manejo de Archivos I/O
- **Lectura Eficiente**: Basada en `Archivo.java` e `IArchivo.java`
- **Iterator Pattern**: Para archivos grandes sin cargar todo en memoria
- **Buffer Management**: Configuración de buffer size
- **Validación**: Métodos default para validar archivos

## Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                    ARQUITECTURA MVC                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  MODEL                 CONTROLLER              VIEW         │
│  ┌─────────────┐      ┌─────────────┐      ┌─────────────┐  │
│  │ CSVRecord   │      │CSVProcessor │      │ ReportView  │  │
│  │ Statistics  │ ←→   │Controller   │ ←→   │             │  │
│  │ Performance │      │             │      │             │  │
│  └─────────────┘      └─────────────┘      └─────────────┘  │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│                    SERVICES LAYER                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐      ┌─────────────┐      ┌─────────────┐  │
│  │ICSVProcessor│      │AStatistical │      │ProcessMode  │  │
│  │             │      │Processor    │      │(enum)       │  │
│  │ +readCSV()  │      │             │      │             │  │
│  │ +validate() │      │ +process()  │      │ SEQUENTIAL  │  │
│  └─────────────┘      └─────────────┘      │ CONCURRENT  │  │
│         ▲                       ▲          └─────────────┘  │
│         │                       │                          │
│  ┌─────────────┐      ┌─────────────┐                      │
│  │CSVReaderImpl│      │Sequential/  │                      │
│  │             │      │Concurrent   │                      │
│  │             │      │Processor    │                      │
│  └─────────────┘      └─────────────┘                      │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│                   THREADING LAYER                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐      ┌─────────────┐      ┌─────────────┐  │
│  │IWorkerTask<T│      │Statistics   │      │Thread       │  │
│  │             │      │Worker       │      │Management   │  │
│  │ +execute()  │      │             │      │             │  │
│  │ +getResult()│      │ implements  │      │ Pool Mgmt   │  │
│  └─────────────┘      │ Runnable    │      │ Sync        │  │
│                       └─────────────┘      └─────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Estructura de Archivos

```
src/
├── model/                          # Entidades y modelos de datos
│   ├── CSVRecord.java             # Registro CSV con constructores sobrecargados
│   ├── StatisticsSummary.java     # Estadísticas thread-safe
│   └── PerformanceReport.java     # Métricas de rendimiento
├── view/                          # Capa de presentación
│   └── ReportView.java           # Vista para reportes y resultados
├── controller/                    # Controladores MVC
│   └── CSVProcessorController.java # Controlador principal
├── service/                       # Servicios de negocio
│   ├── ICSVProcessor.java        # Interfaz principal (como IArchivo)
│   ├── CSVReaderImpl.java        # Implementación lectura CSV
│   ├── AStatisticalProcessor.java # Clase abstracta (como E5_Manager)
│   ├── SequentialProcessor.java  # Procesador secuencial
│   └── ConcurrentProcessor.java  # Procesador concurrente
├── threading/                     # Capa de concurrencia
│   ├── IWorkerTask.java          # Interfaz para tareas
│   └── StatisticsWorker.java     # Worker thread (como E5_Worker)
├── enums/                         # Enumeraciones
│   ├── ProcessingMode.java       # Modos de procesamiento (como HeroesPatria)
│   └── StatisticType.java        # Tipos de estadísticas
├── exceptions/                    # Excepciones personalizadas
│   └── CSVProcessingException.java # Excepción específica CSV
└── Main.java                      # Clase principal
```

## Mapeo con Archivos del Curso

| Concepto | Archivo del Curso | Implementación en Proyecto |
|----------|-------------------|----------------------------|
| **Interfaces con default** | `IArchivo.java` | `ICSVProcessor.java` |
| **Clases concretas** | `Archivo.java` | `CSVReaderImpl.java` |
| **Manager/Worker** | `E5_Manager.java`, `E5_Worker.java` | `ConcurrentProcessor.java`, `StatisticsWorker.java` |
| **Sincronización** | `E1_CuentaProtegidaMonitor.java` | `StatisticsSummary.java` |
| **Enums complejos** | `HeroesPatria.java` | `ProcessingMode.java` |
| **Interface simple** | `E1_ICuentaBancaria.java` | `IWorkerTask.java` |

## Compilación y Ejecución

### Compilar
```bash
chmod +x compile.sh
./compile.sh
```

### Ejecutar
```bash
# Ejemplo básico (archivo debe estar en data/)
java -cp build Main itineraries.csv

# Especificando columnas numéricas
java -cp build Main ventas.csv precio cantidad descuento

# Para archivos grandes (82M+ registros)
java -Xmx8g -Xms4g -XX:+UseG1GC -cp build Main archivo_grande.csv col1 col2

# Con delimitador personalizado
java -cp build Main datos.tsv --delimiter="\t" precio cantidad

# NOTA: Todos los archivos CSV deben estar en la carpeta 'data/'
```

## Funcionalidades Implementadas

### 🔄 Procesamiento Dual
- **Secuencial**: Un hilo, mínimo uso de memoria
- **Concurrente**: Múltiples hilos, máximo rendimiento
- **Comparación automática**: Speedup, eficiencia, throughput

### 📊 Estadísticas Descriptivas Completas
- Media, mediana, desviación estándar
- Valores mínimo y máximo
- Suma total y varianza
- Conteo de registros válidos

### 🚀 Optimizaciones para Grandes Volúmenes
- **Iterator Pattern**: Evita cargar 82M registros en memoria
- **Chunk Processing**: División inteligente de trabajo
- **Thread Pool**: Gestión eficiente de hilos
- **Memory Management**: Configuración de heap y GC

### 📈 Reportería Avanzada
- Métricas de rendimiento detalladas
- Análisis automático de resultados
- Recomendaciones de optimización
- Preparación para regresión lineal

## Diagrama de Flujo del Sistema

### Flujo Principal

```
                    ┌─────────────────┐
                    │   INICIO        │
                    │   Main.java     │
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │ Validar Args    │
                    │ Leer desde data/│
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │ Crear Controller│
                    │ CSVProcessor    │
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │ Leer Headers    │
                    │ Detectar Cols   │
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │ PROCESAMIENTO   │
                    │ DUAL            │
                    └─────┬───────────┘
                          │
              ┌───────────┴───────────┐
              │                       │
    ┌─────────▼───────┐     ┌─────────▼───────┐
    │ SECUENCIAL      │     │ CONCURRENTE     │
    │ 1 Thread        │     │ N Threads       │
    └─────────┬───────┘     └─────────┬───────┘
              │                       │
              └───────────┬───────────┘
                          │
                ┌─────────▼───────┐
                │ Comparar        │
                │ Rendimiento     │
                │ Mostrar Report  │
                └─────────────────┘
```

### Procesamiento Concurrente Detallado

```
┌─────────────────────────────────────────────────────────────────┐
│                 PROCESAMIENTO CONCURRENTE                       │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │ data/       │    │ Dividir en  │    │ Thread Pool │         │
│  │ archivo.csv │───▶│ Chunks      │───▶│ Manager     │         │
│  │ 82M records │    │ Inteligente │    │ (N cores)   │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│                            │                    │               │
│                     ┌─────────────┐    ┌─────────────┐         │
│                     │ Chunk 1     │    │ Worker 1    │         │
│                     │ 10M records │───▶│ Thread      │         │
│                     └─────────────┘    └─────────────┘         │
│                            │                    │               │
│                     ┌─────────────┐    ┌─────────────┐         │
│                     │ Chunk N     │    │ Worker N    │         │
│                     │ 10M records │───▶│ Thread      │         │
│                     └─────────────┘    └─────────────┘         │
│                                                 │               │
│                                        ┌─────────────┐         │
│                                        │ Sincronizar │         │
│                                        │ Combinar    │         │
│                                        └─────────────┘         │
└─────────────────────────────────────────────────────────────────┘
```

### Algoritmo de Welford (Estadísticas Online)

```
┌─────────────────────────────────────────────────────────────────┐
│              ALGORITMO DE WELFORD (ONLINE VARIANCE)            │
├─────────────────────────────────────────────────────────────────┤
│  Para cada valor nuevo:                                         │
│                                                                 │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │ count++     │───▶│ delta =     │───▶│ mean +=     │         │
│  │             │    │ value-mean  │    │ delta/count │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│         │                   │                   │               │
│         ▼                   ▼                   ▼               │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │ sum +=      │    │ delta2 =    │    │ m2 +=       │         │
│  │ value       │    │ value-mean  │    │ delta*delta2│         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│         │                   │                   │               │
│         ▼                   ▼                   ▼               │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │ min/max     │    │ variance =  │    │ stddev =    │         │
│  │ tracking    │    │ m2/(count-1)│    │ sqrt(var)   │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
└─────────────────────────────────────────────────────────────────┘
```

## Estructura del Proyecto

```
PA-java8/
├── data/                           # 📁 Archivos CSV aquí
│   └── itineraries.csv             # Archivo de ejemplo
├── src/                           # Código fuente
│   ├── model/                      # Entidades MVC
│   │   ├── CSVRecord.java         # Registro con constructores sobrecargados
│   │   ├── StatisticsSummary.java # Estadísticas thread-safe
│   │   └── PerformanceReport.java # Métricas de rendimiento
│   ├── view/                      # Capa de presentación
│   │   └── ReportView.java       # Vista para reportes
│   ├── controller/                # Controladores MVC
│   │   └── CSVProcessorController.java # Controlador principal
│   ├── service/                   # Servicios de negocio
│   │   ├── ICSVProcessor.java    # Interfaz principal
│   │   ├── CSVReaderImpl.java    # Implementación lectura
│   │   ├── AStatisticalProcessor.java # Clase abstracta
│   │   ├── SequentialProcessor.java # Procesador secuencial
│   │   └── ConcurrentProcessor.java # Procesador concurrente
│   ├── threading/                 # Capa de concurrencia
│   │   ├── IWorkerTask.java      # Interfaz para tareas
│   │   └── StatisticsWorker.java # Worker thread
│   ├── enums/                     # Enumeraciones
│   │   ├── ProcessingMode.java   # Modos de procesamiento
│   │   └── StatisticType.java    # Tipos de estadísticas
│   ├── exceptions/                # Excepciones personalizadas
│   │   └── CSVProcessingException.java
│   └── Main.java                  # Clase principal
├── build/                         # Clases compiladas
├── compile.sh                     # Script de compilación
└── README.md                      # Esta documentación
```

## Ejemplo de Salida

```
=========================================
PROCESADOR CSV - ARQUITECTURA MVC
=========================================
Java Version: 1.8.0_XXX
Procesadores disponibles: 8
Memoria máxima JVM: 4096 MB
=========================================

CONFIGURACIÓN:
  Archivo: data/itineraries.csv
  Directorio: data/
  Columnas: Detección automática

========================================
COLUMNAS DETECTADAS EN EL ARCHIVO CSV
========================================
Total de columnas: 5
----------------------------------------
  1. id
  2. precio
  3. cantidad
  4. descuento
  5. categoria

========================================
ESTADÍSTICAS - MODO SECUENCIAL
========================================
Configuración: Modo: Procesamiento Secuencial | Hilos: 1 | Procesa registros uno por uno
Estrategia: Optimizado para uso mínimo de memoria
----------------------------------------
Total de registros procesados: 82,000,000
----------------------------------------
Columna: precio
  Registros válidos    : 82,000,000
  Media Aritmética     : 125.45
  Valor mínimo         : 10.00
  Valor máximo         : 999.99
  Suma total           : 10,286,900,000.00
  Desviación Estándar  : 287.23
  Varianza             : 82,501.15

========================================
REPORTE DE RENDIMIENTO COMPARATIVO
========================Registros procesados     : 82,000,000 registros
Hilos utilizados         : 8 hilos
Memoria utilizada        : 2,048 MB
----------------------------------------
Tiempo secuencial        : 45,230 ms
Tiempo concurrente       : 12,450 ms
----------------------------------------
Speedup                  : 3.63x
Eficiencia               : 45.38%
----------------------------------------
Throughput secuencial    : 1,813,542 registros/seg
Throughput concurrente   : 6,585,140 registros/seg
----------------------------------------
ANÁLISIS DE RESULTADOS:
✓ EXCELENTE: La concurrencia mejoró significativamente el rendimiento
⚠ Eficiencia moderada en el uso de hilos

RECOMENDACIONES:
- Para datasets grandes, considerar procesamiento por lotes
- Archivo leído desde: data/itineraries.csv
========================================
```

## Sincronización Thread-Safe

```
┌─────────────────────────────────────────────────────────────────┐
│                  SINCRONIZACIÓN THREAD-SAFE                    │
├─────────────────────────────────────────────────────────────────┤
│  Multiple Workers                    Statistics Summary         │
│  ┌─────────────┐                   ┌─────────────────────┐     │
│  │ Worker 1    │──┐                │ synchronized        │     │
│  │ Thread      │  │                │ addColumnValue()    │     │
│  └─────────────┘  │                └─────────────────────┘     │
│                   │                          │                 │
│  ┌─────────────┐  │    ┌─────────────┐     │                 │
│  │ Worker 2    │──┼───▶│ Object      │     │                 │
│  │ Thread      │  │    │ Lock        │◄────┘                 │
│  └─────────────┘  │    └─────────────┘                       │
│                   │           │                               │
│  ┌─────────────┐  │           ▼                               │
│  │ Worker N    │──┘    ┌─────────────┐                       │
│  │ Thread      │       │ Column      │                       │
│  └─────────────┘       │ Statistics  │                       │
│                        │ Update      │                       │
│                        └─────────────┘                       │
└─────────────────────────────────────────────────────────────────┘
```

## Manejo de Memoria para 82M Registros

```
┌─────────────────────────────────────────────────────────────────┐
│                   MANEJO DE MEMORIA EFICIENTE                  │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │ data/       │    │ Iterator    │    │ Streaming   │         │
│  │ archivo.csv │───▶│ Pattern     │───▶│ Processing  │         │
│  │ 82M Records │    │ (No load    │    │ (Chunk by   │         │
│  │ ~10GB       │    │ all in RAM) │    │ Chunk)      │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│                            │                    │               │
│                            ▼                    ▼               │
│                     ┌─────────────┐    ┌─────────────┐         │
│                     │ Buffer 8KB  │    │ Process &   │         │
│                     │ Configurable│    │ Release     │         │
│                     └─────────────┘    └─────────────┘         │
│                            │                    │               │
│                            ▼                    ▼               │
│                     ┌─────────────┐    ┌─────────────┐         │
│                     │ Parse Line  │    │ Statistics  │         │
│                     │ to Record   │    │ Only (~1MB) │         │
│                     └─────────────┘    └─────────────┘         │
└─────────────────────────────────────────────────────────────────┘
```

## Preparación para Regresión Lineal

El sistema genera estadísticas descriptivas que sirven como base para:

1. **Análisis de Correlación**: Entre variables numéricas
2. **Detección de Outliers**: Usando desviación estándar
3. **Normalización**: Basada en min/max y media
4. **Selección de Features**: Según varianza y distribución
5. **Modelo Predictivo**: Implementación de regresión lineal múltiple

## Configuración Recomendada para 82M Registros

```bash
# Configuración JVM optimizada
# IMPORTANTE: El archivo debe estar en data/
java -Xmx8g \
     -Xms4g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+UnlockExperimentalVMOptions \
     -XX:+UseStringDeduplication \
     -cp build Main archivo_82M.csv columna1 columna2

# Estructura requerida:
# data/archivo_82M.csv ← Archivo aquí
```

## Flujo de Decisión de Procesamiento

```
                    ┌─────────────────┐
                    │ Leer desde      │
                    │ data/archivo    │
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │ Analizar Dataset│
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │ Tamaño < 10K?   │
                    └─────┬───────┬───┘
                          │ Sí    │ No
                          ▼       ▼
                ┌─────────────┐ ┌─────────────┐
                │ Usar        │ │ Usar        │
                │ SEQUENTIAL  │ │ CONCURRENT  │
                └─────────────┘ └─────────────┘
                          │       │
                          │       ▼
                          │ ┌─────────────┐
                          │ │ Cores > 4?  │
                          │ └─────┬───┬───┘
                          │       │Sí │No
                          │       ▼   ▼
                          │ ┌─────────────┐
                          │ │ Usar N      │
                          │ │ Threads     │
                          │ └─────────────┘
                          │       │
                          └───────┼───────┐
                                  │       │
                        ┌─────────▼───────▼─┐
                        │ Ejecutar Ambos    │
                        │ Comparar          │
                        │ Rendimiento       │
                        └───────────────────┘
```

## Extensibilidad

El sistema está diseñado para ser fácilmente extensible:

- **Nuevos Procesadores**: Extender `AStatisticalProcessor`
- **Nuevas Estadísticas**: Agregar a `StatisticType` enum
- **Nuevos Formatos**: Implementar `ICSVProcessor`
- **Nuevas Vistas**: Extender `ReportView`
- **Algoritmos ML**: Usar estadísticas como input

Este proyecto demuestra la aplicación práctica de todos los conceptos avanzados de Java 8 en un sistema real de procesamiento de big data con arquitectura empresarial.