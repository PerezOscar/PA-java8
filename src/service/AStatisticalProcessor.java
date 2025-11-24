package service;

import model.CSVRecord;
import model.StatisticsSummary;
import java.util.List;

/**
 * Clase abstracta para procesadores estadísticos
 * Basada en el patrón de E5_Manager.java
 */
public abstract class AStatisticalProcessor {
    
    protected int threadCount;
    protected Thread[] workerPool;
    protected volatile boolean isProcessing;
    
    // Constructor por defecto
    public AStatisticalProcessor() {
        this.threadCount = Runtime.getRuntime().availableProcessors();
        this.isProcessing = false;
    }
    
    // Constructor con número de hilos
    public AStatisticalProcessor(int threadCount) {
        this.threadCount = Math.max(1, threadCount);
        this.isProcessing = false;
    }
    
    /**
     * Método abstracto principal para procesar datos
     */
    public abstract StatisticsSummary processData(List<CSVRecord> records, List<String> numericColumns);
    
    /**
     * Método abstracto para calcular estadísticas
     */
    protected abstract StatisticsSummary calculateStatistics(List<CSVRecord> chunk, List<String> columns);
    
    /**
     * Inicializa el pool de workers - similar a E5_Manager
     */
    protected void initializeWorkerPool() {
        this.workerPool = new Thread[threadCount];
        this.isProcessing = true;
    }
    
    /**
     * Espera a que terminen todos los workers - similar a E5_Manager.join()
     */
    protected void waitForCompletion() {
        if (workerPool != null) {
            for (int i = 0; i < threadCount; i++) {
                try {
                    if (workerPool[i] != null) {
                        workerPool[i].join();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        this.isProcessing = false;
    }
    
    /**
     * Combina resultados de múltiples summaries
     */
    protected StatisticsSummary mergeResults(List<StatisticsSummary> results) {
        StatisticsSummary merged = new StatisticsSummary();
        
        for (StatisticsSummary summary : results) {
            // Sumar registros totales
            for (long i = 0; i < summary.getTotalRecords(); i++) {
                merged.incrementRecordCount();
            }
            
            // Combinar estadísticas por columna (aproximación)
            for (String column : summary.getColumnStats().keySet()) {
                StatisticsSummary.ColumnStatistics stats = summary.getColumnStats().get(column);
                for (long i = 0; i < stats.getCount(); i++) {
                    merged.addColumnValue(column, stats.getMean());
                }
            }
        }
        
        return merged;
    }
    
    // Getters
    public int getThreadCount() {
        return threadCount;
    }
    
    public boolean isProcessing() {
        return isProcessing;
    }
}