#!/bin/bash

echo "========================================="
echo "PROCESADOR CSV - ARQUITECTURA MVC"
echo "========================================="

# Compilar si no existe build o si hay cambios
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

# Configuración JVM optimizada para concurrencia
JAVA_OPTS="-Xmx6g -Xms3g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UnlockExperimentalVMOptions -XX:+UseStringDeduplication -XX:G1HeapRegionSize=16m -XX:+UseCompressedOops"

echo ""
echo "Ejecutando procesador CSV..."
echo "Configuración JVM: $JAVA_OPTS"
echo ""

# Ejecutar programa
java $JAVA_OPTS -cp build Main "$@"

echo ""
echo "========================================="