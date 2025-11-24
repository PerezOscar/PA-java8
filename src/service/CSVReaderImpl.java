package service;

import model.CSVRecord;
import model.StatisticsSummary;
import java.io.*;
import java.util.*;

/**
 * Implementación del lector CSV
 * Basada en el patrón de Archivo.java
 */
public class CSVReaderImpl implements ICSVProcessor {
    
    private String delimiter;
    private int bufferSize;
    private File currentFile;
    
    // Constructores sobrecargados
    public CSVReaderImpl() {
        this.delimiter = DEFAULT_DELIMITER;
        this.bufferSize = DEFAULT_BUFFER_SIZE;
    }
    
    public CSVReaderImpl(String delimiter) {
        this.delimiter = delimiter;
        this.bufferSize = DEFAULT_BUFFER_SIZE;
    }
    
    public CSVReaderImpl(String delimiter, int bufferSize) {
        this.delimiter = delimiter;
        this.bufferSize = bufferSize;
    }
    
    @Override
    public List<String> readHeaders(String filePath) throws IOException {
        this.currentFile = new File(filePath);
        
        if (!validateFile(currentFile)) {
            throw new IOException("Archivo no válido: " + filePath);
        }
        
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(currentFile), bufferSize);
            String headerLine = reader.readLine();
            
            if (headerLine != null) {
                String[] headers = headerLine.split(delimiter);
                List<String> headerList = new ArrayList<String>();
                for (String header : headers) {
                    headerList.add(header.trim());
                }
                return headerList;
            }
            return new ArrayList<String>();
            
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    @Override
    public Iterator<CSVRecord> getRecordIterator(String filePath) throws IOException {
        return new CSVRecordIterator(filePath);
    }
    
    @Override
    public StatisticsSummary processFile(String filePath, List<String> numericColumns) throws IOException {
        StatisticsSummary summary = new StatisticsSummary();
        Iterator<CSVRecord> iterator = getRecordIterator(filePath);
        
        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
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
    
    /**
     * Iterator interno para lectura eficiente - similar al patrón de Archivo.java
     */
    private class CSVRecordIterator implements Iterator<CSVRecord> {
        private BufferedReader reader;
        private List<String> headers;
        private String nextLine;
        private long recordNumber;
        
        public CSVRecordIterator(String filePath) throws IOException {
            this.reader = new BufferedReader(new FileReader(filePath), bufferSize);
            this.headers = readHeaders(filePath);
            this.reader = new BufferedReader(new FileReader(filePath), bufferSize);
            this.reader.readLine(); // Skip header
            this.nextLine = reader.readLine();
            this.recordNumber = 0;
        }
        
        public boolean hasNext() {
            return nextLine != null;
        }
        
        public CSVRecord next() {
            if (nextLine == null) {
                throw new NoSuchElementException("No more records");
            }
            
            CSVRecord record = parseRecord(nextLine, headers, recordNumber++);
            
            try {
                nextLine = reader.readLine();
                if (nextLine == null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading next record", e);
            }
            
            return record;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }
    
    /**
     * Parsea una línea CSV en un CSVRecord
     */
    private CSVRecord parseRecord(String line, List<String> headers, long recordNumber) {
        String[] values = line.split(delimiter);
        Map<String, String> data = new HashMap<String, String>();
        
        for (int i = 0; i < headers.size() && i < values.length; i++) {
            data.put(headers.get(i), values[i].trim());
        }
        
        return new CSVRecord(data, headers, recordNumber);
    }
    
    // Getters y Setters
    public String getDelimiter() { return delimiter; }
    public void setDelimiter(String delimiter) { this.delimiter = delimiter; }
    public int getBufferSize() { return bufferSize; }
    public void setBufferSize(int bufferSize) { this.bufferSize = bufferSize; }
}