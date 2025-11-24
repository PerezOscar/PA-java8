package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entidad que representa un registro CSV
 * Basado en el patrón de constructores sobrecargados de HeroesPatria.java
 */
public class CSVRecord {
    private final Map<String, String> data;
    private final List<String> headers;
    private final long recordNumber;
    
    // Constructor sobrecargado - solo datos
    public CSVRecord(Map<String, String> data) {
        this.data = new HashMap<String, String>(data);
        this.headers = null;
        this.recordNumber = -1;
    }
    
    // Constructor sobrecargado - datos y headers
    public CSVRecord(Map<String, String> data, List<String> headers) {
        this.data = new HashMap<String, String>(data);
        this.headers = headers;
        this.recordNumber = -1;
    }
    
    // Constructor sobrecargado completo
    public CSVRecord(Map<String, String> data, List<String> headers, long recordNumber) {
        this.data = new HashMap<String, String>(data);
        this.headers = headers;
        this.recordNumber = recordNumber;
    }
    
    // Getters
    public String getValue(String column) {
        return data.get(column);
    }
    
    public Double getNumericValue(String column) {
        try {
            String value = data.get(column);
            return value != null && !value.trim().isEmpty() ? Double.parseDouble(value.trim()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public Map<String, String> getData() {
        return new HashMap<String, String>(data);
    }
    
    public List<String> getHeaders() {
        return headers;
    }
    
    public long getRecordNumber() {
        return recordNumber;
    }
    
    // Setters no incluidos - inmutable por diseño
    
    @Override
    public String toString() {
        return "CSVRecord{recordNumber=" + recordNumber + ", data=" + data + "}";
    }
}