
//package hilos2;
import java.security.SecureRandom;

public class E5_Matriz {
    
    private static final SecureRandom numeroAleatorio;
    
    public static int[][] crear(int filas, int columnas) {

        int MatrizX[][] = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                MatrizX[i][j] = numeroAleatorio.nextInt(10);
            }
        }
        return MatrizX;
    }
    
    
     public static void imprimir(int M[][]) {

        System.out.println();

        int filas = M.length;
        int columnas = M[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(M[i][j] + "  ");
            }
            System.out.println();
        }
    }
    static {    
        numeroAleatorio = new SecureRandom();
    }
    
}
