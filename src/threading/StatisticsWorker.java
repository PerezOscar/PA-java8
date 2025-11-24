package threading;

import model.CSVRecord;
import model.StatisticsSummary;
import java.util.List;

/**
 * Worker thread para procesamiento estadístico
 * Basado en el patrón de E5_Worker.java
 */
public class StatisticsWorker implements Runnable, IWorkerTask<StatisticsSummary> {
    
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
    public void run() {
        if (cancelled) return;
        
        try {
            result = execute();
            completed = true;
        } catch (Exception e) {
            System.err.println("Error en worker " + workerId + ": " + e.getMessage());
            result = new StatisticsSummary(); // Resultado vacío en caso de error
            completed = true;
        }
    }
    
    @Override
    public StatisticsSummary execute() {
        StatisticsSummary summary = new StatisticsSummary();
        
        for (CSVRecord record : dataChunk) {
            if (cancelled) break;
            
            summary.incrementRecordCount();
            
            for (String column : numericColumns) {
                Double value = record.getNumericValue(column);
                if (value != null) {
                    summary.addColumnValue(column, value);
                }
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