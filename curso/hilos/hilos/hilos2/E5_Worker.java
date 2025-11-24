package hilos.hilos2;


public class E5_Worker implements Runnable {

    private  final int A[][];
    private  final int B[][];
    private  final int C[][];
    private  final int fila;

    public E5_Worker(int C[][], int A[][], int B[][], int fila) {        
        this.C = C;
        this.A = A;
        this.B = B;
        this.fila = fila;        
    }    
       
    // Realiza tarea parcial
    @Override
    public void run() {
        for (int i = 0; i < B[0].length; i++) {
            C[this.fila][i] = 0;
            for (int j = 0; j < A[fila].length; j++) {
                C[fila][i] += A[fila][j] * B[j][i];
            }
        }
    }

}
