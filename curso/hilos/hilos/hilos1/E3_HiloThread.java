
package hilos.hilos1;

public class E3_HiloThread extends Thread {
    
    @Override 
    public void run(){
        for(int i =0; i < 12; i++){
            System.out.println("HiloThread: " + i);
        }
    }
    
}
