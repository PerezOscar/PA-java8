package service;

import model.CSVRecord;
import model.StatisticsSummary;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Interfaz principal para procesamiento CSV
 * Basada en el patrón de IArchivo.java con métodos default
 */
public interface ICSVProcessor {
    
    // Constantes como en IArchivo
    String DEFAULT_DELIMITER = ",";
    int DEFAULT_BUFFER_SIZE = 8192;
    String ATTENTION = " ATENCION !!! ";
    String FILE_NOT_FOUND = "No se encontró el archivo:";
    
    // Métodos principales
    List<String> readHeaders(String filePath) throws IOException;
    Iterator<CSVRecord> getRecordIterator(String filePath) throws IOException;
    StatisticsSummary processFile(String filePath, List<String> numericColumns) throws IOException;
    
    // Método default para validación como en IArchivo
    default boolean validateFile(File file) {
        boolean exists = true;
        if (!file.exists()) {
            System.out.printf("\t\t %s \n", ATTENTION);
            System.out.printf("\t %s \t%s\n", FILE_NOT_FOUND, file.getName());
            exists = false;
        }
        return exists;
    }
    
    // Método default para obtener propiedades del archivo
    default void getFileProperties(File file) {
        System.out.println("\t# Propiedades del archivo CSV #");
        System.out.println("\t#-----------------------------#");
        System.out.println("\t Tamaño: \t\t " + file.length() + " bytes");
        System.out.println("\t Lectura: \t\t " + file.canRead());
        System.out.println("\t Escritura: \t\t " + file.canWrite());
        System.out.println("\t Ruta absoluta: \t " + file.getAbsolutePath());
        System.out.println("\t Nombre: \t\t " + file.getName());
    }
    
    // Método estático para mostrar descripción
    static void showDescription(String implementation) {
        System.out.println("########################################");
        System.out.println("Descripción:");
        System.out.printf("\t Implementación: %s \n", implementation);
        System.out.println("\t -------------------------\n");
    }
}