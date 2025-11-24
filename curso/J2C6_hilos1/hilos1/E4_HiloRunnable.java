//package hilos;

public class E4_HiloRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 12; i++) {
            System.out.println("HiloRunnable: " + i);
        }
    }

}
