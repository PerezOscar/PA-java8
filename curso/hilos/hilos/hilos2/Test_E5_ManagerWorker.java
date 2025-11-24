package hilos.hilos2;


import java.util.Date;
import hilos.hilos2.E5_MatrizUtileria.*;

public class Test_E5_ManagerWorker {

    public static void main(String[] args) {
        int A[][], B[][], C[][], D[][];
        double tiempoSerial, tiempoConcurrente;
        double speedUp;

        Date tiempoInicialSerial, tiempoFinalSerial, tiempoInicialConcurrente, tiempoFinalConcurrente;
       
        
        A = E5_Matriz.crear(200, 200);
        B = E5_Matriz.crear(200, 200);

        E5_Manager m = new E5_Manager();

        //Descomente si se desea aburrir esperando a que imprima
        // Matriz.imprimir(A);
        // Matriz.imprimir(B);
        
        tiempoInicialSerial = new Date();
        C = E5_MatrizUtileria.multiplicarSerial(A, B);
        tiempoFinalSerial = new Date();
        
        //Descomente si se desea aburrir esperando a que imprima 
        // Matriz.imprimir(C);
        tiempoInicialConcurrente = new Date();
        D = m.multiplicarConcurrente(A, B);
        tiempoFinalConcurrente = new Date();

        System.out.printf("Matriz A: (%d,%d) \n", A.length, A[0].length);

        System.out.printf("Matriz B: (%d,%d) \n", B.length, B[0].length);

        System.out.printf("Matriz C: (%d,%d) \n", C.length, C[0].length);

        tiempoSerial = tiempoFinalSerial.getTime() - tiempoInicialSerial.getTime();
        tiempoConcurrente = tiempoFinalConcurrente.getTime() - tiempoInicialConcurrente.getTime();

        speedUp = calcularSpeedUp(tiempoSerial, tiempoConcurrente);
        System.out.printf("\n Tiempo serial: %,.6f ms ", tiempoSerial);        
        System.out.printf("\n Tiempo concurrente: %,.6f ms ", tiempoConcurrente);
        System.out.printf("\n SpeedUp: %.6f  \n\n", speedUp);

    }

    private static double calcularSpeedUp(double s, double p) {
        double r;
        r = s / p;
        return r;
    }

}
