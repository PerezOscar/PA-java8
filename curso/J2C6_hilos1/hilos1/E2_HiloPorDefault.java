//package hilos;

public class E2_HiloPorDefault {

    public static void main(String P[]) {

        // Obtenemos el hilo por default creado por el proceso
        Thread hiloDefault = Thread.currentThread();
        imprimirPropiedadesHilo(hiloDefault);
        
       /* Metodos Comentados, descomente para probarlos */
       
        //Descomente para ver los valores del rango de prioridad
        obtenerRangoPrioridad();
        
        
        //Descomente para cambiar valores
        //modificarValores(hiloDefault, "Hilo principal", 8);
        
        
        //Descomente para comprobar los cambios
        //imprimirPropiedadesHilo(hiloDefault);
    }

    private static void imprimirPropiedadesHilo(Thread h) {
        System.out.println(" \n ###################################");        
        System.out.println(" ***\t Propiedades del Hilo:\t ***");
        System.out.println(" \n Nombre:\t" + h.getName());
        System.out.println(" Id: \t\t" + h.getId());
        System.out.println(" Prioridad: \t" + h.getPriority());
        System.out.println(" Estado: \t" + h.getState());
        System.out.println(" isAlive: \t" + h.isAlive());
        System.out.println(" isInterrupted: " + h.isInterrupted());
        System.out.println(" isDaemon: \t" + h.isDaemon());
        System.out.println(" -----------------------------------");
    }
    private static void modificarValores(Thread h, String nombre, int prioridad){       
        h.setName(nombre);
        h.setPriority(prioridad);
    }
    private static void obtenerRangoPrioridad() {
        System.out.println(" MAX_PRIORITY: \t\t" + Thread.MAX_PRIORITY);
        System.out.println(" MIN_PRIORITY: \t\t" + Thread.MIN_PRIORITY);
        System.out.println(" NORM_PRIORITY: \t" + Thread.NORM_PRIORITY);
    }

}
