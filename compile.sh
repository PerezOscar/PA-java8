#!/bin/bash

echo "========================================="
echo "COMPILANDO PROCESADOR CSV MVC - JAVA 8"
echo "========================================="

# Crear directorio de salida
mkdir -p build

# Compilar todos los archivos Java
echo "Compilando archivos fuente..."
find src -name "*.java" -print0 | xargs -0 javac -d build -cp src

# Verificar compilación
if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
    echo ""
    echo "ARCHIVOS GENERADOS:"
    find build -name "*.class" | wc -l | xargs echo "  Total de clases compiladas:"
    echo ""
    echo "PARA EJECUTAR:"
    echo "  java -cp build Main <archivo.csv> [columnas...]"
    echo ""
    echo "NOTA: Los archivos deben estar en la carpeta 'data/'"
    echo ""
    echo "EJEMPLOS:"
    echo "  java -cp build Main itineraries.csv"
    echo "  java -cp build Main ventas.csv precio cantidad"
    echo "  java -Xmx4g -cp build Main archivo_grande.csv col1 col2"
    echo ""
    echo "ESTRUCTURA GENERADA:"
    tree build 2>/dev/null || find build -type f | head -10
else
    echo "✗ Error en la compilación"
    echo "Revise los errores mostrados arriba"
    exit 1
fi

echo "========================================="