package service;

import model.CSVRecord;
import model.StatisticsSummary;
import threading.StatisticsWorker;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Procesador concurrente optimizado con ThreadPoolExecutor
 */
public class ConcurrentProcessor extends AStatisticalProcessor {
    
    private final ExecutorService executor;
    private final int optimalThreads;
    
    public ConcurrentProcessor() {
        super();
        this.optimalThreads = Math.min(threadCount, Runtime.getRuntime().availableProcessors());
        this.executor = new ThreadPoolExecutor(
            optimalThreads, optimalThreads,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                private int counter = 0;
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "CSVWorker-" + (++counter));
                    t.setDaemon(false);
                    return t;
                }
            }
        );
    }
    
    public ConcurrentProcessor(int threadCount) {
        super(threadCount);
        this.optimalThreads = Math.min(threadCount, Runtime.getRuntime().availableProcessors());
        this.executor = new ThreadPoolExecutor(
            optimalThreads, optimalThreads,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                private int counter = 0;
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "CSVWorker-" + (++counter));
                    t.setDaemon(false);
                    return t;
                }
            }
        );
    }
    
    @Override
    public StatisticsSummary processData(List<CSVRecord> records, List<String> numericColumns) {
        if (records.isEmpty()) return new StatisticsSummary();
        
        // Calcular chunk size óptimo basado en tamaño de datos y threads
        int optimalChunkSize = Math.max(1000, records.size() / (optimalThreads * 4));
        List<Future<StatisticsSummary>> futures = new ArrayList<Future<StatisticsSummary>>();
        
        // Dividir en chunks optimizados
        for (int i = 0; i < records.size(); i += optimalChunkSize) {
            int end = Math.min(i + optimalChunkSize, records.size());
            List<CSVRecord> chunk = records.subList(i, end);
            
            StatisticsWorker worker = new StatisticsWorker(chunk, numericColumns, futures.size());
            futures.add(executor.submit(worker));
        }
        
        // Recopilar resultados usando Future
        List<StatisticsSummary> results = new ArrayList<StatisticsSummary>();
        for (Future<StatisticsSummary> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error en worker: " + e.getMessage());
                results.add(new StatisticsSummary());
            }
        }
        
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
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
    
    @Override
    public int getThreadCount() {
        return optimalThreads;
    }
}