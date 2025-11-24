package hilos.hilos1;



public class Test_E3_E4 {

    public static void main(String P[]) {
        // Hilo de la clase Thread
        E3_HiloThread hiloThread = new E3_HiloThread();
        
        //Hilo de la interface Runnable        
        Thread hiloRunnable = new Thread(new E4_HiloRunnable());
        
        hiloThread.start();
        hiloRunnable.start();
        
        
        
        
        
        for(int i =0; i<12; i++){
            System.out.println("HiloMain: " +i);
            
        }
        
        
        
    }
}
