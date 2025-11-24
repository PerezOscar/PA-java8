package hilos.hilos2;



public class E1_CuentaNoProtegida implements E1_ICuentaBancaria {

    private int saldoActual = 0;

    @Override
    public void incremento() {
        this.saldoActual++;
    }

    @Override
    public void decremento() {
        this.saldoActual--;
    }

    @Override
    public int saldoActual() {
        return this.saldoActual;
    }

    static {
        System.out.println("#######################");
        System.out.println("# Cuenta no protegida #");
        System.out.println("# ------------------- #\n");
    }
}
