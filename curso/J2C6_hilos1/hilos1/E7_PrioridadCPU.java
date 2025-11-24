
//package hilos;




public class E7_PrioridadCPU implements Runnable {

    private char letra;
    private int nVeces;

    public E7_PrioridadCPU(char c, int n) {
        letra = c;
        nVeces = n;
    }

    @Override
    public void run() {
        hacerTarea();
       // hacerTareaPausada();        
    }

    private void hacerTarea() {
        for (int i = 1; i <= nVeces; i++) {
            System.out.print(" " + letra + "(" + i + ")");
        }
        System.out.println("\n#Fin del hilo:\t " + letra);
    }

    private void hacerTareaPausada() {
        try {
            for (int i = 1; i <= nVeces; i++) {
                System.out.print(" " + letra + "(" + i + ")");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        }
        System.out.println("\n#Fin del hilo:\t " + letra);
    }
       
    public int getNVeces(){
        return nVeces;
    }
}
