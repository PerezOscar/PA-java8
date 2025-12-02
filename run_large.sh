#!/bin/bash

echo "========================================="
echo "PROCESADOR CSV - ARCHIVOS GRANDES"
echo "========================================="

# Compilar si es necesario
if [ ! -d "build" ] || [ "src" -nt "build" ]; then
    echo "Compilando..."
    mkdir -p build
    find src -name "*.java" -exec javac -d build -cp src {} +
    
    if [ $? -ne 0 ]; then
        echo "✗ Error en la compilación"
        exit 1
    fi
    echo "✓ Compilación exitosa"
fi

# Configuración JVM para archivos grandes
JAVA_OPTS="-Xmx8g -Xms4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

echo ""
echo "Configuración para archivos grandes:"
echo "  Heap máximo: 8GB"
echo "  Heap inicial: 4GB"
echo "  GC: G1 optimizado"
echo ""

# Verificar memoria disponible
echo "Memoria del sistema:"
free -h 2>/dev/null || echo "Información de memoria no disponible"

echo ""
echo "Ejecutando procesador CSV..."
echo "NOTA: Para archivos grandes el proceso puede tomar tiempo"
echo ""

# Ejecutar programa
java $JAVA_OPTS -cp build Main "$@"

echo ""
echo "========================================="