package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Modelo para estadísticas descriptivas
 * Implementa sincronización similar a E1_CuentaProtegidaMonitor.java
 */
public class StatisticsSummary {
    private final Map<String, ColumnStatistics> columnStats;
    private volatile long totalRecords;
    private final Object lock = new Object();
    
    public StatisticsSummary() {
        this.columnStats = new HashMap<String, ColumnStatistics>();
        this.totalRecords = 0;
    }
    
    /**
     * Clase interna para estadísticas por columna
     */
    public static class ColumnStatistics {
        private double sum = 0.0;
        private double min = Double.MAX_VALUE;
        private double max = Double.MIN_VALUE;
        private long count = 0;
        private double mean = 0.0;
        private double m2 = 0.0; // Para cálculo de varianza
        
        public synchronized void addValue(double value) {
            count++;
            sum += value;
            min = Math.min(min, value);
            max = Math.max(max, value);
            
            // Algoritmo de Welford para varianza online
            double delta = value - mean;
            mean += delta / count;
            double delta2 = value - mean;
            m2 += delta * delta2;
        }
        
        // Getters
        public double getSum() { return sum; }
        public double getMin() { return min == Double.MAX_VALUE ? 0 : min; }
        public double getMax() { return max == Double.MIN_VALUE ? 0 : max; }
        public long getCount() { return count; }
        public double getMean() { return mean; }
        public double getVariance() { return count > 1 ? m2 / (count - 1) : 0; }
        public double getStandardDeviation() { return Math.sqrt(getVariance()); }
    }
    
    public synchronized void addColumnValue(String column, double value) {
        ColumnStatistics stats = columnStats.get(column);
        if (stats == null) {
            stats = new ColumnStatistics();
            columnStats.put(column, stats);
        }
        stats.addValue(value);
    }
    
    public synchronized void incrementRecordCount() {
        totalRecords++;
    }
    
    // Getters
    public Map<String, ColumnStatistics> getColumnStats() {
        synchronized (lock) {
            return new HashMap<String, ColumnStatistics>(columnStats);
        }
    }
    
    public long getTotalRecords() {
        return totalRecords;
    }
}