package hilos.hilos2;


import java.util.LinkedList;
import java.util.concurrent.locks.*;

public class E4_MesaBuffet {

    // Tenemos que definir un tamaño de mesaBuffet
    // es donde se colocaran los productos del Productor
    private final static int TAMANYO;
    private final LinkedList<Integer> mesaBuffet = new LinkedList<>();
    // Creamos el candado
    private final static Lock candado;
    // Se deben de crear las condiciones de señalizacion entre los procesos
    private final static Condition hayPlatillos;
    private final static Condition noHayPlatillos;

    public void producir(int cantidadPlatillos) {
        candado.lock();  // Cerramos el candado
        try {
            while (mesaBuffet.size() == TAMANYO) {
                System.out.print("# MesaBuffet(LLENA)");
                System.out.println("\tCOCINERO despierta a COMELON\n ");                
                noHayPlatillos.await();
            }
            mesaBuffet.offer(cantidadPlatillos);
            hayPlatillos.signal(); // Señal de condicion hayPlatillos 
        } catch (InterruptedException ex) {
        } finally {
            candado.unlock(); // Liberamos el candado
        }
    }

    public int consumir() {
        int valor = 0;
        candado.lock();  // Cerramos el candado
        try {
            while (mesaBuffet.isEmpty()) {
                System.out.print("# MesaBuffet(VACIA)");
                System.out.println("\tCOMELON despierta a COCINERO\n ");               
                hayPlatillos.await();
            }
            valor = mesaBuffet.remove();
            noHayPlatillos.signal(); // señal de condicion noHayPlatillos 
        } catch (InterruptedException ex) {
        } finally {
            candado.unlock(); // Liberamos el candado            
        }
        return valor;
    }

    static {
        TAMANYO = 5;
        candado = new ReentrantLock();
        hayPlatillos = candado.newCondition();
        noHayPlatillos = candado.newCondition();
    }
}
