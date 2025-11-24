
//package hilos;


public class Test_E7_PrioridadCPU {
     public static void main(String Argumentos[]) {
        int prioridadBaja = 1;        
        int prioridadAlta = 10;
        
        //Ejercicios 2 y 3
        Runnable objRunnable1 = new E7_PrioridadCPU('A', 12);
        Thread hilo1 = new Thread(objRunnable1);
        hilo1.setPriority(prioridadBaja);
        
        Runnable objRunnable2 = new E7_PrioridadCPU('B', 12);
        Thread hilo2 = new Thread(objRunnable2);
        
        Runnable objRunnable3 = new E7_PrioridadCPU('C', 12);
        Thread hilo3 = new Thread(objRunnable3);
        hilo3.setPriority(prioridadAlta);
        
        hilo1.start();
        hilo2.start();
        hilo3.start();
        
        for (int i = 1; i <= 12; i++) {
            System.out.print(" M" + "(" + i + ")");
        }
        System.out.println("\n#Fin del Main ");    
    }
}
