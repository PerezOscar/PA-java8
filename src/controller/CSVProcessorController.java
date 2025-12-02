package controller;

import model.*;
import service.*;
import view.ReportView;
import enums.ProcessingMode;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Controlador principal MVC para procesamiento CSV
 */
public class CSVProcessorController {
    
    private final ICSVProcessor csvReader;
    private final AStatisticalProcessor sequentialProcessor;
    private final AStatisticalProcessor concurrentProcessor;
    private final ReportView view;
    
    public CSVProcessorController() {
        this.csvReader = new CSVReaderImpl();
        this.sequentialProcessor = new SequentialProcessor();
        this.concurrentProcessor = new ConcurrentProcessor();
        this.view = new ReportView();
    }
    
    public CSVProcessorController(String delimiter) {
        this.csvReader = new CSVReaderImpl(delimiter);
        this.sequentialProcessor = new SequentialProcessor();
        this.concurrentProcessor = new ConcurrentProcessor();
        this.view = new ReportView();
    }
    
    /**
     * Procesa archivo CSV con comparación de rendimiento
     */
    public void processFile(String filePath, List<String> numericColumns) {
        try {
            // Mostrar información del archivo
            List<String> headers = csvReader.readHeaders(filePath);
            view.displayHeaders(headers);
            
            // Detectar columnas numéricas si no se especificaron
            if (numericColumns == null || numericColumns.isEmpty()) {
                numericColumns = detectNumericColumns(filePath, headers);
            }
            
            view.displayProcessingStart(filePath, numericColumns);
            
            // Procesamiento secuencial streaming
            long startTime = System.currentTimeMillis();
            StatisticsSummary sequentialSummary = processSequentialStreaming(filePath, numericColumns);
            long sequentialTime = System.currentTimeMillis() - startTime;
            
            // Procesamiento concurrente por chunks
            startTime = System.currentTimeMillis();
            StatisticsSummary concurrentSummary = processConcurrentChunks(filePath, numericColumns);
            long concurrentTime = System.currentTimeMillis() - startTime;
            
            // Mostrar resultados
            view.displayStatistics("SECUENCIAL", sequentialSummary, ProcessingMode.SEQUENTIAL);
            view.displayStatistics("CONCURRENTE", concurrentSummary, ProcessingMode.CONCURRENT);
            
            // Reporte de rendimiento
            PerformanceReport report = new PerformanceReport(
                sequentialTime, concurrentTime, 
                sequentialSummary.getTotalRecords(), 
                concurrentProcessor.getThreadCount(),
                getMemoryUsage()
            );
            
            view.displayPerformanceReport(report);
            
        } catch (IOException e) {
            view.displayError("Error procesando archivo: " + e.getMessage());
        } catch (Exception e) {
            view.displayError("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Procesamiento secuencial streaming sin cargar todo en memoria
     */
    private StatisticsSummary processSequentialStreaming(String filePath, List<String> numericColumns) throws IOException {
        StatisticsSummary summary = new StatisticsSummary();
        java.util.Iterator<CSVRecord> iterator = csvReader.getRecordIterator(filePath);
        
        long processedRecords = 0;
        long startTime = System.currentTimeMillis();
        
        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            summary.incrementRecordCount();
            processedRecords++;
            
            for (String column : numericColumns) {
                Double value = record.getNumericValue(column);
                if (value != null) {
                    summary.addColumnValue(column, value);
                }
            }
            
            // Progreso cada 2 millones de registros
            if (processedRecords % 2000000 == 0) {
                long elapsed = System.currentTimeMillis() - startTime;
                double rate = processedRecords / (elapsed / 1000.0);
                System.out.printf("[SECUENCIAL] %,d registros | %,d ms | %.0f reg/seg\n", 
                                processedRecords, elapsed, rate);
            }
        }
        
        return summary;
    }
    
    /**
     * Procesamiento concurrente por chunks pequeños
     */
    private StatisticsSummary processConcurrentChunks(String filePath, List<String> numericColumns) throws IOException {
        StatisticsSummary finalSummary = new StatisticsSummary();
        java.util.Iterator<CSVRecord> iterator = csvReader.getRecordIterator(filePath);
        
        final int CHUNK_SIZE = 50000; // 50K registros por chunk
        List<CSVRecord> chunk = new ArrayList<CSVRecord>();
        
        long processedRecords = 0;
        long startTime = System.currentTimeMillis();
        
        while (iterator.hasNext()) {
            chunk.add(iterator.next());
            
            if (chunk.size() >= CHUNK_SIZE) {
                StatisticsSummary chunkSummary = concurrentProcessor.processData(chunk, numericColumns);
                mergeResults(finalSummary, chunkSummary);
                
                processedRecords += chunk.size();
                chunk.clear();
                
                // Progreso cada 2 millones de registros
                if (processedRecords % 2000000 == 0) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    double rate = processedRecords / (elapsed / 1000.0);
                    System.out.printf("[CONCURRENTE] %,d registros | %,d ms | %.0f reg/seg\n", 
                                    processedRecords, elapsed, rate);
                }
            }
        }
        
        // Procesar último chunk
        if (!chunk.isEmpty()) {
            StatisticsSummary chunkSummary = concurrentProcessor.processData(chunk, numericColumns);
            mergeResults(finalSummary, chunkSummary);
        }
        
        return finalSummary;
    }
    
    /**
     * Combina resultados de chunks
     */
    private void mergeResults(StatisticsSummary target, StatisticsSummary source) {
        for (long i = 0; i < source.getTotalRecords(); i++) {
            target.incrementRecordCount();
        }
        
        for (String column : source.getColumnStats().keySet()) {
            StatisticsSummary.ColumnStatistics stats = source.getColumnStats().get(column);
            for (long i = 0; i < stats.getCount(); i++) {
                target.addColumnValue(column, stats.getMean());
            }
        }
    }
    
    /**
     * Detecta columnas numéricas automáticamente
     */
    private List<String> detectNumericColumns(String filePath, List<String> headers) {
        List<String> numericColumns = new ArrayList<String>();
        
        try {
            java.util.Iterator<CSVRecord> iterator = csvReader.getRecordIterator(filePath);
            
            // Analizar primeros 100 registros para detectar tipos
            int sampleCount = 0;
            while (iterator.hasNext() && sampleCount < 100) {
                CSVRecord record = iterator.next();
                
                for (String header : headers) {
                    if (record.getNumericValue(header) != null && !numericColumns.contains(header)) {
                        numericColumns.add(header);
                    }
                }
                sampleCount++;
            }
        } catch (IOException e) {
            // Si hay error, asumir que todas las columnas son numéricas
            numericColumns.addAll(headers);
        }
        
        return numericColumns;
    }
    
    /**
     * Obtiene uso de memoria actual
     */
    private long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024); // MB
    }
    
    // Getters para testing
    public ICSVProcessor getCsvReader() { return csvReader; }
    public AStatisticalProcessor getSequentialProcessor() { return sequentialProcessor; }
    public AStatisticalProcessor getConcurrentProcessor() { return concurrentProcessor; }
}