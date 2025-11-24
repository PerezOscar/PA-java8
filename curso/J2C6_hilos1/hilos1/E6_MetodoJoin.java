
//package hilos;

public class E6_MetodoJoin implements Runnable {
    private static int sumaTotal = 0;

    @Override
    public void run() {
       // this.hacerTarea();
        this.hacerTareaPausada();
    }

    public void hacerTarea() {
        int suma = 0;
        for (int i = 1; i < 101; i++) {
            suma += i;           
        }
        sumaTotal += suma;
    }

    public void hacerTareaPausada() {
        int suma = 0;
        try {
            for (int i = 0; i < 101; i++) {
                suma += i;
                Thread.sleep(1);
            }
            sumaTotal += suma;
        } catch (InterruptedException e) {
        }
    }

    public static int obtenerSumaTotal() {
        return sumaTotal;
    }
}
