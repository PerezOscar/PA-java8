package threading;

import model.StatisticsSummary;

/**
 * Interfaz para tareas de worker threads
 * Basada en el patrón de ICuentaBancaria.java
 */
public interface IWorkerTask<T> {
    
    // Constantes para configuración
    int DEFAULT_CHUNK_SIZE = 1000;
    long DEFAULT_TIMEOUT_MS = 30000;
    
    /**
     * Ejecuta la tarea del worker
     * @return resultado de la ejecución
     */
    T execute();
    
    /**
     * Obtiene el resultado de la tarea
     * @return resultado procesado
     */
    T getResult();
    
    /**
     * Verifica si la tarea está completada
     * @return true si está completada
     */
    boolean isCompleted();
    
    /**
     * Cancela la ejecución de la tarea
     */
    void cancel();
}