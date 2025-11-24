package hilos.hilos2;


public interface E1_ICuentaBancaria {

    public static final int INGRESOS = 300000, GASTOS = 5000;

    void incremento();

    void decremento();

    int saldoActual();

}
