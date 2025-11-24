package hilos.hilos2;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test_E4_ProductorConsumidor {
    
    private static E4_MesaBuffet mesaBuffet = new E4_MesaBuffet();

    public static void main(String[] args) {
        // Creamos el pool de hilos
        ExecutorService cocineros = Executors.newFixedThreadPool(2); // 2 cocineros
        ExecutorService comelones = Executors.newFixedThreadPool(5); // 3 comelones
        
        cocineros.execute(new E4_Productor(mesaBuffet));
        
        comelones.execute(new E4_Consumidor(mesaBuffet));
        cocineros.shutdown();
        comelones.shutdown();
    }
}
