package view;

import model.*;
import enums.ProcessingMode;
import enums.StatisticType;
import java.util.List;
import java.util.Map;

/**
 * Vista para mostrar reportes y resultados
 * Basada en los métodos de salida de IArchivo.java
 */
public class ReportView {
    
    private static final String SEPARATOR = "========================================";
    private static final String SUBSEPARATOR = "----------------------------------------";
    
    /**
     * Muestra headers detectados del CSV
     */
    public void displayHeaders(List<String> headers) {
        System.out.println(SEPARATOR);
        System.out.println("COLUMNAS DETECTADAS EN EL ARCHIVO CSV");
        System.out.println(SEPARATOR);
        System.out.println("Total de columnas: " + headers.size());
        System.out.println(SUBSEPARATOR);
        
        for (int i = 0; i < headers.size(); i++) {
            System.out.printf("%3d. %s\n", (i + 1), headers.get(i));
        }
        System.out.println();
    }
    
    /**
     * Muestra inicio del procesamiento
     */
    public void displayProcessingStart(String filePath, List<String> numericColumns) {
        System.out.println(SEPARATOR);
        System.out.println("INICIANDO PROCESAMIENTO CSV");
        System.out.println(SEPARATOR);
        System.out.println("Archivo: " + filePath);
        System.out.println("Columnas numéricas a procesar: " + numericColumns.size());
        
        for (String column : numericColumns) {
            System.out.println("  - " + column);
        }
        System.out.println();
    }
    
    /**
     * Muestra estadísticas procesadas
     */
    public void displayStatistics(String mode, StatisticsSummary summary, ProcessingMode processingMode) {
        System.out.println(SEPARATOR);
        System.out.println("ESTADÍSTICAS - MODO " + mode);
        System.out.println(SEPARATOR);
        System.out.println("Configuración: " + processingMode.getRecommendedConfig());
        System.out.println("Estrategia: " + processingMode.getOptimizationStrategy());
        System.out.println(SUBSEPARATOR);
        System.out.printf("Total de registros procesados: %,d\n", summary.getTotalRecords());
        System.out.println(SUBSEPARATOR);
        
        Map<String, StatisticsSummary.ColumnStatistics> columnStats = summary.getColumnStats();
        
        if (columnStats.isEmpty()) {
            System.out.println("No se encontraron datos numéricos para procesar.");
        } else {
            for (Map.Entry<String, StatisticsSummary.ColumnStatistics> entry : columnStats.entrySet()) {
                String column = entry.getKey();
                StatisticsSummary.ColumnStatistics stats = entry.getValue();
                
                System.out.println("Columna: " + column);
                System.out.printf("  %-20s: %,d\n", "Registros válidos", stats.getCount());
                System.out.printf("  %-20s: %,.2f\n", StatisticType.MEAN.getDisplayName(), stats.getMean());
                System.out.printf("  %-20s: %,.2f\n", "Valor mínimo", stats.getMin());
                System.out.printf("  %-20s: %,.2f\n", "Valor máximo", stats.getMax());
                System.out.printf("  %-20s: %,.2f\n", "Suma total", stats.getSum());
                System.out.printf("  %-20s: %,.2f\n", StatisticType.STANDARD_DEVIATION.getDisplayName(), stats.getStandardDeviation());
                System.out.printf("  %-20s: %,.2f\n", "Varianza", stats.getVariance());
                System.out.println();
            }
        }
    }
    
    /**
     * Muestra reporte de rendimiento comparativo
     */
    public void displayPerformanceReport(PerformanceReport report) {
        System.out.println(SEPARATOR);
        System.out.println("REPORTE DE RENDIMIENTO COMPARATIVO");
        System.out.println(SEPARATOR);
        
        // Información general
        System.out.printf("%-25s: %,d registros\n", "Registros procesados", report.getRecordsProcessed());
        System.out.printf("%-25s: %d hilos\n", "Hilos utilizados", report.getThreadCount());
        System.out.printf("%-25s: %,d MB\n", "Memoria utilizada", report.getMemoryUsedMB());
        
        System.out.println(SUBSEPARATOR);
        
        // Tiempos de ejecución
        System.out.printf("%-25s: %,d ms\n", "Tiempo secuencial", report.getSequentialTimeMs());
        System.out.printf("%-25s: %,d ms\n", "Tiempo concurrente", report.getConcurrentTimeMs());
        
        System.out.println(SUBSEPARATOR);
        
        // Métricas de rendimiento
        System.out.printf("%-25s: %.2fx\n", "Speedup", report.getSpeedup());
        System.out.printf("%-25s: %.2f%%\n", "Eficiencia", report.getEfficiency() * 100);
        
        System.out.println(SUBSEPARATOR);
        
        // Throughput
        System.out.printf("%-25s: %,.0f registros/seg\n", "Throughput secuencial", report.getSequentialThroughput());
        System.out.printf("%-25s: %,.0f registros/seg\n", "Throughput concurrente", report.getConcurrentThroughput());
        
        System.out.println(SUBSEPARATOR);
        
        // Análisis de resultados
        displayPerformanceAnalysis(report);
        
        System.out.println(SEPARATOR);
    }
    
    /**
     * Analiza y muestra conclusiones del rendimiento
     */
    private void displayPerformanceAnalysis(PerformanceReport report) {
        System.out.println("ANÁLISIS DE RESULTADOS:");
        
        double speedup = report.getSpeedup();
        double efficiency = report.getEfficiency();
        
        if (speedup > 1.5) {
            System.out.println("✓ EXCELENTE: La concurrencia mejoró significativamente el rendimiento");
        } else if (speedup > 1.1) {
            System.out.println("✓ BUENO: La concurrencia mejoró moderadamente el rendimiento");
        } else if (speedup > 0.9) {
            System.out.println("⚠ NEUTRO: La concurrencia no afectó significativamente el rendimiento");
        } else {
            System.out.println("✗ MALO: La concurrencia empeoró el rendimiento");
        }
        
        if (efficiency > 0.7) {
            System.out.println("✓ Alta eficiencia en el uso de hilos");
        } else if (efficiency > 0.4) {
            System.out.println("⚠ Eficiencia moderada en el uso de hilos");
        } else {
            System.out.println("✗ Baja eficiencia - considerar reducir número de hilos");
        }
        
        // Recomendaciones
        System.out.println("\nRECOMENDACIONES:");
        if (report.getRecordsProcessed() < 10000) {
            System.out.println("- Para datasets pequeños, el procesamiento secuencial puede ser más eficiente");
        } else if (report.getRecordsProcessed() > 1000000) {
            System.out.println("- Para datasets grandes, considerar procesamiento por lotes");
        }
        
        if (efficiency < 0.5) {
            System.out.println("- Reducir número de hilos para mejorar eficiencia");
        }
    }
    
    /**
     * Muestra errores
     */
    public void displayError(String message) {
        System.err.println(SEPARATOR);
        System.err.println("ERROR EN EL PROCESAMIENTO");
        System.err.println(SEPARATOR);
        System.err.println(message);
        System.err.println(SEPARATOR);
    }
    
    /**
     * Muestra información de preparación para regresión lineal
     */
    public void displayRegressionPreparation(StatisticsSummary summary) {
        System.out.println(SEPARATOR);
        System.out.println("PREPARACIÓN PARA REGRESIÓN LINEAL MÚLTIPLE");
        System.out.println(SEPARATOR);
        
        System.out.println("Variables disponibles para el modelo:");
        for (String column : summary.getColumnStats().keySet()) {
            StatisticsSummary.ColumnStatistics stats = summary.getColumnStats().get(column);
            System.out.printf("- %s: %,d valores válidos (rango: %.2f - %.2f)\n", 
                            column, stats.getCount(), stats.getMin(), stats.getMax());
        }
        
        System.out.println("\nPróximos pasos:");
        System.out.println("1. Análisis de correlación entre variables");
        System.out.println("2. Detección y tratamiento de outliers");
        System.out.println("3. Normalización de datos");
        System.out.println("4. Selección de features");
        System.out.println("5. Implementación del modelo de regresión");
    }
}