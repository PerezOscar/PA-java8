
package hilos.hilos2;
 

public class E1_SeccionCritica implements Runnable{
   
    E1_ICuentaBancaria nomina;
    
    public E1_SeccionCritica(E1_ICuentaBancaria s) {
        this.nomina = s;        
    } 
    
    @Override
    public void run() {
        for (int i = 0; i < E1_ICuentaBancaria.GASTOS; i++) {
            nomina.decremento();
        }
    }


}
