package threading;

import model.CSVRecord;
import model.StatisticsSummary;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Worker optimizado que implementa Callable para mejor integración con ExecutorService
 */
public class StatisticsWorker implements Callable<StatisticsSummary>, IWorkerTask<StatisticsSummary> {
    
    private final List<CSVRecord> dataChunk;
    private final List<String> numericColumns;
    private final int workerId;
    private StatisticsSummary result;
    private volatile boolean completed;
    private volatile boolean cancelled;
    
    public StatisticsWorker(List<CSVRecord> dataChunk, List<String> numericColumns, int workerId) {
        this.dataChunk = dataChunk;
        this.numericColumns = numericColumns;
        this.workerId = workerId;
        this.completed = false;
        this.cancelled = false;
    }
    
    @Override
    public StatisticsSummary call() throws Exception {
        if (cancelled) return new StatisticsSummary();
        
        try {
            result = execute();
            completed = true;
            return result;
        } catch (Exception e) {
            System.err.println("Error en worker " + workerId + ": " + e.getMessage());
            result = new StatisticsSummary();
            completed = true;
            return result;
        }
    }
    
    public void run() {
        try {
            call();
        } catch (Exception e) {
            System.err.println("Error en worker " + workerId + ": " + e.getMessage());
        }
    }
    
    @Override
    public StatisticsSummary execute() {
        StatisticsSummary summary = new StatisticsSummary();
        int batchSize = Math.min(1000, dataChunk.size() / 10);
        
        for (int i = 0; i < dataChunk.size(); i++) {
            if (cancelled) break;
            
            CSVRecord record = dataChunk.get(i);
            summary.incrementRecordCount();
            
            // Procesar columnas numéricas en lote
            for (String column : numericColumns) {
                Double value = record.getNumericValue(column);
                if (value != null) {
                    summary.addColumnValue(column, value);
                }
            }
            
            // Yield control periodically para mejor concurrencia
            if (i % batchSize == 0 && i > 0) {
                Thread.yield();
            }
        }
        
        return summary;
    }
    
    @Override
    public StatisticsSummary getResult() {
        return result;
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public void cancel() {
        this.cancelled = true;
    }
    
    public int getWorkerId() {
        return workerId;
    }
    
    public int getChunkSize() {
        return dataChunk.size();
    }
}