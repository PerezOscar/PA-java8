package hilos.hilos2;


public class E1_CuentaProtegidaMonitor implements E1_ICuentaBancaria {
    private int saldoActual = 0;

    @Override
    public synchronized void incremento() {
        this.saldoActual++;
    }
    @Override
    public synchronized void decremento() {
        this.saldoActual--;
    }
    @Override
    public int saldoActual() {
        return this.saldoActual;
    }
    static {
        System.out.println("##################################");
        System.out.println("# Cuenta protegida (syncronized) #");
        System.out.println("# ------------------------------ #\n");
    }
}
