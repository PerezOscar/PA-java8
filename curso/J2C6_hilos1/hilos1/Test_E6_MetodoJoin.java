
//package hilos;


public class Test_E6_MetodoJoin {
    public static void main(String Argumentos[]) {

        Thread hilo4 = new Thread(new E6_MetodoJoin());
        Thread hilo5 = new Thread(new E6_MetodoJoin());

        hilo4.start();
        hilo5.start();
       
        /*
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
        */
        
        try {
            hilo4.join();
            hilo5.join();
        } catch (InterruptedException e) {
        }
        
       
        System.out.println("La suma Hilo es:" + E6_MetodoJoin.obtenerSumaTotal());
        System.out.println("Fin Main:");
    }
}
