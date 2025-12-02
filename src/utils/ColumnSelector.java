package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para seleccionar columnas interactivamente
 */
public class ColumnSelector {
    
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Permite al usuario seleccionar columnas específicas
     */
    public static List<String> selectColumns(List<String> availableColumns) {
        System.out.println("\n========================================");
        System.out.println("SELECCIÓN DE COLUMNAS PARA PROCESAR");
        System.out.println("========================================");
        
        displayAvailableColumns(availableColumns);
        
        return selectSpecificColumns(availableColumns);
    }
    
    /**
     * Muestra las columnas disponibles
     */
    private static void displayAvailableColumns(List<String> columns) {
        System.out.println("\nColumnas disponibles:");
        for (int i = 0; i < columns.size(); i++) {
            System.out.printf("%3d. %s\n", (i + 1), columns.get(i));
        }
    }
    
    /**
     * Permite seleccionar columnas específicas
     */
    private static List<String> selectSpecificColumns(List<String> availableColumns) {
        List<String> selectedColumns = new ArrayList<String>();
        
        try {
            System.out.println("\nIngrese los números de las columnas separados por comas (ej: 1,3,5):");
            System.out.print("Columnas: ");
            String input = reader.readLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Error: Debe seleccionar al menos una columna.");
                return selectSpecificColumns(availableColumns);
            }
            
            String[] indices = input.split(",");
            for (String indexStr : indices) {
                try {
                    int index = Integer.parseInt(indexStr.trim()) - 1;
                    if (index >= 0 && index < availableColumns.size()) {
                        String column = availableColumns.get(index);
                        if (!selectedColumns.contains(column)) {
                            selectedColumns.add(column);
                        }
                    } else {
                        System.out.println("⚠ Índice inválido: " + (index + 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Número inválido: " + indexStr.trim());
                }
            }
            
            if (selectedColumns.isEmpty()) {
                System.out.println("Error: No se seleccionaron columnas válidas. Inténtelo de nuevo.");
                return selectSpecificColumns(availableColumns);
            }
            
            System.out.println("\n✓ Columnas seleccionadas:");
            for (String column : selectedColumns) {
                System.out.println("  - " + column);
            }
            
        } catch (IOException e) {
            System.out.println("Error leyendo entrada. Inténtelo de nuevo.");
            return selectSpecificColumns(availableColumns);
        }
        
        return selectedColumns;
    }
}