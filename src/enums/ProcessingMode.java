package enums;

/**
 * Enum para modos de procesamiento
 * Basado en el patrón de HeroesPatria.java con constructores sobrecargados
 */
public enum ProcessingMode {
    
    SEQUENTIAL("Procesamiento Secuencial", 1, "Procesa registros uno por uno") {
        @Override
        public String getOptimizationStrategy() {
            return "Optimizado para uso mínimo de memoria";
        }
    },
    
    CONCURRENT("Procesamiento Concurrente", Runtime.getRuntime().availableProcessors(), "Usa múltiples hilos") {
        @Override
        public String getOptimizationStrategy() {
            return "Optimizado para máximo rendimiento con " + threadCount + " hilos";
        }
    },
    
    PARALLEL("Procesamiento Paralelo", Runtime.getRuntime().availableProcessors() * 2, "Paralelismo avanzado") {
        @Override
        public String getOptimizationStrategy() {
            return "Optimizado para datasets masivos con " + threadCount + " workers";
        }
    };
    
    // Atributos como en HeroesPatria
    private String description;
    protected int threadCount;
    private String details;
    
    // Constructor por defecto
    ProcessingMode() {
        this.description = "Modo de procesamiento estándar";
        this.threadCount = 1;
        this.details = "Sin detalles específicos";
    }
    
    // Constructor con descripción
    ProcessingMode(String description) {
        this.description = description;
        this.threadCount = 1;
        this.details = "Sin detalles específicos";
    }
    
    // Constructor con descripción y hilos
    ProcessingMode(String description, int threadCount) {
        this.description = description;
        this.threadCount = threadCount;
        this.details = "Sin detalles específicos";
    }
    
    // Constructor completo como en HeroesPatria
    ProcessingMode(String description, int threadCount, String details) {
        this.description = description;
        this.threadCount = threadCount;
        this.details = details;
    }
    
    // Método abstracto que debe implementar cada enum
    public abstract String getOptimizationStrategy();
    
    // Getters como en HeroesPatria
    public String getDescription() {
        return this.description;
    }
    
    public int getThreadCount() {
        return this.threadCount;
    }
    
    public String getDetails() {
        return this.details;
    }
    
    // Método para obtener configuración recomendada
    public String getRecommendedConfig() {
        return String.format("Modo: %s | Hilos: %d | %s", 
                           description, threadCount, details);
    }
}