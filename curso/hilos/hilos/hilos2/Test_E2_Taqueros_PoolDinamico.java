
package hilos.hilos2;

import java.util.concurrent.*;


public class Test_E2_Taqueros_PoolDinamico {
    public static void main(String Argumentos[]) {
        int cantidadTaqueros;
        // Crear el pool de hilos con un numero Dinamico, 
        // depende del numeo de objetos Runnables
        ExecutorService poolTaqueros = Executors.newCachedThreadPool();
        
        // Activamos el pool de hilos enviandoles tareas
        for (int i = 0; i < 30; i++) {
            poolTaqueros.execute(new E2_Taqueros('T', 10));
        }
        //Obtenemos el numero de Hilos que se activaron 
        //para atender la carga de trabajo, restando el hilo main
        cantidadTaqueros = Thread.activeCount() - 1;

        // Se debe detener el pool
        poolTaqueros.shutdown();
        while (!poolTaqueros.isTerminated()) {
        }// Trampa de espera }
        E2_Taqueros.imprimirResumen();
        System.out.println("# Hilos (Taqueros trabajando): \t" + cantidadTaqueros);
        System.out.println("\n\nMetodo empleado:\t Executors.newCachedThreadPool()");
        
    }
}
