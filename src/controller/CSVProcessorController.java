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
            
            // Cargar datos para procesamiento
            List<CSVRecord> allRecords = loadAllRecords(filePath);
            
            // Procesamiento secuencial
            long startTime = System.currentTimeMillis();
            StatisticsSummary sequentialSummary = sequentialProcessor.processData(allRecords, numericColumns);
            long sequentialTime = System.currentTimeMillis() - startTime;
            
            // Procesamiento concurrente
            startTime = System.currentTimeMillis();
            StatisticsSummary concurrentSummary = concurrentProcessor.processData(allRecords, numericColumns);
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
     * Carga todos los registros en memoria para procesamiento
     */
    private List<CSVRecord> loadAllRecords(String filePath) throws IOException {
        List<CSVRecord> records = new ArrayList<CSVRecord>();
        
        try {
            java.util.Iterator<CSVRecord> iterator = csvReader.getRecordIterator(filePath);
            while (iterator.hasNext()) {
                records.add(iterator.next());
            }
        } catch (IOException e) {
            throw new IOException("Error cargando registros: " + e.getMessage());
        }
        
        return records;
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