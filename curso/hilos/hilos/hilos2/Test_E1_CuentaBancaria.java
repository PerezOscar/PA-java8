package hilos.hilos2;


public class Test_E1_CuentaBancaria {

    public static void main(String r[]) {
        
        /* De los siguientes escenarios, seleccione y 
            mantenga solamente uno descomentado, 
            o modifique el programa  e incluyale un menu de seleccion        
        */
        
        /* Escenario 1:   Cuenta No protegida */
         E1_ICuentaBancaria objetoCuenta = new E1_CuentaNoProtegida();
        
        /* Escenario 2:   Cuenta protegida con monitores */
         // E1_ICuentaBancaria objetoCuenta = new E1_CuentaProtegidaMonitor();
        
        /* Escenario 3:   Cuenta protegida con candados */
        //E1_ICuentaBancaria objetoCuenta = new E1_CuentaProtegidaCandado();

        Runnable objetoRunable = new E1_SeccionCritica(objetoCuenta);
        Thread objetoHilo = new Thread(objetoRunable);
        objetoHilo.start();
        for (int i = 0; i < E1_ICuentaBancaria.INGRESOS; i++) {
            objetoCuenta.incremento();
        }
        try {
            objetoHilo.join();
        } catch (InterruptedException e) { /* Lo que quieran aqui  */  }

        System.out.printf("El saldo final es: %d  \t \n\n", objetoCuenta.saldoActual());
    }
}
