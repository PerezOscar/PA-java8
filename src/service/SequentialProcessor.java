package service;

import model.CSVRecord;
import model.StatisticsSummary;
import java.util.List;

/**
 * Procesador secuencial que extiende la clase abstracta
 */
public class SequentialProcessor extends AStatisticalProcessor {
    
    public SequentialProcessor() {
        super(1); // Solo un hilo para procesamiento secuencial
    }
    
    @Override
    public StatisticsSummary processData(List<CSVRecord> records, List<String> numericColumns) {
        StatisticsSummary summary = new StatisticsSummary();
        
        // Procesamiento secuencial simple
        for (CSVRecord record : records) {
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
    protected StatisticsSummary calculateStatistics(List<CSVRecord> chunk, List<String> columns) {
        return processData(chunk, columns);
    }
}