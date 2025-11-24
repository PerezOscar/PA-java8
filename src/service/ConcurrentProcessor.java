package service;

import model.CSVRecord;
import model.StatisticsSummary;
import threading.StatisticsWorker;
import java.util.ArrayList;
import java.util.List;

/**
 * Procesador concurrente basado en el patrón de E5_Manager.java
 */
public class ConcurrentProcessor extends AStatisticalProcessor {
    
    private List<StatisticsWorker> workers;
    private List<StatisticsSummary> results;
    
    public ConcurrentProcessor() {
        super();
        this.workers = new ArrayList<StatisticsWorker>();
        this.results = new ArrayList<StatisticsSummary>();
    }
    
    public ConcurrentProcessor(int threadCount) {
        super(threadCount);
        this.workers = new ArrayList<StatisticsWorker>();
        this.results = new ArrayList<StatisticsSummary>();
    }
    
    @Override
    public StatisticsSummary processData(List<CSVRecord> records, List<String> numericColumns) {
        // Dividir datos en chunks como en E5_Manager
        int chunkSize = Math.max(1, records.size() / threadCount);
        
        initializeWorkerPool();
        
        // Crear y lanzar workers
        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = (i == threadCount - 1) ? records.size() : (i + 1) * chunkSize;
            
            if (start < records.size()) {
                List<CSVRecord> chunk = records.subList(start, end);
                StatisticsWorker worker = new StatisticsWorker(chunk, numericColumns, i);
                workers.add(worker);
                
                workerPool[i] = new Thread(worker);
                workerPool[i].start();
            }
        }
        
        // Esperar a que terminen todos los workers - como E5_Manager.join()
        waitForCompletion();
        
        // Recopilar resultados
        results.clear();
        for (StatisticsWorker worker : workers) {
            results.add(worker.getResult());
        }
        
        // Combinar resultados
        return mergeResults(results);
    }
    
    @Override
    protected StatisticsSummary calculateStatistics(List<CSVRecord> chunk, List<String> columns) {
        StatisticsSummary summary = new StatisticsSummary();
        
        for (CSVRecord record : chunk) {
            summary.incrementRecordCount();
            
            for (String column : columns) {
                Double value = record.getNumericValue(column);
                if (value != null) {
                    summary.addColumnValue(column, value);
                }
            }
        }
        
        return summary;
    }
    
    public List<StatisticsSummary> getWorkerResults() {
        return new ArrayList<StatisticsSummary>(results);
    }
}