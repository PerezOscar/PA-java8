package exceptions;

/**
 * Excepción personalizada para errores de procesamiento CSV
 * Implementa jerarquía de excepciones
 */
public class CSVProcessingException extends Exception {
    
    private final String fileName;
    private final long recordNumber;
    private final String columnName;
    
    // Constructor básico
    public CSVProcessingException(String message) {
        super(message);
        this.fileName = null;
        this.recordNumber = -1;
        this.columnName = null;
    }
    
    // Constructor con causa
    public CSVProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.fileName = null;
        this.recordNumber = -1;
        this.columnName = null;
    }
    
    // Constructor completo
    public CSVProcessingException(String message, String fileName, long recordNumber, String columnName) {
        super(message);
        this.fileName = fileName;
        this.recordNumber = recordNumber;
        this.columnName = columnName;
    }
    
    // Constructor completo con causa
    public CSVProcessingException(String message, Throwable cause, String fileName, long recordNumber, String columnName) {
        super(message, cause);
        this.fileName = fileName;
        this.recordNumber = recordNumber;
        this.columnName = columnName;
    }
    
    // Getters
    public String getFileName() { return fileName; }
    public long getRecordNumber() { return recordNumber; }
    public String getColumnName() { return columnName; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        
        if (fileName != null) {
            sb.append(" [Archivo: ").append(fileName).append("]");
        }
        if (recordNumber >= 0) {
            sb.append(" [Registro: ").append(recordNumber).append("]");
        }
        if (columnName != null) {
            sb.append(" [Columna: ").append(columnName).append("]");
        }
        
        return sb.toString();
    }
}