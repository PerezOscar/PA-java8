import controller.CSVProcessorController;
import service.ICSVProcessor;
import service.CSVReaderImpl;
import utils.ColumnSelector;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase principal para ejecutar el procesador CSV MVC
 * Implementa todos los conceptos del curso de Java 8
 */
public class Main {
    
    public static void main(String[] args) {
        // Mostrar información del sistema
        showSystemInfo();
        
        // Validar argumentos
        if (args.length < 1) {
            showUsage();
            return;
        }
        
        String fileName = args[0];
        String filePath = "data/" + fileName;
        
        // Leer headers y mostrar columnas detectadas
        List<String> headers = readHeaders(filePath);
        if (headers.isEmpty()) {
            System.err.println("Error: No se pudieron leer las columnas del archivo.");
            return;
        }
        
        displayDetectedColumns(headers);
        
        // Selección de columnas
        List<String> numericColumns;
        if (args.length > 1) {
            numericColumns = parseNumericColumns(args);
        } else {
            numericColumns = ColumnSelector.selectColumns(headers);
        }
        
        // Crear controlador y procesar
        try {
            CSVProcessorController controller = createController(args);
            controller.processFile(filePath, numericColumns);
            
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Muestra información del sistema
     */
    private static void showSystemInfo() {
        System.out.println("=====================================");
        System.out.println("PROCESADOR CSV - ARQUITECTURA MVC");
        System.out.println("=====================================");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Procesadores disponibles: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Memoria máxima JVM: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " MB");
        System.out.println("=====================================\n");
        
        // Mostrar descripción usando método estático de interfaz
        ICSVProcessor.showDescription("Arquitectura MVC con Java 8");
    }
    
    /**
     * Muestra uso correcto del programa
     */
    private static void showUsage() {
        System.out.println("USO:");
        System.out.println("  java Main <archivo.csv> [columnas_numericas...]");
        System.out.println();
        System.out.println("NOTA: Los archivos deben estar en la carpeta 'data/'");
        System.out.println();
        System.out.println("EJEMPLOS:");
        System.out.println("  java Main itineraries.csv");
        System.out.println("  java Main ventas.csv precio cantidad descuento");
        System.out.println("  java Main empleados.csv salario edad experiencia");
        System.out.println();
        System.out.println("OPCIONES:");
        System.out.println("  Si no se especifican columnas, se detectarán automáticamente");
        System.out.println("  Para archivos grandes, usar: java -Xmx4g Main archivo.csv");
    }
    
    /**
     * Parsea columnas numéricas de los argumentos
     */
    private static List<String> parseNumericColumns(String[] args) {
        List<String> columns = new ArrayList<String>();
        
        if (args.length > 1) {
            // Agregar columnas especificadas
            for (int i = 1; i < args.length; i++) {
                columns.add(args[i]);
            }
        }
        
        return columns;
    }
    
    /**
     * Muestra configuración actual
     */
    private static void showConfiguration(String filePath, List<String> numericColumns) {
        System.out.println("CONFIGURACIÓN:");
        System.out.println("  Archivo: " + filePath);
        System.out.println("  Directorio: data/");
        
        if (numericColumns.isEmpty()) {
            System.out.println("  Columnas: Detección automática");
        } else {
            System.out.println("  Columnas especificadas: " + numericColumns.size());
            for (String column : numericColumns) {
                System.out.println("    - " + column);
            }
        }
        System.out.println();
    }
    
    /**
     * Lee headers del archivo CSV
     */
    private static List<String> readHeaders(String filePath) {
        try {
            ICSVProcessor reader = new CSVReaderImpl();
            return reader.readHeaders(filePath);
        } catch (Exception e) {
            System.err.println("Error leyendo headers: " + e.getMessage());
            return new ArrayList<String>();
        }
    }
    
    /**
     * Muestra las columnas detectadas en el archivo
     */
    private static void displayDetectedColumns(List<String> headers) {
        System.out.println("========================================");
        System.out.println("COLUMNAS DETECTADAS EN EL ARCHIVO CSV");
        System.out.println("========================================");
        System.out.println("Total de columnas: " + headers.size());
        System.out.println("----------------------------------------");
        
        for (int i = 0; i < headers.size(); i++) {
            System.out.printf("%3d. %s\n", (i + 1), headers.get(i));
        }
        System.out.println();
    }
    
    /**
     * Crea el controlador apropiado basado en argumentos
     */
    private static CSVProcessorController createController(String[] args) {
        // Por defecto usar coma como delimitador
        String delimiter = ",";
        
        // Buscar si se especificó un delimitador personalizado
        for (String arg : args) {
            if (arg.startsWith("--delimiter=")) {
                delimiter = arg.substring("--delimiter=".length());
                break;
            }
        }
        
        return new CSVProcessorController(delimiter);
    }
}