
package hilos.hilos1;

import java.util.Date;

public class E5_MetodoSleep {
   
    public  void relojContinuo() {
        for (int i = 0; i < 100; i++) {
            System.out.printf("Reloj continuo: \t %s \n", new Date());
        }
    }

    public void relojPausado() {
        try {
            for (int i = 0; i < 6; i++) {
                System.out.printf("Reloj pausado: \t %s \n", new Date());
                Thread.sleep(100);   // Objetivo:  Pausar el hilo
            }
        } catch (InterruptedException e) {/* No hacemos nada  aqui */ }
    }
}
