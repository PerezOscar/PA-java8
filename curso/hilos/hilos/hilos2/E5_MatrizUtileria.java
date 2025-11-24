
package hilos.hilos2;


public class E5_MatrizUtileria {
    
    
    
     public static int[][] multiplicarSerial(int A[][], int B[][]) {
        validarAxB(A[0].length, B.length);

        int filasA = A.length;
        int columnasB = B[0].length;

        int C[][] = new int[filasA][columnasB];

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                C[i][j] = 0;
                for (int k = 0; k < columnasB; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static void validarAxB(int a, int b) {
        if (a != b) {
            System.out.println("No es posible la multiplicacion!");
            System.exit(0);
        }
    }
    
}
