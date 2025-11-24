package hilos.hilos2;


public class E4_Consumidor implements Runnable {

    E4_MesaBuffet mesaBuffet;

    public E4_Consumidor(E4_MesaBuffet mesaBuffet) {
        this.mesaBuffet = mesaBuffet;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("\t\t\tComelon consume: " + mesaBuffet.consumir());
                Thread.sleep((int) (Math.random() * 1000));
            }
        } catch (InterruptedException ex) {
            // La logica que cada quien quiera....
        }
    }
}
