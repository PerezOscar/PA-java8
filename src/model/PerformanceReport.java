package model;

/**
 * Modelo para reportes de rendimiento
 * Incluye getters/setters y cálculos de métricas
 */
public class PerformanceReport {
    private final long sequentialTimeMs;
    private final long concurrentTimeMs;
    private final long recordsProcessed;
    private final int threadCount;
    private final long memoryUsedMB;
    
    // Constructor completo
    public PerformanceReport(long sequentialTimeMs, long concurrentTimeMs, 
                           long recordsProcessed, int threadCount, long memoryUsedMB) {
        this.sequentialTimeMs = sequentialTimeMs;
        this.concurrentTimeMs = concurrentTimeMs;
        this.recordsProcessed = recordsProcessed;
        this.threadCount = threadCount;
        this.memoryUsedMB = memoryUsedMB;
    }
    
    // Constructor sobrecargado sin memoria
    public PerformanceReport(long sequentialTimeMs, long concurrentTimeMs, 
                           long recordsProcessed, int threadCount) {
        this(sequentialTimeMs, concurrentTimeMs, recordsProcessed, threadCount, 0);
    }
    
    // Getters
    public long getSequentialTimeMs() { return sequentialTimeMs; }
    public long getConcurrentTimeMs() { return concurrentTimeMs; }
    public long getRecordsProcessed() { return recordsProcessed; }
    public int getThreadCount() { return threadCount; }
    public long getMemoryUsedMB() { return memoryUsedMB; }
    
    // Métricas calculadas
    public double getSpeedup() {
        return concurrentTimeMs > 0 ? (double) sequentialTimeMs / concurrentTimeMs : 0.0;
    }
    
    public double getEfficiency() {
        return threadCount > 0 ? getSpeedup() / threadCount : 0.0;
    }
    
    public double getSequentialThroughput() {
        return sequentialTimeMs > 0 ? (double) recordsProcessed / sequentialTimeMs * 1000 : 0.0;
    }
    
    public double getConcurrentThroughput() {
        return concurrentTimeMs > 0 ? (double) recordsProcessed / concurrentTimeMs * 1000 : 0.0;
    }
    
    @Override
    public String toString() {
        return String.format("PerformanceReport{records=%d, threads=%d, speedup=%.2fx, efficiency=%.2f%%}", 
                           recordsProcessed, threadCount, getSpeedup(), getEfficiency() * 100);
    }
}