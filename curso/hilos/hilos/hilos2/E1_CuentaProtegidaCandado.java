package hilos.hilos2;


import java.util.concurrent.locks.*;

public class E1_CuentaProtegidaCandado implements E1_ICuentaBancaria {

    private static final Lock objetoCANDADO = new ReentrantLock();  // Creamos el  objeto candado
    private int saldoActual = 0;

    @Override
    public void incremento() {
        objetoCANDADO.lock();  //  Se CIERRA el candado
        try {
            this.saldoActual++;
        } finally {
            objetoCANDADO.unlock(); //  Se ABRE o LIBERA el candado
        }
    }

    @Override
    public void decremento() {
        objetoCANDADO.lock(); //  Se CIERRA el candado
        try {
            this.saldoActual--;
        } finally {
            objetoCANDADO.unlock(); //  Se ABRE o LIBERA el candado
        }
    }

    @Override
    public int saldoActual() {
        return this.saldoActual;
    }

    static {
        System.out.println("####################################");
        System.out.println("# Cuenta protegida (ReentrantLock) #");
        System.out.println("# -------------------------------- #\n");
    }
}
