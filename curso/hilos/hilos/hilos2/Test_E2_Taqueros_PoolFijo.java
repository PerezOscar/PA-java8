package hilos.hilos2;

import java.util.concurrent.*;

public class Test_E2_Taqueros_PoolFijo {
    public static void main(String Argumentos[]) {
        int cantidadTaqueros; 
        // Obtenemos el numero de CPUs que lee la JVM
        int CPUs = Runtime.getRuntime().availableProcessors();

        // Crear el pool de hilos con un numero Fijo
        ExecutorService poolTaqueros = Executors.newFixedThreadPool(CPUs);
        
        /* Vamos a simular que la CARGA de trabajo es que los 
           Taqueros preparen ordenes de algun producto
        */
        poolTaqueros.execute(new E2_Taqueros('T', 10)); // Orden 1:  (Producto T, cantidad 6) 
        poolTaqueros.execute(new E2_Taqueros('Q', 4));
        poolTaqueros.execute(new E2_Taqueros('P', 10));
        poolTaqueros.execute(new E2_Taqueros('S', 5));
        poolTaqueros.execute(new E2_Taqueros('E', 5));
        poolTaqueros.execute(new E2_Taqueros('T', 6));
        //Obtenemos el numero de Hilos que se activaron 
        //para atender la carga de trabajo, restando el hilo main
        cantidadTaqueros = Thread.activeCount() -1;

        // Se debe detener el pool
        poolTaqueros.shutdown();
        while (!poolTaqueros.isTerminated()) {
        }

        E2_Taqueros.imprimirResumen();
        System.out.println("# Hilos (Taqueros trabajando): \t" + cantidadTaqueros );
        System.out.println("\n\nMetodo empleado:\t Executors.newFixedThreadPool(CPUs)");

    }
}
